package comp3350.myshifts.tests.business;

import junit.framework.TestCase;

import java.util.ArrayList;

import comp3350.myshifts.application.Services;
import comp3350.myshifts.business.AccessSchedules;
import comp3350.myshifts.objects.Schedule;
import comp3350.myshifts.tests.persistence.DataAccessStub;
import comp3350.myshifts.persistence.Persistence;


public class AccessSchedulesTest extends TestCase
{
    private Persistence persistence;
    private AccessSchedules accessor;
    private ArrayList<Schedule> allSchedules;
    private Schedule schedule;

    public AccessSchedulesTest(String arg0)
    {
        super(arg0);
    }

    public void setUp()
    {
        persistence = Services.createDataAccess(new DataAccessStub());
        accessor = new AccessSchedules();
        allSchedules = accessor.getAllSchedules();
    }

    public void tearDown()
    {
        Services.closeDataAccess();
        accessor = null;
    }

    public void testAccessScheduleValidCreation()
    {
        System.out.println("\nTesting testAccessScheduleValidCreation");

        // 5 schedules already created
        accessor.createSchedule("Week 3", "June", "2017"); // 105
        accessor.createSchedule("Week 1", "September", "2017"); // 106
        accessor.createSchedule("Week 4", "August", "2017"); // 107

        //checking id 105, 106, 107
        schedule = accessor.getScheduleByID(105);
        assertEquals(105, schedule.getSchedID());

        schedule = accessor.getScheduleByID(106);
        assertEquals(106, schedule.getSchedID());

        schedule = accessor.getScheduleByID(107);
        assertEquals(107, schedule.getSchedID());

        // check for valid creation, check weeks first
        assertEquals("Week 3", accessor.getScheduleByID(105).getWeek());
        assertEquals("Week 1", accessor.getScheduleByID(106).getWeek());
        assertEquals("Week 4", accessor.getScheduleByID(107).getWeek());

        // check for valid creation, check months
        assertEquals("June", accessor.getScheduleByID(105).getMonth());
        assertEquals("September", accessor.getScheduleByID(106).getMonth());
        assertEquals("August", accessor.getScheduleByID(107).getMonth());

        // check for valid creation, check years
        assertEquals("2017", accessor.getScheduleByID(105).getYear());
        assertEquals("2017", accessor.getScheduleByID(106).getYear());
        assertEquals("2017", accessor.getScheduleByID(107).getYear());

        System.out.println("Finished testAccessScheduleValidCreation");
    }

    public void testAccessScheduleInvalidCreation()
    {
        System.out.println("\nTesting testAccessScheduleInvalidCreation");

        try
        {
            accessor.createSchedule("", "July", "2018");
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            accessor.createSchedule(null, "July", "2018");
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            accessor.createSchedule("1", "", "2018");
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            accessor.createSchedule("1", null, "2018");
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            accessor.createSchedule("1", "July", "");
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            accessor.createSchedule("1", "July", null);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        System.out.println("Finished testAccessScheduleInvalidCreation");
    }

    public void testAccessScheduleGetByID()
    {
        System.out.println("\nStarting testAccessScheduleGetByID");

        // test for valid access for existing schedules
        schedule = accessor.getScheduleByID(100);
        assertEquals(100, schedule.getSchedID());
        assertTrue(schedule.equals(allSchedules.get(0))); // 0 since the arraylist starts at 0

        schedule = accessor.getScheduleByID(101);
        assertEquals(101, schedule.getSchedID());
        assertTrue(schedule.equals(allSchedules.get(1)));

        schedule = accessor.getScheduleByID(102);
        assertEquals(102, schedule.getSchedID());
        assertTrue(schedule.equals(allSchedules.get(2)));

        // test for invalid access of nonexisting schedules
        schedule = accessor.getScheduleByID(0);
        assertNull(schedule);

        schedule = accessor.getScheduleByID(-1);
        assertNull(schedule);

        schedule = accessor.getScheduleByID(10000);
        assertNull(schedule);

        schedule = accessor.getScheduleByID(-10000);
        assertNull(schedule);

        System.out.println("Finished testAccessScheduleGetByID");
    }

    public void testAccessScheduleDelete()
    {
        System.out.println("\nStarting testAccessScheduleDelete");

        // test valid deletes
        accessor.deleteScheduleByID(101);
        assertNull(accessor.getScheduleByID(101));

        accessor.deleteScheduleByID(103);
        assertNull(accessor.getScheduleByID(103));

        // test invalid deletes
        assertFalse(accessor.deleteScheduleByID(-10000));
        assertFalse(accessor.deleteScheduleByID(-1));
        assertFalse(accessor.deleteScheduleByID(0));
        assertFalse(accessor.deleteScheduleByID(105));
        assertFalse(accessor.deleteScheduleByID(10000));

        System.out.println("Finished testAccessScheduleDelete");
    }

    public void testAccessScheduleValidUpdate()
    {
        System.out.println("\nStarting testAccessScheduleUpdateByID");

        // test valid updates
        schedule = accessor.getScheduleByID(100);
        assertTrue(accessor.updateScheduleByID(100, "Week 3", "April", "2018"));
        assertEquals("Week 3", schedule.getWeek());
        assertEquals("April", schedule.getMonth());
        assertEquals("2018", schedule.getYear());

        schedule = accessor.getScheduleByID(102);
        assertTrue(accessor.updateScheduleByID(102, "Week 4", "December", "2002"));
        assertEquals("Week 4", schedule.getWeek());
        assertEquals("December", schedule.getMonth());
        assertEquals("2002", schedule.getYear());

        schedule = accessor.getScheduleByID(101);
        assertTrue(accessor.updateScheduleByID(101, "Week 1", "August", "2007"));
        assertEquals("Week 1", schedule.getWeek());
        assertEquals("August", schedule.getMonth());
        assertEquals("2007", schedule.getYear());

        System.out.println("Finished testAccessScheduleUpdateByID");
    }

    public void testAccessScheduleInvalidUpdate()
    {
        System.out.println("\nStarting testAccessScheduleUpdateByID");

        // test invalid employees
        assertFalse(accessor.updateScheduleByID(-10000, "Week 3", "April", "2018"));
        assertFalse(accessor.updateScheduleByID(-1, "Week 3", "April", "2018"));
        assertFalse(accessor.updateScheduleByID(0, "Week 3", "April", "2018"));
        assertFalse(accessor.updateScheduleByID(105, "Week 3", "April", "2018"));
        assertFalse(accessor.updateScheduleByID(999, "Week 3", "April", "2018"));

        //test invalid week/months/year

        try
        {
            accessor.updateScheduleByID(100, "", "April", "2018");
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            accessor.updateScheduleByID(100, null, "April", "2018");
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            accessor.updateScheduleByID(100, "Week 3", "", "2018");
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            accessor.updateScheduleByID(100, "Week 3", null, "2018");
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            accessor.updateScheduleByID(100, "Week 3", "April", "");
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            accessor.updateScheduleByID(100, "Week 3", "April", null);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        System.out.println("Finished testAccessScheduleUpdateByID");
    }

    public void testGetSchedulesByEmployeeID()
    {
        System.out.println("\nStarting testGetSchedulesByEmployeeID");

        // first three default employees should have 1 schedule
        allSchedules = accessor.getSchedulesByEmployeeID(100);
        assertEquals(1, allSchedules.size());

        allSchedules = accessor.getSchedulesByEmployeeID(101);
        assertEquals(1, allSchedules.size());

        allSchedules = accessor.getSchedulesByEmployeeID(102);
        assertEquals(1, allSchedules.size());

        // eID 103 shouldn't return a schedule
        allSchedules = accessor.getSchedulesByEmployeeID(103);
        assertEquals(0, allSchedules.size());

        // get list for invalid schedules
        allSchedules = accessor.getSchedulesByEmployeeID(0);
        assertEquals(0, allSchedules.size());

        allSchedules = accessor.getSchedulesByEmployeeID(-1);
        assertEquals(0, allSchedules.size());

        allSchedules = accessor.getSchedulesByEmployeeID(-10000);
        assertEquals(0, allSchedules.size());

        allSchedules = accessor.getSchedulesByEmployeeID(10000);
        assertEquals(0, allSchedules.size());

        System.out.println("Finished testGetSchedulesByEmployeeID");
    }
}
