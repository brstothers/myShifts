package comp3350.myshifts.tests.objects;

import junit.framework.TestCase;

import comp3350.myshifts.objects.ScheduleSummary;

public class ScheduleSummaryTest extends TestCase
{
    ScheduleSummary scheduleSummary;

    public ScheduleSummaryTest(String arg0)
    {
        super(arg0);
    }

    public void testScheduleSummary()
    {
        System.out.println("\nStarting testScheduleSummary");

        scheduleSummary = new ScheduleSummary("Week 1", "April", "2017", 100, 1, 1, 10.0, 150.0);

        assertEquals("Week 1", scheduleSummary.getSchedWeek());
        assertEquals("April", scheduleSummary.getSchedMonth());
        assertEquals("2017", scheduleSummary.getSchedYear());
        assertEquals(100, scheduleSummary.getScheduleID());
        assertEquals(1, scheduleSummary.getNumShifts());
        assertEquals(1, scheduleSummary.getNumEmployees());
        assertEquals(10.0, scheduleSummary.getTotalHours());
        assertEquals(150.0, scheduleSummary.getTotalPayroll());

        System.out.println("Finished testScheduleSummary");
    }

    public void testInvalidScheduleSummary()
    {
        System.out.println("\nStarting testScheduleSummary");

        try
        {
            scheduleSummary = new ScheduleSummary("", "April", "2017", 100, 1, 1, 10.0, 150.0);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            scheduleSummary = new ScheduleSummary(null, "April", "2017", 100, 1, 1, 10.0, 150.0);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            scheduleSummary = new ScheduleSummary("Week 1", "", "2017", 100, 1, 1, 10.0, 150.0);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            scheduleSummary = new ScheduleSummary("Week 1", null, "2017", 100, 1, 1, 10.0, 150.0);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            scheduleSummary = new ScheduleSummary("Week 1", "April", "", 100, 1, 1, 10.0, 150.0);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            scheduleSummary = new ScheduleSummary("Week 1", "April", null, 100, 1, 1, 10.0, 150.0);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            scheduleSummary = new ScheduleSummary("Week 1", "April", "2017", -100, 1, 1, 10.0, 150.0);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            scheduleSummary = new ScheduleSummary("Week 1", "April", "2017", 100, -1, 1, 10.0, 150.0);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            scheduleSummary = new ScheduleSummary("Week 1", "April", "2017", 100, 1, -1, 10.0, 150.0);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            scheduleSummary = new ScheduleSummary("Week 1", "April", "2017", 100, 1, 1, -10.0, 150.0);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            scheduleSummary = new ScheduleSummary("Week 1", "April", "2017", 100, 1, 1, 10.0, -150.0);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        System.out.println("Finished testScheduleSummary");
    }
}
