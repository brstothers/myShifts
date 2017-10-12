package comp3350.myshifts.tests.integration;

import junit.framework.TestCase;

import java.util.ArrayList;

import comp3350.myshifts.application.Main;
import comp3350.myshifts.application.Services;

import comp3350.myshifts.objects.Employee;
import comp3350.myshifts.objects.Weekday;
import comp3350.myshifts.persistence.Persistence;
import comp3350.myshifts.tests.persistence.DataAccessStubTest;

public class DataAccessHSQLDBTest extends TestCase
{
    private static String dbName = Main.dbName;
    Persistence persistence;
    ArrayList<Employee> employees;

    public DataAccessHSQLDBTest(String arg0)
    {
        super(arg0);
    }

    public void setUp()
    {
        Services.closeDataAccess();
        Services.createDataAccess(dbName); // gets the default service of hsqldb, so the call Tests use the sql db instead
        persistence = Services.getDataAccess(dbName);
    }

    public void testAccessEmployeesDBIntegration()
    {
        System.out.println("\nStarting testAccessEmployeesDBIntegration");

        DataAccessStubTest.testGetAllEmployees();

        DataAccessStubTest.testGetEmployeeByID();

        DataAccessStubTest.testValidAddEmployee();
        assertTrue(persistence.deleteEmployeeByID(105)); // reset default state
        assertTrue(persistence.deleteEmployeeByID(106));

        DataAccessStubTest.testInvalidAddEmployee();

        DataAccessStubTest.testValidUpdateEmployee();
        assertTrue(persistence.updateEmployeeByID(100, "Gary Chalmers", "2041236787", 10.50)); // reset to default state
        assertTrue(persistence.updateEmployeeByID(104, "flowey theflower", "2042222222", 20.50));

        DataAccessStubTest.testInvalidUpdateEmployee(); // need to add checks in DataAccessObject

        System.out.println("\nFinished testAccessEmployeesDBIntegration");
    }

    public void testAccessSchedulesDBIntegration()
    {
        System.out.println("\nStarting testAccessSchedulesDBIntegration");

        DataAccessStubTest.testGetAllSchedules();

        DataAccessStubTest.testGetScheduleByID();

        DataAccessStubTest.testValidAddSchedule();
        assertTrue(persistence.deleteScheduleByID(105)); // reset default state
        assertTrue(persistence.deleteScheduleByID(106));
        assertTrue(persistence.deleteScheduleByID(107));

        DataAccessStubTest.testInvalidAddSchedule();

        DataAccessStubTest.testValidUpdateSchedule();
        assertTrue(persistence.updateScheduleByID(100, "Week 1", "February", "2017")); // reset to default state

        DataAccessStubTest.testInvalidUpdateSchedule(); // need to add checks in DataAccessObject

        System.out.println("\nFinished testAccessSchedulesDBIntegration");
    }

    public void testAccessShiftsDBIntegration()
    {
        System.out.println("\nStarting testAccessShiftsDBIntegration");

        DataAccessStubTest.testGetAllShifts();

        DataAccessStubTest.testGetShiftByID();

        DataAccessStubTest.testValidAddShift();
        assertTrue(persistence.deleteShiftbyID(100, 104, Weekday.FRI)); // reset default state

        DataAccessStubTest.testInvalidAddShift();

        DataAccessStubTest.testValidUpdateShift();
        assertTrue(persistence.updateShiftByID(100, 100, Weekday.FRI, 10, 15));

        DataAccessStubTest.testInvalidUpdateShift(); // need to add checks in DataAccessObject

        System.out.println("\nFinished testAccessShiftsDBIntegration");
    }
}
