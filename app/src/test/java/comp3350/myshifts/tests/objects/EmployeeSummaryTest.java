package comp3350.myshifts.tests.objects;

import junit.framework.TestCase;

import comp3350.myshifts.objects.EmployeeSummary;

public class EmployeeSummaryTest extends TestCase
{
    private EmployeeSummary employeeSummary;

    public EmployeeSummaryTest(String arg0)
    {
        super(arg0);
    }

    public void testEmployeeSummary()
    {
        System.out.println("\nStarting testEmployeeSummary");

        employeeSummary = new EmployeeSummary("Deckard", 100, 10.5, 1, 1, 10.0, 150.0);

        assertEquals("Deckard", employeeSummary.getEmployeeName());
        assertEquals(100, employeeSummary.getEmployeeID());
        assertEquals(10.5, employeeSummary.getEmployeeWage());
        assertEquals(1, employeeSummary.getNumShifts());
        assertEquals(1, employeeSummary.getNumScheds());
        assertEquals(10.0, employeeSummary.getTotalHours());
        assertEquals(150.0, employeeSummary.getTotalPay());

        System.out.println("Finished testEmployeeSummary");
    }

    public void testInvalidEmployeeSummary()
    {
        System.out.println("\nStarting testInvalidEmployeeSummary");

        try
        {
            employeeSummary = new EmployeeSummary(null, 100, 10.5, 1, 1, 10.0, 150.0);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            employeeSummary = new EmployeeSummary("", 100, 10.5, 1, 1, 10.0, 150.0);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            employeeSummary = new EmployeeSummary("Deckard", -100, 10.5, 1, 1, 10.0, 150.0);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            employeeSummary = new EmployeeSummary("Deckard", 100, -10.5, 1, 1, 10.0, 150.0);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            employeeSummary = new EmployeeSummary("Deckard", 100, 10.5, -1, 1, 10.0, 150.0);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            employeeSummary = new EmployeeSummary("Deckard", 100, 10.5, 1, -1, 10.0, 150.0);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            employeeSummary = new EmployeeSummary("Deckard", 100, 10.5, 1, 1, -10.0, 150.0);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            employeeSummary = new EmployeeSummary("Deckard", 100, 10.5, 1, 1, 10.0, -150.0);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        System.out.println("Finished testInvalidEmployeeSummary");
    }
}
