package comp3350.myshifts.tests.objects;

import junit.framework.TestCase;

import comp3350.myshifts.objects.Schedule;

public class ScheduleTest extends TestCase
{
    Schedule schedule;

    public ScheduleTest(String arg0)
    {
        super(arg0);
    }

    public void testValidScheduleCreation()
    {
        System.out.println("\nStarting testValidScheduleCreation");

        schedule = new Schedule("1", "Feb", "2017");
        assertEquals(-1, schedule.getSchedID());
        assertEquals("1", schedule.getWeek());
        assertEquals("Feb", schedule.getMonth());
        assertEquals("2017", schedule.getYear());

        System.out.println("Finished testValidScheduleCreation");
    }

    public void testInvalidScheduleCreation()
    {
        System.out.println("\nStarting testInvalidScheduleCreation");

        try
        {
            schedule = new Schedule(null, "Feb", "2017");
            fail("Expected an illegal argument exception.");
        }
        catch(IllegalArgumentException e)
        {
        }

        try
        {
            schedule = new Schedule("1", null, "2017");
            fail("Expected an illegal argument exception.");
        }
        catch(IllegalArgumentException e)
        {
        }

        try
        {
            schedule = new Schedule("1", "Feb", null);
            fail("Expected an illegal argument exception.");
        }
        catch(IllegalArgumentException e)
        {
        }

        System.out.println("Finished testInvalidScheduleCreation");
    }

    public void testValidScheduleUpdate()
    {
        System.out.println("\nStarting testValidScheduleUpdate");

        schedule = new Schedule("1", "Feb", "2017");

        schedule.setWeek("4");
        schedule.setMonth("Dec");
        schedule.setYear("2018");

        assertEquals("4", schedule.getWeek());
        assertEquals("Dec", schedule.getMonth());
        assertEquals("2018", schedule.getYear());

        System.out.println("Finished testValidScheduleUpdate");
    }

    public void testInvalidScheduleUpdate()
    {
        System.out.println("\nStarting testInvalidScheduleUpdate");

        schedule = new Schedule("1", "Feb", "2017");

        try
        {
            schedule.setWeek("");
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            schedule.setWeek(null);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            schedule.setMonth("");
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            schedule.setMonth(null);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            schedule.setYear("");
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            schedule.setYear(null);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        System.out.println("Finished testInvalidScheduleUpdate");
    }
}
