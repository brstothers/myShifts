package comp3350.myshifts.tests.objects;

import junit.framework.TestCase;

import comp3350.myshifts.objects.Employee;


public class EmployeeTest extends TestCase
{
	private Employee employee;

	public EmployeeTest(String arg0)
	{
		super(arg0);
	}

	public void testValidEmployeeCreation()
	{
		System.out.println("\nStarting testValidEmployeeCreation");

		// testing public Employee(String newName, String newPhone) constructor
		employee = new Employee("John Wick", "2042222222");
		assertEquals(-1, employee.getEmployeeID());
		assertEquals("John Wick", employee.getEmployeeName());
		assertEquals("2042222222", employee.getEmployeePhone());
		assertTrue(0 == employee.getEmployeeWage());

		// testing public Employee(String newName, String newPhone, double newWage) constructor
		employee = new Employee("John Wick", "2042042041", 16.96);
		assertEquals(-1, employee.getEmployeeID());
		assertEquals("John Wick", employee.getEmployeeName());
		assertEquals("2042042041", employee.getEmployeePhone());
		assertTrue(16.96 == employee.getEmployeeWage());

		System.out.println("Finished testValidEmployeeCreation");
	}

	public void testInvalidEmployeeCreation()
	{
		System.out.println("\nStarting testInvalidEmployeeCreation");

		try
		{
			employee = new Employee("Johny", "2", -1);
			fail("Expected the illegal argument.");
		}
		catch (IllegalArgumentException e)
		{
		}

		try
		{
			employee = new Employee("johny", "204aa");
			fail("Expected the illegal argument.");
		}
		catch (IllegalArgumentException e)
		{
		}

		try
		{
			employee = new Employee("johny", "2052222222", -1);
			fail("Expected the illegal argument.");
		}
		catch (IllegalArgumentException e)
		{
		}

		try
		{
			employee = new Employee("", "2052052061");
			fail("Expected the illegal argument.");
		}
		catch (IllegalArgumentException e)
		{
		}

		try
		{
			employee = new Employee(null, "2052052061");
			fail("Expected the illegal argument.");
		}
		catch (IllegalArgumentException e)
		{
		}

		try
		{
			employee = new Employee(null, null);
			fail("Expected the illegal argument.");
		}
		catch (IllegalArgumentException e)
		{
		}

		System.out.println("Finished testInvalidEmployeeCreation");
	}

	public void testValidEmployeeUpdate()
	{
		System.out.println("\nStarting testValidEmployeeUpdate");

		employee = new Employee("Harry Potter", "2042222222", 15.50);
		employee.setEmployeeName("Ron Weasley");
		employee.setEmployeePhone("2048371234");
		employee.setEmployeeWage(20.20);

		assertEquals("Ron Weasley", employee.getEmployeeName());
		assertEquals("2048371234", employee.getEmployeePhone());
		assertEquals(20.20, employee.getEmployeeWage());

		System.out.println("Finished testValidEmployeeUpdate");
	}

	public void testInvalidEmployeeUpdate()
	{
		System.out.println("\nStarting testInvalidEmployeeUpdate");

		employee = new Employee("Harry Potter", "2042222222", 15.50);

		try
		{
			employee.setEmployeeName(null);
			fail("Expected the illegal argument.");
		}
		catch (IllegalArgumentException e)
		{
		}

		try
		{
			employee.setEmployeeName("");
			fail("Expected the illegal argument.");
		}
		catch (IllegalArgumentException e)
		{
		}

		try
		{
			employee.setEmployeePhone(null);
			fail("Expected the illegal argument.");
		}
		catch (IllegalArgumentException e)
		{
		}

		try
		{
			employee.setEmployeePhone("");
			fail("Expected the illegal argument.");
		}
		catch (IllegalArgumentException e)
		{
		}

		try
		{
			employee.setEmployeePhone("12345678901");
			fail("Expected the illegal argument.");
		}
		catch (IllegalArgumentException e)
		{
		}

		try
		{
			employee.setEmployeePhone("abcdefghij");
			fail("Expected the illegal argument.");
		}
		catch (IllegalArgumentException e)
		{
		}

		try
		{
			employee.setEmployeeWage(-13.76);
			fail("Expected the illegal argument.");
		}
		catch (IllegalArgumentException e)
		{
		}


		System.out.println("Finished testInvalidEmployeeUpdate");
	}
}