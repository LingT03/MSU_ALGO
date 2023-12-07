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
import java.util.LinkedList;
import java.util.Queue;

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

    public static Collection<Transporter> allocateForDemand(Collection<Supplier> suppliers,
            Collection<Transporter> transporters) {
        if (suppliers == null || transporters == null) {
            return Collections.emptyList();
        }

        // Create a graph representing the flow network
        Map<String, Map<String, Integer>> graph = new HashMap<>();
        int totalDemand = 0;

        // Add suppliers as sources and calculate total demand
        for (Supplier supplier : suppliers) {
            graph.put(supplier.name(), new HashMap<>());
            totalDemand += supplier.demand();
        }

        // Add transporters as intermediate nodes
        for (Transporter transporter : transporters) {
            graph.put(transporter.name(), new HashMap<>());
        }

        // Add edges from suppliers to transporters with infinite capacity
        for (Supplier supplier : suppliers) {
            for (Transporter transporter : transporters) {
                graph.get(supplier.name()).put(transporter.name(), Integer.MAX_VALUE);
            }
        }

        // Add edges from transporters to destinations with capacity equal to
        // transporter allocation
        for (Transporter transporter : transporters) {
            graph.get(transporter.name()).put(transporter.to().name(), transporter.allocation());
        }

        // Implement Ford-Fulkerson algorithm
        Map<String, Map<String, Integer>> residualGraph = new HashMap<>(graph);

        while (true) {
            Map<String, String> parentMap = new HashMap<>();
            Queue<String> queue = new LinkedList<>();
            queue.add(suppliers.iterator().next().name());

            while (!queue.isEmpty()) {
                String current = queue.poll();

                for (String neighbor : residualGraph.get(current).keySet()) {
                    if (!parentMap.containsKey(neighbor) && residualGraph.get(current).get(neighbor) > 0) {
                        parentMap.put(neighbor, current);
                        queue.add(neighbor);
                    }
                }
            }

            // If there are no augmenting paths, break
            if (!parentMap.containsKey(transporters.iterator().next().name())) {
                break;
            }

            // Find bottleneck capacity in the augmenting path
            int bottleneck = Integer.MAX_VALUE;
            String node = transporters.iterator().next().name(); // Target node

            while (parentMap.containsKey(node)) {
                String parent = parentMap.get(node);
                bottleneck = Math.min(bottleneck, residualGraph.get(parent).get(node));
                node = parent;
            }

            // Update residual capacities
            node = transporters.iterator().next().name();

            while (parentMap.containsKey(node)) {
                String parent = parentMap.get(node);
                residualGraph.get(parent).put(node, residualGraph.get(parent).get(node) - bottleneck);
                residualGraph.get(node).put(parent, residualGraph.get(node).get(parent) + bottleneck);
                node = parent;
            }
        }

        // Extract allocated transporters
        List<Transporter> allocatedTransporters = new ArrayList<>();

        for (Transporter transporter : transporters) {
            int remainingCapacity = residualGraph.get(transporter.name()).get(transporter.to().name());

            if (remainingCapacity < transporter.allocation()) {
                // The transporter was used in the flow
                int allocatedAmount = transporter.allocation() - remainingCapacity;
                allocatedTransporters.add(new Transporter(
                        transporter.name(),
                        transporter.from(),
                        transporter.to(),
                        transporter.costPerUnit(),
                        transporter.maxCapacity(),
                        allocatedAmount));
            }
        }

        return allocatedTransporters;
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
