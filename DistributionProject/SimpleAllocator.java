import java.util.Collection;
//import java.util.Collections;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.PriorityQueue;

public class SimpleAllocator {

    // Method to find the cheapest path using Dijkstra's algorithm
    public static Collection<Transporter> CheapestPath(
            Collection<Supplier> suppliers,
            Collection<Transporter> transporters) {

        // Priority queue to store the available transporters based on cost
        PriorityQueue<Transporter> minCostQueue = new PriorityQueue<>(
                (t1, t2) -> Integer.compare(t1.costPerUnit(), t2.costPerUnit()));

        // Set to keep track of visited suppliers
        HashSet<Supplier> visitedSuppliers = new HashSet<>();

        // Collection to store the selected transporters for the cheapest path
        Collection<Transporter> cheapestPath = new HashSet<>();

        // Starting from the manufacturer
        Supplier manufacturer = getSupplierByName(suppliers, "Manufacturer");

        // Add transporters from the manufacturer to the queue
        for (Transporter transporter : transporters) {
            if (transporter.from().equals(manufacturer)) {
                minCostQueue.add(transporter);
            }
        }

        // Dijkstra's algorithm
        while (!minCostQueue.isEmpty()) {
            Transporter currentTransporter = minCostQueue.poll();
            Supplier currentSupplier = currentTransporter.to();

            // Skip if the supplier is already visited
            if (visitedSuppliers.contains(currentSupplier)) {
                continue;
            }

            // Add the selected transporter to the cheapest path
            cheapestPath.add(currentTransporter);

            // Mark the current supplier as visited
            visitedSuppliers.add(currentSupplier);

            // Add transporters from the current supplier to the queue
            for (Transporter transporter : transporters) {
                if (transporter.from().equals(currentSupplier)) {
                    minCostQueue.add(transporter);
                }
            }
        }

        return cheapestPath;
    }

    // Method to allocate for demand using Ford Fulkerson algorithm
    public static Collection<Transporter> AllocateForDemand(
            Collection<Supplier> suppliers,
            Collection<Transporter> transporters) {

        // Collection to store the selected transporters for allocation
        Collection<Transporter> allocation = new HashSet<>();

        // Total demand to be satisfied
        int totalDemand = totalDemand(suppliers);

        // Iterate through each supplier
        for (Supplier supplier : suppliers) {
            int supplierDemand = supplier.demand();
            int remainingDemand = supplierDemand;

            // Iterate through transporters to allocate
            for (Transporter transporter : transporters) {
                Supplier fromSupplier = transporter.from();
                Supplier toSupplier = transporter.to();

                // Allocate based on remaining demand and transporter capacity
                if (fromSupplier.equals(supplier) && remainingDemand > 0) {
                    int availableCapacity = transporter.maxCapacity() - transporter.allocation();
                    int allocationAmount = Math.min(remainingDemand, availableCapacity);

                    // Update transporter allocation
                    transporter.setAllocation(transporter.allocation() + allocationAmount);

                    // Update remaining demand
                    remainingDemand -= allocationAmount;

                    // Add transporter to allocation
                    allocation.add(transporter);
                }
            }

            // Check if the demand has been satisfied
            if (remainingDemand > 0) {
                System.err.println("Insufficient capacity to satisfy demand for " + supplier.name());
            }
        }

        return allocation;
    }

    // Helper method to get a supplier by name
    private static Supplier getSupplierByName(Collection<Supplier> suppliers, String name) {
        return suppliers.stream()
                .filter(supplier -> supplier.name().equals(name))
                .findFirst()
                .orElse(null);
    }

    public static int totalDemand(Collection<Supplier> suppliers) {
        int sum = 0;
        for (Supplier s : suppliers) {
            sum += s.demand();
        }
        return sum;
    }

    public static int totalAmountShipped(Collection<Transporter> transporters) {
        int sum = 0;
        for (Transporter t : transporters) {
            sum += t.allocation();
        }
        return sum;
    }

    public static int totalTransporterCost(Collection<Transporter> transporters) {
        return transporters.stream()
                .map(t -> t.allocation() * t.costPerUnit())
                .reduce(0, Integer::sum);
    }

    public static String displayAllocations(Collection<Transporter> transporters) {
        return transporters.stream()
                .map(t -> String.format("%s: %s $%s", t.name(), t.allocation(), t.costPerUnit()))
                .collect(Collectors.joining("\n"));
    }
}