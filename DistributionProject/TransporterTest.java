import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
/**
 * Tests for class Transporter.
 *
 * @author  Dr. Jody Paul
 * @version 20231107
 */
public class TransporterTest {
    /**
     * Default constructor for test class TransporterTest
     */
    public TransporterTest() {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp() {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void AccessorTests_DefaultConstruction() {
        Transporter transporter1 = new Transporter();
        int whereIsAt = transporter1.name().indexOf('@');
        if (whereIsAt != -1) {
            assertEquals("Transporter", transporter1.name().substring(0, whereIsAt));
        } else {
            assertTrue(transporter1.name().length() > 0);
        }
        assertNull(transporter1.from());
        assertNull(transporter1.to());
        assertEquals(transporter1.costPerUnit(), 0);
        assertEquals(transporter1.maxCapacity(), Transporter.MAX_UNITS);
    }

    @Test
    public void AccessorTests_ParameterizedConstruction() {
        Transporter transporter1 = new Transporter("Test Transport",
                                                   new Supplier("S From", 5, 6, 7, 8),
                                                   new Supplier("S To", 11, 12, 13, 14),
                                                   33,
                                                   57,
                                                   19);
        assertEquals("Test Transport", transporter1.name());
        assertEquals("S From", transporter1.from().name());
        assertEquals("S To", transporter1.to().name());
        assertEquals(33, transporter1.costPerUnit());
        assertEquals(57, transporter1.maxCapacity());
        assertEquals(19, transporter1.allocation());
    }

    @Test
    public void equalsAndHashCodeTest() {
        Transporter transporter1 = new Transporter("XPTR Name",
                                                   new Supplier("M", 0, 0, 300, 499),
                                                   new Supplier("D", 10, 42, 50, 127),
                                                   35,
                                                   Transporter.MAX_UNITS,
                                                   0);
        Transporter transporter2 = new Transporter("XPTR Name",
                                                   new Supplier("M", 0, 0, 300, 499),
                                                   new Supplier("D", 10, 42, 50, 127),
                                                   35,
                                                   Transporter.MAX_UNITS,
                                                   0);
        Transporter transporter3 = new Transporter();
        Transporter transporter4 = new Transporter();
        assertEquals(transporter1, transporter2);
        assertEquals(transporter3, transporter4);
        assertNotEquals(transporter1, transporter3);
        assertNotEquals(transporter1, transporter4);
        assertFalse(transporter1 == transporter2);
        assertFalse(transporter3 == transporter4);
        assertTrue(transporter1.hashCode() == transporter2.hashCode());
        assertTrue(transporter3.hashCode() == transporter4.hashCode());
    }

    @Test
    public void allocationMutatorTest() {
        Transporter transporter1 = new Transporter();
        assertEquals(0, transporter1.allocation());
        transporter1.setAllocation(42);
        assertEquals(42, transporter1.allocation());
        transporter1.setAllocation(310);
        assertEquals(310, transporter1.allocation());
    }


    @Test
    public void SaveRestoreTest() {
        Transporter transporter1 = new Transporter("XPTR Name",
                                                   new Supplier("M", 0, 0, 300, 499),
                                                   new Supplier("D", 10, 42, 50, 127),
                                                   35,
                                                   Transporter.MAX_UNITS,
                                                   0);
        Transporter transporter2 = new Transporter();
        assertNotEquals(transporter1, transporter2);
        assertNotEquals("XPTR Name", transporter2.name());
        try {
            assertTrue(transporter1.save("xptrtest.ser"));
        } catch (Exception e) {
            fail("Exception when attempting save");
        }
        assertNotEquals(transporter1, transporter2);
        assertNotEquals("XPTR Name", transporter2.name());
        try {
            assertTrue(transporter2.restore("xptrtest.ser"));
        } catch (Exception e) {
            fail("Exception when attempting restore");
        }
        assertEquals("XPTR Name", transporter2.name());
        assertEquals(transporter1, transporter2);
    }
}
