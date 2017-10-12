package comp3350.myshifts.tests.objects;

import junit.framework.TestCase;

import comp3350.myshifts.objects.Shift;
import comp3350.myshifts.objects.Weekday;

public class ShiftTest extends TestCase
{
	private Shift shift;

	public ShiftTest(String arg0)
	{
		super(arg0);
	}

	public void testValidShiftCreation()
	{
		System.out.println("\nStarting testValidShiftCreation");

		// testing public Shift(String newEmployeeID, String newWorkdayID) constructor
		shift = new Shift(1234, 1234, Weekday.WED, 1, 2);
		assertNotNull(shift);
		assertEquals(1234, shift.getEmployeeID());
		assertEquals(1234, shift.getScheduleID());

		// testing public Shift(String newEmployeeID, String newWorkdayID, String newEmployeeName, String newWorkdayDate)
		shift = new Shift(1234, 23, Weekday.SUN, 1, 2);
		assertNotNull(shift);
		assertEquals(1234, shift.getEmployeeID());
		assertEquals(23, shift.getScheduleID());

		System.out.println("Finished testValidShiftCreation");
	}

	public void testInvalidShiftCreation()
	{
		System.out.println("\nStarting testInvalidShiftCreation");

		// test for invalid employee id
		try
		{
			shift = new Shift(-1, 1, Weekday.FRI, 5, 10);
			fail("Expected the illegal argument.");
		}
		catch (IllegalArgumentException e)
		{
		}

		// test for invalid workday id
		try
		{
			shift = new Shift(1, -1, Weekday.THR, 2, 8);
			fail("Expected the illegal argument.");
		}
		catch (IllegalArgumentException e)
		{
		}

		// test for invalid start time
		try
		{
			shift = new Shift(2, 5, Weekday.THR, -2, 8);
			fail("Expected the illegal argument.");
		}
		catch (IllegalArgumentException e)
		{
		}

		// test for invalid end time
		try
		{
			shift = new Shift(2, 5, Weekday.THR, 5, -20);
			fail("Expected the illegal argument.");
		}
		catch (IllegalArgumentException e)
		{
		}

		try
		{
			shift = new Shift(2, 5, Weekday.THR, 5, 30);
			fail("Expected the illegal argument.");
		}
		catch (IllegalArgumentException e)
		{
		}

		try
		{
			shift = new Shift(2, 5, Weekday.THR, 30, 8);
			fail("Expected the illegal argument.");
		}
		catch (IllegalArgumentException e)
		{
		}

		System.out.println("Finished testInvalidShiftCreation");
	}

	public void testShiftStartChange()
	{
		System.out.println("\nStarting testShiftStartTime");

		// testing getShiftStart
		shift = new Shift(12, 21, Weekday.MON, 1, 2);
		assertEquals(1.0, shift.getStartTime());

		// testing setStartTime
		shift = new Shift(12, 12, Weekday.SAT, 3.0, 4.0);
		shift.setStartTime(2);
		assertEquals(2.0, shift.getStartTime());

		System.out.println("Finished testShiftStartTime");
	}

	public void testShiftEndChange()
	{
		System.out.println("\nStarting testShiftEndTime");

		// testing getShiftEnd
		shift = new Shift(12, 12, Weekday.FRI, 1.0, 3.0);
		assertEquals(3.0, shift.getEndTime());

		// testing setEndTime
		shift = new Shift(2, 3, Weekday.WED, 3.0, 4.0);
		shift.setEndTime(2);
		assertEquals(3.0, shift.getEndTime());

		System.out.println("Finished testShiftEndTime");
	}

	public void testWeekdayChange()
	{
		System.out.println("\nStarting testWeekdayChange");

		// testing getShiftEnd
		shift = new Shift(12, 12, Weekday.FRI, 1.0, 2.0);
		assertEquals(Weekday.FRI, shift.getWeekday());

		// testing setEndTime
		shift = new Shift(1, 2, Weekday.FRI, 3.0, 4.0);
		shift.setWeekday(Weekday.WED);
		assertEquals(Weekday.WED, shift.getWeekday());

		System.out.println("Finished testWeekdayChange");
	}

	public void testInvalidShiftUpdate()
	{
		System.out.println("\nStarting testInvalidShiftUpdate");

		shift = new Shift(1, 2, Weekday.FRI, 3.0, 4.0);

		// test invalid shift end time
		try
		{
			shift.setEndTime(-2);
			fail("Expected the illegal argument.");
		}
		catch (IllegalArgumentException e)
		{
		}

		try
		{
			shift.setEndTime(30);
			fail("Expected the illegal argument.");
		}
		catch (IllegalArgumentException e)
		{
		}

		// test invalid shift start time
		try
		{
			shift.setStartTime(-2);
			fail("Expected the illegal argument.");
		}
		catch (IllegalArgumentException e)
		{
		}

		try
		{
			shift.setStartTime(30);
			fail("Expected the illegal argument.");
		}
		catch (IllegalArgumentException e)
		{
		}

		System.out.println("Finished testInvalidShiftUpdate");
	}
}