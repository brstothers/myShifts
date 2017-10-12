package comp3350.myshifts.tests.business;

import junit.framework.TestCase;

import java.util.ArrayList;

import comp3350.myshifts.application.Services;
import comp3350.myshifts.business.AccessShifts;
import comp3350.myshifts.business.Summarize;
import comp3350.myshifts.objects.EmployeeSummary;
import comp3350.myshifts.objects.ScheduleSummary;
import comp3350.myshifts.objects.Weekday;
import comp3350.myshifts.tests.persistence.DataAccessStub;

public class SummarizeTest extends TestCase
{
    AccessShifts accessShifts;

    EmployeeSummary employeeSummary;
    ScheduleSummary scheduleSummary;

    ArrayList<Integer> listInts;
    int numInts;

    public SummarizeTest(String arg0)
    {
        super(arg0);
    }

    public void setUp()
    {
        Services.closeDataAccess();
        Services.createDataAccess(new DataAccessStub());
        accessShifts = new AccessShifts();
    }

    public void testEmployeeSummary()
    {
        System.out.println("\nTesting testEmployeeSummary");

        // test employee of Gary Chalmers
        // two shifts, total of 10 hours worked, and paid at $10.50 an hour
        // should be paid $105.00

        employeeSummary = Summarize.employee(100);

        assertEquals(100, employeeSummary.getEmployeeID());
        assertEquals("Gary Chalmers", employeeSummary.getEmployeeName());
        assertEquals(10.5, employeeSummary.getEmployeeWage());
        assertEquals(1, employeeSummary.getNumScheds());
        assertEquals(2, employeeSummary.getNumShifts());
        assertEquals(105.0, employeeSummary.getTotalPay());
        assertEquals(10.0, employeeSummary.getTotalHours());

        // test employee of Jonny Redfern
        // two shifts, total of 10 hours worked, and paid at $13.00 an hour
        // should be paid $130.00

        employeeSummary = Summarize.employee(101);

        assertEquals(101, employeeSummary.getEmployeeID());
        assertEquals("Jonny Redfern", employeeSummary.getEmployeeName());
        assertEquals(13.0, employeeSummary.getEmployeeWage());
        assertEquals(1, employeeSummary.getNumScheds());
        assertEquals(2, employeeSummary.getNumShifts());
        assertEquals(130.0, employeeSummary.getTotalPay());
        assertEquals(10.0, employeeSummary.getTotalHours());

        // test employee of Quincy Adams
        // two schedules, two shifts, total of 13 hours worked, and paid at $15.00 an hour
        // should be paid $195.00

        accessShifts.createShift(102, 100, Weekday.FRI, 2.0, 10.0); // add shift to test multiple schedules

        employeeSummary = Summarize.employee(102);

        assertEquals(102, employeeSummary.getEmployeeID());
        assertEquals("Quincy Adams", employeeSummary.getEmployeeName());
        assertEquals(15.0, employeeSummary.getEmployeeWage());
        assertEquals(2, employeeSummary.getNumScheds());
        assertEquals(2, employeeSummary.getNumShifts());
        assertEquals(195.0, employeeSummary.getTotalPay());
        assertEquals(13.0, employeeSummary.getTotalHours());

        // test employee of flowey theflower
        // has no shifts
        // should be paid $0.0

        employeeSummary = Summarize.employee(104);

        assertEquals(104, employeeSummary.getEmployeeID());
        assertEquals("flowey theflower", employeeSummary.getEmployeeName());
        assertEquals(20.5, employeeSummary.getEmployeeWage());
        assertEquals(0, employeeSummary.getNumScheds());
        assertEquals(0, employeeSummary.getNumShifts());
        assertEquals(0.0, employeeSummary.getTotalPay());
        assertEquals(0.0, employeeSummary.getTotalHours());

        try
        {
            employeeSummary = Summarize.employee(-1000);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            employeeSummary = Summarize.employee(-1);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            employeeSummary = Summarize.employee(105);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            employeeSummary = Summarize.employee(9999);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        System.out.println("Finished testEmployeeSummary");
    }

    public void testScheduleSummary()
    {
        System.out.println("\nTesting testScheduleSummary");

        // test schedule, id 100
        // one employee, 10 hours worked, wage of 10.5
        // employee should be paid $105.00

        scheduleSummary = Summarize.schedule(100);

        assertEquals(100, scheduleSummary.getScheduleID());
        assertEquals("Week 1", scheduleSummary.getSchedWeek());
        assertEquals("February", scheduleSummary.getSchedMonth());
        assertEquals("2017", scheduleSummary.getSchedYear());
        assertEquals(1, scheduleSummary.getNumEmployees());
        assertEquals(2, scheduleSummary.getNumShifts());
        assertEquals(10.0, scheduleSummary.getTotalHours());
        assertEquals(105.0, scheduleSummary.getTotalPayroll());

        // test schedule, id 101
        // one employee, 10 hours worked, wage of 13
        // employee should be paid $130.00

        scheduleSummary = Summarize.schedule(101);

        assertEquals(101, scheduleSummary.getScheduleID());
        assertEquals("Week 2", scheduleSummary.getSchedWeek());
        assertEquals("March", scheduleSummary.getSchedMonth());
        assertEquals("2017", scheduleSummary.getSchedYear());
        assertEquals(1, scheduleSummary.getNumEmployees());
        assertEquals(2, scheduleSummary.getNumShifts());
        assertEquals(10.0, scheduleSummary.getTotalHours());
        assertEquals(130.0, scheduleSummary.getTotalPayroll());

        // test schedule, id 102
        // two employees, 13 hours worked, wages of 10.5 (8 hours) and 15.0 (5 hours)
        // total payout should be $159.0

        accessShifts.createShift(100, 102, Weekday.FRI, 2.0, 10.0); // add shift to test multiple employees

        scheduleSummary = Summarize.schedule(102);

        assertEquals(102, scheduleSummary.getScheduleID());
        assertEquals("Week 1", scheduleSummary.getSchedWeek());
        assertEquals("April", scheduleSummary.getSchedMonth());
        assertEquals("2017", scheduleSummary.getSchedYear());
        assertEquals(2, scheduleSummary.getNumEmployees());
        assertEquals(2, scheduleSummary.getNumShifts());
        assertEquals(13.0, scheduleSummary.getTotalHours());
        assertEquals(159.0, scheduleSummary.getTotalPayroll());

        // test schedule, id 103
        // no employees and no shifts

        scheduleSummary = Summarize.schedule(103);

        assertEquals(103, scheduleSummary.getScheduleID());
        assertEquals("Week 2", scheduleSummary.getSchedWeek());
        assertEquals("May", scheduleSummary.getSchedMonth());
        assertEquals("2018", scheduleSummary.getSchedYear());
        assertEquals(0, scheduleSummary.getNumEmployees());
        assertEquals(0, scheduleSummary.getNumShifts());
        assertEquals(0.0, scheduleSummary.getTotalHours());
        assertEquals(0.0, scheduleSummary.getTotalPayroll());

        try
        {
            scheduleSummary = Summarize.schedule(-1000);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            scheduleSummary = Summarize.schedule(-1);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            scheduleSummary = Summarize.schedule(105);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            scheduleSummary = Summarize.schedule(7464);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        System.out.println("Finished testScheduleSummary");
    }
}
