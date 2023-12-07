import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.ArrayList;

public class SimpleAllocator {

    public static Collection<Transporter> cheapestPath(
            Collection<Supplier> suppliers,
            Collection<Transporter> transporters,
            Supplier source,
            Supplier destination) {

        // Check if any parameter is null
        if (suppliers == null || transporters == null || source == null || destination == null) {
            return Collections.emptyList();
        }

        // Create the adjacency list
        Map<Supplier, List<Transporter>> adjacencyList = createAdjacencyList(suppliers, transporters);

        // Priority queue to store transporters based on cost
        PriorityQueue<Transporter> minCostQueue = new PriorityQueue<>(
                Comparator.comparingInt(Transporter::costPerUnit));

        // Set to keep track of visited suppliers
        Set<Supplier> visitedSuppliers = new HashSet<>();

        // Collection to store the selected transporters for the cheapest path
        Collection<Transporter> cheapestPath = new HashSet<>();

        // Add transporters from the source supplier to the queue
        for (Transporter transporter : adjacencyList.getOrDefault(source, Collections.emptyList())) {
            minCostQueue.add(transporter);
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

            // Check if the destination supplier is reached
            if (currentSupplier.equals(destination)) {
                break;
            }

            // Add transporters from the current supplier to the queue
            for (Transporter nextTransporter : adjacencyList.getOrDefault(currentSupplier, Collections.emptyList())) {
                minCostQueue.add(nextTransporter);
            }
        }

        return cheapestPath;
    }

    public static Map<Supplier, List<Transporter>> createAdjacencyList(
            Collection<Supplier> suppliers,
            Collection<Transporter> transporters) {

        Map<Supplier, List<Transporter>> adjacencyList = new HashMap<>();

        for (Transporter transporter : transporters) {
            Supplier fromSupplier = transporter.from();
            adjacencyList.computeIfAbsent(fromSupplier, k -> new ArrayList<>()).add(transporter);
        }

        return adjacencyList;
    }

    public static Collection<Transporter> allocateForDemand(
            Collection<Supplier> suppliers,
            Collection<Transporter> transporters) {

        Collection<Transporter> allocation = new HashSet<>();

        int totalDemand = totalDemand(suppliers);

        for (Supplier supplier : suppliers) {
            int supplierDemand = supplier.demand();
            int remainingDemand = supplierDemand;

            for (Transporter transporter : transporters) {
                Supplier fromSupplier = transporter.from();

                if (fromSupplier.equals(supplier) && remainingDemand > 0) {
                    int availableCapacity = transporter.maxCapacity() - transporter.allocation();
                    int allocationAmount = Math.min(remainingDemand, availableCapacity);

                    transporter.setAllocation(transporter.allocation() + allocationAmount);

                    remainingDemand -= allocationAmount;
                    allocation.add(transporter);
                }
            }

            if (remainingDemand > 0) {
                System.err.println("Insufficient capacity to satisfy demand for " + supplier.name());
            }
        }

        return allocation;
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
