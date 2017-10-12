package comp3350.myshifts.tests.business;

import junit.framework.TestCase;

import java.util.ArrayList;

import comp3350.myshifts.application.Services;
import comp3350.myshifts.business.AccessEmployees;
import comp3350.myshifts.objects.Employee;
import comp3350.myshifts.persistence.Persistence;
import comp3350.myshifts.tests.persistence.DataAccessStub;

public class AccessEmployeesTest extends TestCase
{
    Persistence persistence;
    AccessEmployees accessor;
    Employee employee;
    ArrayList<Employee> employees;

    public AccessEmployeesTest(String arg0)
    {
        super(arg0);
    }

    public void setUp()
    {
        persistence = Services.createDataAccess(new DataAccessStub());
        accessor = new AccessEmployees();
    }

    public void tearDown()
    {
        Services.closeDataAccess();
        accessor = null;
        employee = null;
        employees = null;
    }

    public void testValidEmployeeAddition()
    {
        System.out.println("\nStarting testValidEmployeeAddition");

        // employee ID should be 105, as there are default employees
        accessor.createEmployee("Tegan Quin", "2450238765", 20.50);
        employee = accessor.getEmployeeByID(105);
        assertEquals(105, employee.getEmployeeID());
        assertEquals("Tegan Quin", employee.getEmployeeName());
        assertEquals("2450238765", employee.getEmployeePhone());
        assertEquals(20.50, employee.getEmployeeWage());

        // id of next employee should be 106
        accessor.createEmployee("Sara Quin", "1234567890", 21.50);
        employee = accessor.getEmployeeByID(106);
        assertEquals(106, employee.getEmployeeID());
        assertEquals("Sara Quin", employee.getEmployeeName());
        assertEquals("1234567890", employee.getEmployeePhone());
        assertEquals(21.50, employee.getEmployeeWage());

        System.out.println("Finished testValidEmployeeAddition");
    }

    public void testInvalidEmployeeAddition()
    {
        System.out.println("\nStarting testInvalidEmployeeAddition");

        try
        {
            accessor.createEmployee("", "2450238765", 20.50);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            accessor.createEmployee(null, "2450238765", 20.50);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            accessor.createEmployee("Legion", "", 20.50);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            accessor.createEmployee("Legion", null, 20.50);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            accessor.createEmployee("Legion", "12345678901", 20.50);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            accessor.createEmployee("Legion", "abcdefg", 20.50);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            accessor.createEmployee("Legion", "12345678901", -20.50);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        System.out.println("Finished testInvalidEmployeeAddition");
    }

    public void testGetEmployees()
    {
        System.out.println("\nStarting testGetEmployees");

        employees = accessor.getAllEmployees();
        // should be five default employees with ids 100 - 104
        assertEquals(5, employees.size());

        employee = employees.get(0);
        assertEquals(100, employee.getEmployeeID());

        employee = employees.get(1);
        assertEquals(101, employee.getEmployeeID());

        employee = employees.get(2);
        assertEquals(102, employee.getEmployeeID());

        employee = employees.get(3);
        assertEquals(103, employee.getEmployeeID());

        employee = employees.get(4);
        assertEquals(104, employee.getEmployeeID());

        //employee not exist;
        assertNull(accessor.getEmployeeByID(222));

        // there are 5 default schedules, IDs: 100 - 104
        // First three employees should be in 1 schedule each
        employees = accessor.getEmployeesBySchedID(100);
        assertEquals(1, employees.size());
        assertEquals(100, employees.get(0).getEmployeeID());

        employees = accessor.getEmployeesBySchedID(101);
        assertEquals(1, employees.size());
        assertEquals(101, employees.get(0).getEmployeeID());

        employees = accessor.getEmployeesBySchedID(102);
        assertEquals(1, employees.size());
        assertEquals(102, employees.get(0).getEmployeeID());

        // Test for invalid schedules
        employees = accessor.getEmployeesBySchedID(-2);
        assertEquals(0, employees.size());

        employees = accessor.getEmployeesBySchedID(-1000);
        assertEquals(0, employees.size());

        employees = accessor.getEmployeesBySchedID(105);
        assertEquals(0, employees.size());

        employees = accessor.getEmployeesBySchedID(1000);
        assertEquals(0, employees.size());

        System.out.println("Finished testGetEmployees");
    }

    public void testEmployeeDeletion()
    {
        System.out.println("\nStarting testEmployeeDeletion");

        // test valid deletion
        assertTrue(accessor.deleteEmployeeByID(100));
        assertNull(accessor.getEmployeeByID(100));
        assertEquals(4, accessor.getAllEmployees().size());

        assertTrue(accessor.deleteEmployeeByID(103));
        assertNull(accessor.getEmployeeByID(103));
        assertEquals(3, accessor.getAllEmployees().size());

        // test invalid deletions
        assertFalse(accessor.deleteEmployeeByID(100));
        assertFalse(accessor.deleteEmployeeByID(103));
        assertFalse(accessor.deleteEmployeeByID(-5));
        assertFalse(accessor.deleteEmployeeByID(-1000));
        assertFalse(accessor.deleteEmployeeByID(5));
        assertFalse(accessor.deleteEmployeeByID(1000));
        assertFalse(accessor.deleteEmployeeByID(105));

        System.out.println("Finished testEmployeeDeletion");
    }

    public void testValidUpdates()
    {
        System.out.println("\nStarting testValidUpdates");

        assertTrue(accessor.updateEmployeeByID(100, "Tegan Quin", "2042222222", 15.25));
        employee = accessor.getEmployeeByID(100);
        assertEquals(100, employee.getEmployeeID());
        assertEquals("Tegan Quin", employee.getEmployeeName());
        assertEquals("2042222222", employee.getEmployeePhone());
        assertEquals(15.25, employee.getEmployeeWage());

        assertTrue(accessor.updateEmployeeByID(102, "Sara Quin", "2042439687", 12.75));
        employee = accessor.getEmployeeByID(102);
        assertEquals(102, employee.getEmployeeID());
        assertEquals("Sara Quin", employee.getEmployeeName());
        assertEquals("2042439687", employee.getEmployeePhone());
        assertEquals(12.75, employee.getEmployeeWage());

        System.out.println("Finished testValidUpdates");
    }

    public void testInvalidUpdates()
    {
        System.out.println("\nStarting testInvalidUpdates");

        assertFalse(accessor.updateEmployeeByID(99, "Thom Yorke", "2042222222", 75.50));
        assertFalse(accessor.updateEmployeeByID(-5, "Thom Yorke", "2042222222", 75.50));
        assertFalse(accessor.updateEmployeeByID(105, "Thom Yorke", "2042222222", 75.50));
        assertFalse(accessor.updateEmployeeByID(1000, "Thom Yorke", "2042222222", 75.50));

        try
        {
            accessor.updateEmployeeByID(100, "", "2042222222", 75.50);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            accessor.updateEmployeeByID(100, null, "2042222222", 75.50);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            accessor.updateEmployeeByID(100, "Thom Yorke", "", 75.50);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            accessor.updateEmployeeByID(100, "Thom Yorke", null, 75.50);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            accessor.updateEmployeeByID(100, "Thom Yorke", "12345678901", 75.50);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            accessor.updateEmployeeByID(100, "Thom Yorke", "abc", 75.50);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            accessor.updateEmployeeByID(100, "Thom Yorke", "abc", -75.50);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        System.out.println("Finished testInvalidUpdates");
    }
}
