package comp3350.myshifts.tests.business;

import junit.framework.TestCase;

import java.util.ArrayList;

import comp3350.myshifts.application.Main;
import comp3350.myshifts.application.Services;
import comp3350.myshifts.business.AccessEmployees;
import comp3350.myshifts.business.AccessSchedules;
import comp3350.myshifts.business.AccessShifts;
import comp3350.myshifts.objects.Employee;
import comp3350.myshifts.objects.Schedule;
import comp3350.myshifts.objects.Shift;
import comp3350.myshifts.objects.Weekday;
import comp3350.myshifts.tests.persistence.DataAccessStub;
import comp3350.myshifts.persistence.Persistence;

public class AccessShiftsTest extends TestCase
{
    public AccessShiftsTest(String arg0)
    {
        super(arg0);
    }

    private Persistence persistence;
    private AccessShifts shiftAccessor;
    private AccessSchedules scheduleAccessor;
    private AccessEmployees employeeAccessor;
    private ArrayList<Employee> allEmployees;
    private ArrayList<Schedule> allSchedules;
    private ArrayList<Shift> allShifts;
    private Shift shift1, shift2;

    public void setUp()
    {
        persistence = Services.createDataAccess(new DataAccessStub());
        shiftAccessor = new AccessShifts();
        employeeAccessor = new AccessEmployees();
        scheduleAccessor = new AccessSchedules();
        allShifts = shiftAccessor.getAllShifts();
        allEmployees = employeeAccessor.getAllEmployees();
        allSchedules = scheduleAccessor.getAllSchedules();

    }

    public void tearDown()
    {
        shiftAccessor = null;
        allEmployees = null;
        allSchedules = null;
        Services.closeDataAccess();
    }

    public void testScheduleAccess()
    {
        System.out.println("\nTesting testScheduleAccess");

        // Test creation of valid shift with first employee and schedule
        shift1 = shiftAccessor.createShift(100, 100, Weekday.MON, 10, 15);
        shift2 = shiftAccessor.getShiftByID(100, 100, Weekday.MON);
        assertEquals(100, shift1.getEmployeeID());
        assertEquals(100, shift1.getScheduleID());
        assertEquals(100, shift2.getEmployeeID());
        assertEquals(100, shift2.getScheduleID());
        assertEquals(10.0, shift2.getStartTime());
        assertEquals(15.0, shift2.getEndTime());

        assertTrue(shift2.equals(shift1));

        // make sure you cannot add duplicate shifts
        assertNull(shiftAccessor.createShift(100, 100, Weekday.MON, 10, 15));

        //check to make sure update works correctly
        assertTrue(shiftAccessor.updateShiftbyID(100, 100, Weekday.MON, 5, 10));
        shift2 = shiftAccessor.getShiftByID(100, 100, Weekday.MON);
        assertEquals(5.0, shift2.getStartTime());
        assertEquals(10.0, shift2.getEndTime());

        //make sure deletion worked
        assertTrue(shiftAccessor.deleteShiftbyID(100, 100, Weekday.MON));
        assertNull(shiftAccessor.getShiftByID(100, 100, Weekday.MON));

        //make sure you can't delete non-existant employees
        assertFalse(shiftAccessor.deleteShiftbyID(100, 100, Weekday.THR)); // check the deleted employee
        assertFalse(shiftAccessor.deleteShiftbyID(-1000, 100, Weekday.THR));
        assertFalse(shiftAccessor.deleteShiftbyID(-2, 100, Weekday.THR));
        assertFalse(shiftAccessor.deleteShiftbyID(0, 100, Weekday.THR));
        assertFalse(shiftAccessor.deleteShiftbyID(1000, 100, Weekday.THR));

        //make sure you can't update non-existant employees
        assertFalse(shiftAccessor.updateShiftbyID(-1000, 100, Weekday.THR, 2, 8));
        assertFalse(shiftAccessor.updateShiftbyID(-2, 100, Weekday.THR, 2, 8));
        assertFalse(shiftAccessor.updateShiftbyID(0, 100, Weekday.THR, 2, 8));
        assertFalse(shiftAccessor.updateShiftbyID(1000, 100, Weekday.THR, 2, 8));

        // re-add initial shift
        shift1 = shiftAccessor.createShift(100, 100, Weekday.MON, 10, 15);
        assertEquals(100, shift1.getEmployeeID());
        assertEquals(100, shift1.getScheduleID());

        System.out.println("Finished testScheduleAccess");
    }

    public void testInvalidShiftAccessUpdate()
    {
        System.out.println("\nStarted testInvalidShiftAccessUpdate");

        assertFalse(shiftAccessor.updateShiftbyID(-100, 100, Weekday.FRI, 10, 15));
        assertFalse(shiftAccessor.updateShiftbyID(100, -100, Weekday.FRI, 10, 15));

        try
        {
            shiftAccessor.updateShiftbyID(100, 100, Weekday.FRI, -10, 15);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            shiftAccessor.updateShiftbyID(100, 100, Weekday.FRI, 10, -15);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            shiftAccessor.updateShiftbyID(100, 100, Weekday.FRI, 30, 15);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            shiftAccessor.updateShiftbyID(100, 100, Weekday.FRI, 10, 30);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        System.out.println("Finished testInvalidShiftAccessUpdate");
    }

    public void testInvalidScheduleAddition()
    {
        System.out.println("\nStarting testInvalidScheduleAddition");

        try
        {
            shiftAccessor.createShift(-100, 100, Weekday.FRI, 2, 10);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            shiftAccessor.createShift(100, -100, Weekday.FRI, 2, 10);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            shiftAccessor.createShift(-100, 100, Weekday.FRI, -2, 10);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            shiftAccessor.createShift(-100, 100, Weekday.FRI, 2, -10);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            shiftAccessor.createShift(-100, 100, Weekday.FRI, 30, -10);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            shiftAccessor.createShift(-100, 100, Weekday.FRI, 2, 30);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        System.out.println("Finished testInvalidScheduleAddition");
    }

    public void testGetShiftsByEmployeeID()
    {
        System.out.println("\nTesting testGetShiftsByEmployeeID");

        // get list for employee with no shifts, we never seed a shift for employee 3
        allShifts = shiftAccessor.getShiftsByEmployeeID(allEmployees.get(3).getEmployeeID());
        assertEquals(0, allShifts.size());

        // get list for invalid schedules
        allShifts = shiftAccessor.getShiftsByEmployeeID(1000);
        assertEquals(0, allShifts.size());

        allShifts = shiftAccessor.getShiftsByEmployeeID(-2);
        assertEquals(0, allShifts.size());

        allShifts = shiftAccessor.getShiftsByEmployeeID(-1000);
        assertEquals(0, allShifts.size());

        allShifts = shiftAccessor.getShiftsByEmployeeID(105);
        assertEquals(0, allShifts.size());

        // get list for employee with some shifts
        allShifts = shiftAccessor.getShiftsByEmployeeID(allEmployees.get(0).getEmployeeID());
        assertEquals(2, allShifts.size());
        assertEquals(100, allShifts.get(0).getEmployeeID()); // should have eID of 100
        assertEquals(100, allShifts.get(1).getEmployeeID());

        allShifts = shiftAccessor.getShiftsByEmployeeID(allEmployees.get(1).getEmployeeID());
        assertEquals(2, allShifts.size());
        assertEquals(101, allShifts.get(0).getEmployeeID()); // eID of 101
        assertEquals(101, allShifts.get(1).getEmployeeID());

        System.out.println("Finished testGetShiftsByEmployeeID");
    }

    public void testGetShiftsByScheduleID()
    {
        System.out.println("\nTesting testGetShiftsByScheduleID");

        // get list for employee with no shifts, we never seed a shift for employee 3
        allShifts = shiftAccessor.getShiftsByScheduleID(allSchedules.get(3).getSchedID());
        assertEquals(0, allShifts.size());

        // get list for invalid schedules
        allShifts = shiftAccessor.getShiftsByScheduleID(1000);
        assertEquals(0, allShifts.size());

        allShifts = shiftAccessor.getShiftsByScheduleID(-2);
        assertEquals(0, allShifts.size());

        allShifts = shiftAccessor.getShiftsByScheduleID(-1000);
        assertEquals(0, allShifts.size());

        allShifts = shiftAccessor.getShiftsByScheduleID(105);
        assertEquals(0, allShifts.size());

        // get list for employee with some shifts
        allShifts = shiftAccessor.getShiftsByScheduleID(allSchedules.get(0).getSchedID());
        assertEquals(2, allShifts.size());
        assertEquals(100, allShifts.get(0).getScheduleID());
        assertEquals(100, allShifts.get(1).getScheduleID());

        allShifts = shiftAccessor.getShiftsByScheduleID(allSchedules.get(1).getSchedID());
        assertEquals(2, allShifts.size());
        assertEquals(101, allShifts.get(0).getScheduleID());
        assertEquals(101, allShifts.get(1).getScheduleID());

        System.out.println("Finished testGetShiftsByEmployeeID");
    }
}
