import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
/**
 * Tests for class Supplier.
 *
 * @author  Dr. Jody Paul
 * @version 20231107a
 */
public class SupplierTest {
    /**
     * Default constructor for test class SupplierTest
     */
    public SupplierTest() {
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
        Supplier supplier1 = new Supplier();
        int whereIsAt = supplier1.name().indexOf('@');
        if (whereIsAt != -1) {
            assertEquals("Supplier", supplier1.name().substring(0, whereIsAt));
        } else {
            assertTrue(supplier1.name().length() > 0);
        }
        assertEquals(0, supplier1.storageCost());
        assertEquals(0, supplier1.inventory());
        assertEquals(0, supplier1.demand());
        assertEquals(0, supplier1.surplus());
        assertEquals(Supplier.MAX_CAPACITY, supplier1.maxCapacity());
    }

    @Test
    public void AccessorTests_ParameterizedConstruction() {
        Supplier supplier1 = new Supplier("Test Supplier 1", // name
                                          13, // storage cost
                                          97, // demand
                                          37, // inventory
                                          150); // maximum capacity
        assertEquals("Test Supplier 1", supplier1.name());
        assertEquals(13, supplier1.storageCost());
        assertEquals(150, supplier1.maxCapacity());
        assertEquals(37, supplier1.inventory());
        assertEquals(97, supplier1.demand());
        assertEquals(-60, supplier1.surplus());
    }

    @Test
    public void equalsAndHashCodeTest() {
        Supplier supplier1 = new Supplier("My Name", 42, 300, 0, 149);
        Supplier supplier2 = new Supplier("My Name", 42, 300, 0, 149);
        Supplier supplier3 = new Supplier();
        Supplier supplier4 = new Supplier();
        assertEquals(true, supplier1.equals(supplier2));
        assertEquals(false, supplier1.equals(supplier3));
        assertEquals(false, supplier2.equals(supplier3));
        assertEquals(true, supplier3.equals(supplier4));
        assertFalse(supplier1 == supplier2);
        assertFalse(supplier3 == supplier4);
        assertTrue(supplier1.hashCode() == supplier2.hashCode());
        assertTrue(supplier3.hashCode() == supplier4.hashCode());
    }

    @Test
    public void SaveRestoreTest() {
        Supplier supplier1 = new Supplier("SP Name", 35, 100, 85, 171);
        Supplier supplier2 = new Supplier();
        assertNotEquals(supplier1, supplier2);
        assertNotEquals("SP Name", supplier2.name());
        try {
            assertTrue(supplier1.save("suptest.ser"));
        } catch (Exception e) {
            fail("Exception when attempting save");
        }
        assertNotEquals(supplier1, supplier2);
        assertNotEquals("SP Name", supplier2.name());
        try {
            assertTrue(supplier2.restore("suptest.ser"));
        } catch (Exception e) {
            fail("Exception when attempting restore");
        }
        assertEquals("SP Name", supplier2.name());
        assertEquals(supplier1, supplier2);
    }
}
