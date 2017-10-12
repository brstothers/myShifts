package comp3350.myshifts.tests.integration;

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

public class BusinessPersistenceSeamTest extends TestCase
{
    private AccessEmployees accessEmployees;
    private AccessSchedules accessSchedules;
    private AccessShifts accessShifts;

    private ArrayList<Employee> employees;
    private ArrayList<Schedule> schedules;
    private ArrayList<Shift> shifts;

    private Employee employee;
    private Schedule schedule;
    private Shift shift;

    public BusinessPersistenceSeamTest(String arg0)
    {
        super(arg0);
    }

    public void setUp()
    {
        Services.closeDataAccess();
        Services.createDataAccess(Main.dbName);
    }

    public void testAccessEmployeesSeam()
    {
        System.out.println("\nStarting testAccessEmployeesSeam");

        accessEmployees = new AccessEmployees();
        employees = accessEmployees.getAllEmployees();

        assertEquals(5, employees.size());

        accessEmployees.createEmployee("Deckard", "2042222222", 32.74); // eID should be 105

        employees = accessEmployees.getAllEmployees();
        assertEquals(6, employees.size());
        employee = accessEmployees.getEmployeeByID(105);

        assertEquals("Deckard", employee.getEmployeeName());
        assertEquals("2042222222", employee.getEmployeePhone());
        assertEquals(32.74, employee.getEmployeeWage());

        assertTrue(accessEmployees.updateEmployeeByID(105, "Roy Batty", "1234567890", 21.85));
        employee = accessEmployees.getEmployeeByID(105);

        assertEquals("Roy Batty", employee.getEmployeeName());
        assertEquals("1234567890", employee.getEmployeePhone());
        assertEquals(21.85, employee.getEmployeeWage());

        accessEmployees.deleteEmployeeByID(105);

        employees = accessEmployees.getAllEmployees();
        assertEquals(5, employees.size());
        assertNull(accessEmployees.getEmployeeByID(105));

        assertFalse(accessEmployees.deleteEmployeeByID(-1000));
        assertFalse(accessEmployees.deleteEmployeeByID(-1));
        assertFalse(accessEmployees.deleteEmployeeByID(105));
        assertFalse(accessEmployees.deleteEmployeeByID(9999));

        assertFalse(accessEmployees.updateEmployeeByID(-1000, "Ryan Gosling", "1234567890", 74.28));
        assertFalse(accessEmployees.updateEmployeeByID(-1, "Ryan Gosling", "1234567890", 74.28));
        assertFalse(accessEmployees.updateEmployeeByID(105, "Ryan Gosling", "1234567890", 74.28));
        assertFalse(accessEmployees.updateEmployeeByID(9574, "Ryan Gosling", "1234567890", 74.28));

        try
        {
            accessEmployees.createEmployee(null, "2042222222", 15.67);
            fail("Expected an illegal argument exception.");
        }
        catch(IllegalArgumentException iae)
        {
        }

        try
        {
            accessEmployees.updateEmployeeByID(100, "Huh?", null, 10.50);
            fail("Expected an illegal argument exception.");
        }
        catch(IllegalArgumentException iae)
        {
        }

        System.out.println("Finished testAccessEmployeesSeam");
    }

    public void testAccessSchedulesSeam()
    {
        System.out.println("\nStarting testAccessSchedulesSeam");

        accessSchedules = new AccessSchedules();
        schedules = accessSchedules.getAllSchedules();

        assertEquals(5, schedules.size());

        accessSchedules.createSchedule("1", "Feb", "2017"); // should have sID of 105

        schedules = accessSchedules.getAllSchedules();
        assertEquals(6, schedules.size());

        schedule = accessSchedules.getScheduleByID(105);

        assertEquals("1", schedule.getWeek());
        assertEquals("Feb", schedule.getMonth());
        assertEquals("2017", schedule.getYear());

        accessSchedules.updateScheduleByID(105, "3", "March", "2018");
        schedule = accessSchedules.getScheduleByID(105);

        assertEquals("3", schedule.getWeek());
        assertEquals("March", schedule.getMonth());
        assertEquals("2018", schedule.getYear());

        accessSchedules.deleteScheduleByID(105);
        schedules = accessSchedules.getAllSchedules();
        assertEquals(5, schedules.size());
        assertNull(accessSchedules.getScheduleByID(105));

        assertFalse(accessSchedules.deleteScheduleByID(-1000));
        assertFalse(accessSchedules.deleteScheduleByID(-1));
        assertFalse(accessSchedules.deleteScheduleByID(105));
        assertFalse(accessSchedules.deleteScheduleByID(9999));

        assertFalse(accessSchedules.updateScheduleByID(-1000, "2", "April", "2017"));
        assertFalse(accessSchedules.updateScheduleByID(-1, "2", "April", "2017"));
        assertFalse(accessSchedules.updateScheduleByID(105, "2", "April", "2017"));
        assertFalse(accessSchedules.updateScheduleByID(9574, "2", "April", "2017"));

        try
        {
            accessSchedules.createSchedule(null, "Test", "Fail");
            fail("Expected an illegal argument exception.");
        }
        catch(IllegalArgumentException iae)
        {
        }

        try
        {
            accessSchedules.updateScheduleByID(105, "Test", null, "Fail");
            fail("Expected an illegal argument exception.");
        }
        catch(IllegalArgumentException iae)
        {
        }

        System.out.println("Finished testAccessSchedulesSeam");
    }

    public void testAccessShiftsSeam()
    {
        System.out.println("\nStarting testAccessShiftsSeam");

        accessShifts = new AccessShifts();
        shifts = accessShifts.getAllShifts();

        assertEquals(5, shifts.size());

        accessShifts.createShift(100, 101, Weekday.MON, 4.0, 9.0); // should have sID of 105

        shifts = accessShifts.getAllShifts();
        assertEquals(6, shifts.size());

        shift = accessShifts.getShiftByID(100, 101, Weekday.MON);

        assertEquals(100, shift.getEmployeeID());
        assertEquals(101, shift.getScheduleID());
        assertEquals(Weekday.MON, shift.getWeekday());
        assertEquals(4.0, shift.getStartTime());
        assertEquals(9.0, shift.getEndTime());

        accessShifts.updateShiftbyID(100, 101, Weekday.MON, 5.0, 10.0);
        shift = accessShifts.getShiftByID(100, 101, Weekday.MON);

        assertEquals(100, shift.getEmployeeID());
        assertEquals(101, shift.getScheduleID());
        assertEquals(Weekday.MON, shift.getWeekday());
        assertEquals(5.0, shift.getStartTime());
        assertEquals(10.0, shift.getEndTime());

        accessShifts.deleteShiftbyID(100, 101, Weekday.MON);
        shifts = accessShifts.getAllShifts();
        assertEquals(5, shifts.size());
        assertNull(accessShifts.getShiftByID(100, 101, Weekday.MON));

        assertFalse(accessShifts.deleteShiftbyID(-1000, 100, Weekday.MON));
        assertFalse(accessShifts.deleteShiftbyID(-1, 100, Weekday.MON));
        assertFalse(accessShifts.deleteShiftbyID(105, 100, Weekday.MON));
        assertFalse(accessShifts.deleteShiftbyID(5000, 100, Weekday.MON));

        assertFalse(accessShifts.deleteShiftbyID(100, -1000, Weekday.MON));
        assertFalse(accessShifts.deleteShiftbyID(100, -1, Weekday.MON));
        assertFalse(accessShifts.deleteShiftbyID(100, 105, Weekday.MON));
        assertFalse(accessShifts.deleteShiftbyID(100, 7060, Weekday.MON));

        assertFalse(accessShifts.updateShiftbyID(100, -1000, Weekday.MON, 10.0, 18.0));
        assertFalse(accessShifts.updateShiftbyID(100, -1, Weekday.MON, 10.0, 18.0));
        assertFalse(accessShifts.updateShiftbyID(100, 105, Weekday.MON, 10.0, 18.0));
        assertFalse(accessShifts.updateShiftbyID(100, 8436, Weekday.MON, 10.0, 18.0));

        assertFalse(accessShifts.updateShiftbyID(-1000, 100, Weekday.MON, 10.0, 18.0));
        assertFalse(accessShifts.updateShiftbyID(-1, 100, Weekday.MON, 10.0, 18.0));
        assertFalse(accessShifts.updateShiftbyID(105, 100, Weekday.MON, 10.0, 18.0));
        assertFalse(accessShifts.updateShiftbyID(8436, 100, Weekday.MON, 10.0, 18.0));

        try
        {
            accessShifts.createShift(100, 100, Weekday.SUN, -15.0, 2.0);
            fail("Expected an illegal argument exception.");
        }
        catch(IllegalArgumentException iae)
        {
        }

        try
        {
            accessShifts.createShift(100, 100, Weekday.FRI, 15.0, -2.0);
            fail("Expected an illegal argument exception.");
        }
        catch(IllegalArgumentException iae)
        {
        }

        try
        {
            accessShifts.updateShiftbyID(100, 100, Weekday.FRI, -5.0, 12.0);
            fail("Expected an illegal argument exception.");
        }
        catch(IllegalArgumentException iae)
        {
        }

        accessShifts.createShift(104, 101, Weekday.FRI, 8.0, 16.0);

        shifts = accessShifts.getShiftsByEmployeeID(104);
        assertEquals(1, shifts.size());
        assertEquals(104, shifts.get(0).getEmployeeID());
        assertEquals(101, shifts.get(0).getScheduleID());
        assertEquals(Weekday.FRI, shifts.get(0).getWeekday());
        assertEquals(8.0, shifts.get(0).getStartTime());
        assertEquals(16.0, shifts.get(0).getEndTime());

        assertTrue(accessShifts.deleteShiftbyID(104, 101, Weekday.FRI));

        System.out.println("Finished testAccessShiftsSeam");
    }
}
