import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Map;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * Test scaffolding for class Allocator.
 * 
 * @author Dr. Jody Paul
 * @version 20231114
 */
public class SimpleAllocatorTest {
    // Data for tests.
    public Supplier man1, man2, depot, dist1, dist2, dist3, dist4, dist5;
    public Transporter manuship1, manuship2, transpA, transpB, transpC, transpD, transpE, transpF, transpG;
    public Collection<Supplier> sColl;
    public Collection<Transporter> tColl;

    /**
     * Constructor generates and saves serialized test data.
     */
    public SimpleAllocatorTest() {
        // Generate test data.
        Supplier manufacturer1 = new Supplier("Manufacturer1", 0, 0, 600, 1000);
        Supplier manufacturer2 = new Supplier("Manufacturer2", 0, 0, 00, 1000);
        Supplier depot = new Supplier("Depot", 0, 210, 0, 750);
        Supplier dist1 = new Supplier("Distributor 1", 1, 210, 0, 160);
        Supplier dist2 = new Supplier("Distributor 2", 2, 220, 0, 525);
        Supplier dist3 = new Supplier("Distributor 3", 3, 230, 0, 530);
        Supplier dist4 = new Supplier("Distributor 4", 4, 240, 0, 440);
        Supplier dist5 = new Supplier("Distributor 5", 5, 250, 0, 550);
        Transporter manuship1 = new Transporter("Shipping",
                manufacturer1,
                depot,
                4,
                Transporter.MAX_UNITS,
                600);
        Transporter manuship2 = new Transporter("Shipping",
                manufacturer2,
                dist1,
                9,
                Transporter.MAX_UNITS,
                600);
        Transporter transpA = new Transporter("Transporter A",
                depot,
                dist1,
                10,
                Transporter.MAX_UNITS,
                100);
        Transporter transpB = new Transporter("Transporter B",
                depot,
                dist2,
                6,
                Transporter.MAX_UNITS,
                400);
        Transporter transpC = new Transporter("Transporter C",
                dist1,
                dist3,
                8,
                Transporter.MAX_UNITS,
                400);
        Transporter transpD = new Transporter("Transporter D",
                dist1,
                dist5,
                2,
                Transporter.MAX_UNITS,
                400);
        Transporter transpE = new Transporter("Transporter E",
                dist2,
                dist3,
                3,
                Transporter.MAX_UNITS,
                100);
        Transporter transpF = new Transporter("Transporter G",
                dist2,
                dist4,
                7,
                Transporter.MAX_UNITS,
                40);
        Transporter transpG = new Transporter("Transporter G",
                dist3,
                dist4,
                5,
                Transporter.MAX_UNITS,
                90);
        try {
            manufacturer1.save("man1.ser");
            manufacturer2.save("man2.ser");
            depot.save("dep.ser");
            dist1.save("dist1.ser");
            dist2.save("dist2.ser");
            dist3.save("dist3.ser");
            dist4.save("dist4.ser");
            dist5.save("dist5.ser");
            transpA.save("trnA.ser");
            transpB.save("trnB.ser");
            transpC.save("trnC.ser");
            transpD.save("trnD.ser");
            transpE.save("trnE.ser");
            transpF.save("trnF.ser");
            transpG.save("trnG.ser");
        } catch (Exception e) {
            System.err.println("Exception when saving serialized data. " + e);
        }
    }

    /**
     * Sets up the test fixture by restoring
     * serialized suppliers and transporters.
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp() {
        man1 = new Supplier(); // Manufacturer
        man2 = new Supplier(); // Manufacturer
        depot = new Supplier(); // Depot
        dist1 = new Supplier(); // Distributor 1
        dist2 = new Supplier(); // Distributor 2
        dist3 = new Supplier(); // Distributor 3
        dist4 = new Supplier(); // Distributor 4
        dist5 = new Supplier(); // Distributor 5
        manuship1 = new Transporter(); // Shipping from manufacturer
        manuship2 = new Transporter();
        transpA = new Transporter();
        transpB = new Transporter();
        transpC = new Transporter();
        transpD = new Transporter();
        transpE = new Transporter();
        transpF = new Transporter();
        transpG = new Transporter();
        try {
            man1.restore("man1.ser");
            man2.restore("man2.ser");
            depot.restore("dep.ser");
            dist1.restore("dist1.ser");
            dist2.restore("dist2.ser");
            dist3.restore("dist3.ser");
            dist4.restore("dist4.ser");
            dist5.restore("dist5.ser");
            manuship1.restore("trn1.ser");
            manuship2.restore("trn2.ser");
            transpA.restore("trnA.ser");
            transpB.restore("trnB.ser");
            transpC.restore("trnC.ser");
            transpD.restore("trnD.ser");
            transpE.restore("trnE.ser");
            transpF.restore("trnF.ser");
            transpG.restore("trnG.ser");
        } catch (Exception e) {
            System.err.println("Exception when restoring test data. " + e);
        }
        if (!man1.name().equals("Manufacturer1")) {
            System.err.println("Restored test data corrupted.");
            System.exit(1);
        }
        sColl = new HashSet<Supplier>();
        sColl.add(man1);
        sColl.add(man2);
        sColl.add(depot);
        sColl.add(dist1);
        sColl.add(dist2);
        sColl.add(dist3);
        sColl.add(dist4);
        sColl.add(dist5);
        tColl = new HashSet<Transporter>();
        tColl.add(manuship1);
        tColl.add(manuship2);
        tColl.add(transpA);
        tColl.add(transpB);
        tColl.add(transpC);
        tColl.add(transpD);
        tColl.add(transpE);
        tColl.add(transpF);
        tColl.add(transpG);
    }

    /**
     * Tears down the test fixture, enabling garbage collection.
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown() {
        man1 = man2 = depot = dist1 = dist2 = dist3 = dist4 = dist5 = null;
        manuship1 = manuship2 = transpA = transpB = transpC = transpD = transpE = transpF = transpG = null;
    }

    @Test
    void CheapestPathTest() {
        Collection<Supplier> suppliers = new HashSet<>();
        Collection<Transporter> transporters = new HashSet<>();

        Supplier source = new Supplier("Source", 0, 0, 0, 50);
        Supplier destination = new Supplier("Destination", 10, 20, 0, 30);

        suppliers.add(source);
        suppliers.add(destination);

        Transporter path1 = new Transporter("Path1", source, destination, 10, 30, 0);
        Transporter path2 = new Transporter("Path2", source, destination, 15, 20, 0);
        Transporter path3 = new Transporter("Path3", destination, source, 5, 40, 0);

        transporters.add(path1);
        transporters.add(path2);
        transporters.add(path3);

        // Test the cheapestPath method
        Collection<Transporter> result = SimpleAllocator.cheapestPath(suppliers, transporters, source, destination);

        // Validate the result
        assertEquals(1, result.size()); // Expecting the cheapest path
        assertFalse(result.contains(path2)); // Expecting path2 to be the cheapest path
    }

    @Test
    public void AllocateForDemandTest() {
        // Create a test scenario with suppliers and transporters
        Collection<Supplier> suppliers = new HashSet<>();
        Collection<Transporter> transporters = new HashSet<>();

        Supplier source1 = new Supplier("Source1", 0, 0, 0, 20);
        Supplier source2 = new Supplier("Source2", 10, 20, 0, 30);
        Supplier destination1 = new Supplier("Destination1", 5, 5, 0, 40);
        Supplier destination2 = new Supplier("Destination2", 15, 15, 0, 50);

        suppliers.add(source1);
        suppliers.add(source2);
        suppliers.add(destination1);
        suppliers.add(destination2);

        Transporter path1 = new Transporter("Path1", source1, destination1, 10, 30, 0);
        Transporter path2 = new Transporter("Path2", source2, destination2, 15, 20, 0);
        Transporter path3 = new Transporter("Path3", source1, destination2, 5, 40, 0);

        transporters.add(path1);
        transporters.add(path2);
        transporters.add(path3);

        // Test the allocateForDemand method
        Collection<Transporter> result = SimpleAllocator.allocateForDemand(suppliers, transporters);

        // Validate the result
        assertEquals(1, result.size()); // Expecting allocation to two paths
        assertFalse(result.contains(path1)); // Expecting allocation to path1
        assertTrue(result.contains(path2)); // Expecting allocation to path2
    }

    @Test
    public void createAdjacencyListTest() {
        Collection<Supplier> suppliers = new HashSet<>();
        Collection<Transporter> transporters = new HashSet<>();

        Supplier supplier1 = new Supplier("Supplier 1", 0, 0, 0, 50);
        Supplier supplier2 = new Supplier("Supplier 2", 10, 20, 0, 30);
        Supplier supplier3 = new Supplier("Supplier 3", 5, 5, 0, 40);

        Transporter transporter1 = new Transporter("Transporter 1", supplier1, supplier2, 10, 30, 0);
        Transporter transporter2 = new Transporter("Transporter 2", supplier2, supplier3, 15, 20, 0);
        Transporter transporter3 = new Transporter("Transporter 3", supplier3, supplier1, 5, 40, 0);

        suppliers.add(supplier1);
        suppliers.add(supplier2);
        suppliers.add(supplier3);

        transporters.add(transporter1);
        transporters.add(transporter2);
        transporters.add(transporter3);

        Map<Supplier, List<Transporter>> adjacencyList = SimpleAllocator.createAdjacencyList(suppliers, transporters);

        // Validate the adjacency list
        assertEquals(3, adjacencyList.size()); // Expecting 3 suppliers in the adjacency list

        List<Transporter> supplier1Transporters = adjacencyList.get(supplier1);
        assertEquals(1, supplier1Transporters.size()); // Expecting 1 transporter for supplier1
        assertTrue(supplier1Transporters.contains(transporter1)); // Expecting transporter1 in the list

        List<Transporter> supplier2Transporters = adjacencyList.get(supplier2);
        assertEquals(1, supplier2Transporters.size()); // Expecting 1 transporter for supplier2
        assertTrue(supplier2Transporters.contains(transporter2)); // Expecting transporter2 in the list

        List<Transporter> supplier3Transporters = adjacencyList.get(supplier3);
        assertEquals(1, supplier3Transporters.size()); // Expecting 1 transporter for supplier3
        assertTrue(supplier3Transporters.contains(transporter3)); // Expecting transporter3 in the list
    }

    @Test
    public void totalDemandTest() {
        Collection<Supplier> suppliers = new HashSet<Supplier>();

        Supplier supplier1 = new Supplier("Supplier 1", 0, 0, 0, 50);
        Supplier supplier2 = new Supplier("Supplier 2", 10, 20, 0, 30);
        Supplier supplier3 = new Supplier("Supplier 3", 5, 5, 0, 40);

        suppliers.add(supplier1);
        suppliers.add(supplier2);
        suppliers.add(supplier3);

        int totalDemand = SimpleAllocator.totalDemand(suppliers);

        assertEquals(25, totalDemand); // Expecting total demand to be 50
    }

    @Test
    public void totalAmountShippedTest() {
        Collection<Transporter> transporters = new HashSet<Transporter>();

        Transporter transporter1 = new Transporter("Transporter 1", null, null, 10, 30, 0);
        Transporter transporter2 = new Transporter("Transporter 2", null, null, 15, 20, 0);
        Transporter transporter3 = new Transporter("Transporter 3", null, null, 5, 40, 0);

        transporters.add(transporter1);
        transporters.add(transporter2);
        transporters.add(transporter3);

        int totalAmountShipped = SimpleAllocator.totalAmountShipped(transporters);

        transporter1.setAllocation(10);
        transporter2.setAllocation(15);
        transporter3.setAllocation(5);

        assertEquals(0, totalAmountShipped); // Expecting total amount shipped to be 0
        totalAmountShipped = SimpleAllocator.totalAmountShipped(transporters);

        assertEquals(30, totalAmountShipped); // Expecting total amount shipped to be 30
        totalAmountShipped = SimpleAllocator.totalAmountShipped(transporters);

        assertEquals(30, totalAmountShipped); // Expecting total amount shipped to be 45
        totalAmountShipped = SimpleAllocator.totalAmountShipped(transporters);

        assertEquals(50, totalAmountShipped); // Expecting total amount shipped to be 50

    }

    @Test
    public void totalTransporterCostTest() {
        Collection<Transporter> transporters = new HashSet<Transporter>();

        Transporter transporter1 = new Transporter("Transporter 1", null, null, 10, 30, 0);
        Transporter transporter2 = new Transporter("Transporter 2", null, null, 15, 20, 0);
        Transporter transporter3 = new Transporter("Transporter 3", null, null, 5, 40, 0);

        transporters.add(transporter1);
        transporters.add(transporter2);
        transporters.add(transporter3);

        int totalTransporterCost = SimpleAllocator.totalTransporterCost(transporters);

        transporter1.setAllocation(10);
        transporter2.setAllocation(15);
        transporter3.setAllocation(5);

        assertEquals(0, totalTransporterCost); // Expecting total transporter cost to be 0
        totalTransporterCost = SimpleAllocator.totalTransporterCost(transporters);

        assertEquals(300, totalTransporterCost); // Expecting total transporter cost to be 300
        totalTransporterCost = SimpleAllocator.totalTransporterCost(transporters);

        assertEquals(500, totalTransporterCost); // Expecting total transporter cost to be 500
        totalTransporterCost = SimpleAllocator.totalTransporterCost(transporters);

        assertEquals(700, totalTransporterCost); // Expecting total transporter cost to be 700

    }

    /**
     * Demonstrate printing of string produced by displayAllocations.
     * Cannot test for full string because order is not specified.
     */
    @Test
    public void displayAllocationsTest() {

    }

}