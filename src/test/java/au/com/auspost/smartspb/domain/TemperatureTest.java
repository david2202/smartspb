package au.com.auspost.smartspb.domain;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertSame;


public class TemperatureTest {

    @Test
    public void testConstructor() {
        Temperature temp = Temperature.valueOf("21.1");
        assertEquals("21.1", temp.toString());
    }

    @Test
    public void testConstructorRoundUp() {
        Temperature temp = Temperature.valueOf("21.15");
        assertEquals("21.2", temp.toString());
    }

    @Test
    public void testConstructorRoundDown() {
        Temperature temp = Temperature.valueOf("21.14");
        assertEquals("21.1", temp.toString());
    }

    @Test
    public void testEquals() {
        Temperature t1 = Temperature.valueOf("21.5");
        Temperature t2 = Temperature.valueOf("21.5");
        assertEquals(t1, t2);
    }

    @Test
    public void testNotEquals() {
        Temperature t1 = Temperature.valueOf("21.5");
        Temperature t2 = Temperature.valueOf("22.5");
        assertNotEquals(t1, t2);
    }

    @Test
    public void testNotEqualsNull() {
        Temperature t1 = Temperature.valueOf("21.5");
        assertNotEquals(t1, null);
    }

    @Test
    public void testNotEqualsDifferentClass() {
        Temperature t1 = Temperature.valueOf("21.5");
        assertNotEquals(t1, new Object());
    }
}
