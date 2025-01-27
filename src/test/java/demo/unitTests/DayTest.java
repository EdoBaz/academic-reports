package demo.unitTests;



import static org.junit.Assert.*;

import demo.model.Day;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;


public class DayTest {

    private Day day;

    @Before
    public void setUp() {
        day = new Day(LocalDate.of(2025, 1, 10), 8, false);
    }

    @Test
    public void testConstructor() {
        assertEquals(LocalDate.of(2025, 1, 10), day.getDate());
        assertEquals(8, day.getHours());
        assertFalse(day.isHoliday());
    }

    @Test
    public void testSetAndGetDate() {
        day.setDate(LocalDate.of(2025, 1, 11));
        assertEquals(LocalDate.of(2025, 1, 11), day.getDate());
    }

    @Test
    public void testSetAndGetHours() {
        day.setHours(10);
        assertEquals(10, day.getHours());
    }

    @Test
    public void testSetAndGetHoliday() {
        day.setHoliday(true);
        assertTrue(day.isHoliday());
    }
}