package comp3350.myshifts.tests.persistence;

import junit.framework.TestCase;

import java.util.ArrayList;

import comp3350.myshifts.application.Main;
import comp3350.myshifts.application.Services;
import comp3350.myshifts.objects.Employee;
import comp3350.myshifts.objects.Schedule;
import comp3350.myshifts.objects.Shift;
import comp3350.myshifts.objects.Weekday;
import comp3350.myshifts.persistence.Persistence;

public class DataAccessStubTest extends TestCase
{
    private static Persistence stub; //stub to be used
    private static Employee employee1;
    private static Employee employee2;
    private static Schedule schedule1;
    private static Schedule schedule2;
    private static Shift shift1;
    private static Shift shift2;
    private static ArrayList<Employee> allEmployees;
    private static ArrayList<Schedule> allSchedules;
    private static ArrayList<Shift> allShifts;

    public DataAccessStubTest(String arg0)
    {
        super(arg0);
    }

    public void setUp()
    {
        Services.closeDataAccess();
        Services.createDataAccess(new DataAccessStub());
    }

    public void tearDown()
    {
        stub = null;
        employee1 = null;
        employee2 = null;
        schedule1 = null;
        schedule2 = null;
        shift1 = null;
        shift2 = null;
    }

    public static void testGetAllEmployees()
    {
        System.out.println("\nStarting testGetAllEmployees");
        stub = Services.getDataAccess(Main.dbName);

        allEmployees = stub.getAllEmployees();

        // check for the number of default employees returned, should be 5
        assertEquals(5, allEmployees.size());

        employee1 = allEmployees.get(0);
        assertEquals(100, employee1.getEmployeeID());
        assertEquals("Gary Chalmers", employee1.getEmployeeName());

        employee1 = allEmployees.get(1);
        assertEquals(101, employee1.getEmployeeID());
        assertEquals("Jonny Redfern", employee1.getEmployeeName());

        employee1 = allEmployees.get(2);
        assertEquals(102, employee1.getEmployeeID());
        assertEquals("Quincy Adams", employee1.getEmployeeName());

        employee1 = allEmployees.get(3);
        assertEquals(103, employee1.getEmployeeID());
        assertEquals("Rumple Stillskin", employee1.getEmployeeName());

        employee1 = allEmployees.get(4);
        assertEquals(104, employee1.getEmployeeID());
        assertEquals("flowey theflower", employee1.getEmployeeName());

        System.out.println("Finished testGetAllEmployees");
    }

    public static void testGetEmployeeByID()
    {
        System.out.println("\nStarting testGetEmployeeByID");
        stub = Services.getDataAccess(Main.dbName);

        // Seeded Employee 1 should be Gary Chalmers
        employee1 = stub.getEmployeeByID(100);
        assertEquals(100, employee1.getEmployeeID());
        assertEquals("Gary Chalmers", employee1.getEmployeeName());
        assertEquals("2041236787", employee1.getEmployeePhone());
        assertEquals(10.5, employee1.getEmployeeWage());

        // Seeded Employee 2 should be Jonny Redfern
        employee1 = stub.getEmployeeByID(101);
        assertEquals(101, employee1.getEmployeeID());
        assertEquals("Jonny Redfern", employee1.getEmployeeName());
        assertEquals("2048289489", employee1.getEmployeePhone());
        assertEquals(13.00, employee1.getEmployeeWage());

        // test invalid employees
        assertNull(stub.getEmployeeByID(-10000));
        assertNull(stub.getEmployeeByID(-1));
        assertNull(stub.getEmployeeByID(0));
        assertNull(stub.getEmployeeByID(105));
        assertNull(stub.getEmployeeByID(10000));

        System.out.println("Finished testGetEmployeeByID");
    }

    public static void testValidAddEmployee()
    {
        System.out.println("\nStarting testValidAddEmployee");
        stub = Services.getDataAccess(Main.dbName);

        // Adding a valid employee
        employee1 = new Employee("Hello World", "2042222222", 10.5);
        assertTrue(stub.addEmployee(employee1));
        assertEquals(105, employee1.getEmployeeID());
        assertEquals("Hello World", employee1.getEmployeeName());
        assertEquals("2042222222", employee1.getEmployeePhone());
        assertEquals(10.5, employee1.getEmployeeWage());

        employee1 = new Employee("Bill Nye", "2048451736", 17.5);
        assertTrue(stub.addEmployee(employee1));
        assertEquals(106, employee1.getEmployeeID());
        assertEquals("Bill Nye", employee1.getEmployeeName());
        assertEquals("2048451736", employee1.getEmployeePhone());
        assertEquals(17.5, employee1.getEmployeeWage());

        System.out.println("Finished testValidAddEmployee");
    }

    public static void testInvalidAddEmployee()
    {
        System.out.println("\nStarting testInvalidAddEmployee");
        stub = Services.getDataAccess(Main.dbName);

        assertFalse(stub.addEmployee(null));

        try
        {
            stub.addEmployee(new Employee("", "6549871234", 55.75));
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            stub.addEmployee(new Employee(null, "6549871234", 55.75));
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            stub.addEmployee(new Employee("Charles Xavier", "", 55.75));
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            stub.addEmployee(new Employee("Charles Xavier", null, 55.75));
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            stub.addEmployee(new Employee("Charles Xavier", "6549871234", -55.75));
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        System.out.println("Finished testInvalidAddEmployee");
    }

    public static void testValidUpdateEmployee()
    {
        System.out.println("\nStarting testValidUpdateEmployee");
        stub = Services.getDataAccess(Main.dbName);

        // Update valid employee
        assertTrue(stub.updateEmployeeByID(100, "Will Dobin", "2042222222", 12.5));
        employee1 = stub.getEmployeeByID(100);
        assertEquals(100, employee1.getEmployeeID());
        assertEquals("Will Dobin", employee1.getEmployeeName());
        assertEquals("2042222222", employee1.getEmployeePhone());
        assertEquals(12.5, employee1.getEmployeeWage());

        assertTrue(stub.updateEmployeeByID(104, "Jean Grey", "1234567890", 15.75));
        employee1 = stub.getEmployeeByID(104);
        assertEquals(104, employee1.getEmployeeID());
        assertEquals("Jean Grey", employee1.getEmployeeName());
        assertEquals("1234567890", employee1.getEmployeePhone());
        assertEquals(15.75, employee1.getEmployeeWage());

        System.out.println("Finished testValidUpdateEmployee");
    }

    public static void testInvalidUpdateEmployee()
    {
        System.out.println("\nStarting testInvalidUpdateEmployee");
        stub = Services.getDataAccess(Main.dbName);

        // Update invalid employee
        assertFalse(stub.updateEmployeeByID(-10000, "Christy Sandybottom", "123445552", 11.50));
        assertFalse(stub.updateEmployeeByID(-1, "Christy Sandybottom", "123445552", 11.50));
        assertFalse(stub.updateEmployeeByID(0, "Christy Sandybottom", "123445552", 11.50));
        assertFalse(stub.updateEmployeeByID(105, "Christy Sandybottom", "123445552", 11.50));
        assertFalse(stub.updateEmployeeByID(10000, "Christy Sandybottom", "123445552", 11.50));

        // Test invalid info update
        try
        {
            stub.updateEmployeeByID(100, "", "7438470987", 21.63);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            stub.updateEmployeeByID(100, null, "7438470987", 21.63);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            stub.updateEmployeeByID(100, "Kurt Wagner", "", 21.63);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            stub.updateEmployeeByID(100, "Kurt Wagner", null, 21.63);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            stub.updateEmployeeByID(100, "Kurt Wagner", "7438470987", -21.63);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        System.out.println("Finished testInvalidUpdateEmployee");
    }

    public static void testDeleteEmployee()
    {
        System.out.println("\nStarting testDeleteEmployee");
        stub = Services.getDataAccess(Main.dbName);

        // Delete valid employee
        assertTrue(stub.deleteEmployeeByID(100));
        assertNull(stub.getEmployeeByID(100));

        //Delete invalid employees
        assertFalse(stub.deleteEmployeeByID(800));
        assertFalse(stub.deleteEmployeeByID(-5));
        assertFalse(stub.deleteEmployeeByID(105));

        System.out.println("Finished testDeleteEmployee");
    }

    public static void testGetAllSchedules()
    {
        System.out.println("\nStarting testGetAllSchedules");
        stub = Services.getDataAccess(Main.dbName);

        allSchedules = stub.getAllSchedules();

        assertNotNull(allSchedules);
        assertEquals(5, allSchedules.size());

        schedule1 = allSchedules.get(0);
        assertEquals("February", schedule1.getMonth());

        schedule1 = allSchedules.get(1);
        assertEquals("March", schedule1.getMonth());

        schedule1 = allSchedules.get(2);
        assertEquals("April", schedule1.getMonth());

        schedule1 = allSchedules.get(3);
        assertEquals("May", schedule1.getMonth());

        schedule1 = allSchedules.get(4);
        assertEquals("June", schedule1.getMonth());

        System.out.println("Finished testGetAllSchedules");
    }

    public static void testGetScheduleByID()
    {
        System.out.println("\nStarting testGetScheduleByID");
        stub = Services.getDataAccess(Main.dbName);

        // Seeded Schedule 1
        schedule1 = stub.getScheduleByID(100);
        assertEquals(100, schedule1.getSchedID());
        assertEquals("Week 1", schedule1.getWeek());
        assertEquals("February", schedule1.getMonth());
        assertEquals("2017", schedule1.getYear());

        // Seeded Schedule 2
        schedule1 = stub.getScheduleByID(101);
        assertEquals(101, schedule1.getSchedID());
        assertEquals("Week 2", schedule1.getWeek());
        assertEquals("March", schedule1.getMonth());
        assertEquals("2017", schedule1.getYear());

        // Test invalid IDs
        assertNull(stub.getScheduleByID(-10000));
        assertNull(stub.getScheduleByID(-1));
        assertNull(stub.getScheduleByID(105));
        assertNull(stub.getScheduleByID(10000));

        System.out.println("Finished testGetScheduleByID");
    }

    public static void testValidAddSchedule()
    {
        System.out.println("\nStarting testValidAddSchedule");
        stub = Services.getDataAccess(Main.dbName);

        // Adding a valid schedule
        schedule1 = new Schedule("Week 3", "June", "2019");
        assertTrue(stub.addSchedule(schedule1));

        schedule2 = stub.getScheduleByID(105);
        assertEquals(105, schedule2.getSchedID()); // new schedule should have id of 105
        assertEquals("Week 3", schedule2.getWeek());
        assertEquals("June", schedule2.getMonth());
        assertEquals("2019", schedule2.getYear());

        schedule1 = new Schedule("Week 2", "November", "20XX");
        assertTrue(stub.addSchedule(schedule1));

        schedule2 = stub.getScheduleByID(106);
        assertEquals(106, schedule2.getSchedID()); // new schedule should have id of 106
        assertEquals("Week 2", schedule2.getWeek());
        assertEquals("November", schedule2.getMonth());
        assertEquals("20XX", schedule2.getYear());

        schedule1 = new Schedule("Week 1", "January", "2020");
        assertTrue(stub.addSchedule(schedule1));

        schedule2 = stub.getScheduleByID(107);
        assertEquals(107, schedule2.getSchedID()); // new schedule should have id of 107
        assertEquals("Week 1", schedule2.getWeek());
        assertEquals("January", schedule2.getMonth());
        assertEquals("2020", schedule2.getYear());

        System.out.println("Finished testValidAddSchedule");
    }

    public static void testInvalidAddSchedule()
    {
        System.out.println("\nStarting testValidAddSchedule");
        stub = Services.getDataAccess(Main.dbName);

        try
        {
            stub.addSchedule(new Schedule("", "March", "2019"));
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            stub.addSchedule(new Schedule(null, "March", "2019"));
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            stub.addSchedule(new Schedule("1", "", "2019"));
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            stub.addSchedule(new Schedule("1", null, "2019"));
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            stub.addSchedule(new Schedule("1", "March", ""));
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            stub.addSchedule(new Schedule("1", "March", null));
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        System.out.println("Finished testValidAddSchedule");
    }

    public static void testValidUpdateSchedule()
    {
        System.out.println("\nStarting testValidUpdateSchedule");
        stub = Services.getDataAccess(Main.dbName);

        // Update valid employee
        assertTrue(stub.updateScheduleByID(100, "Week 4", "September", "2020"));
        schedule1 = stub.getScheduleByID(100);
        assertEquals(100, schedule1.getSchedID());
        assertEquals("Week 4", schedule1.getWeek());
        assertEquals("September", schedule1.getMonth());
        assertEquals("2020", schedule1.getYear());

        System.out.println("Finished testValidUpdateSchedule");
    }

    public static void testInvalidUpdateSchedule()
    {
        System.out.println("\nStarting testValidUpdateSchedule");
        stub = Services.getDataAccess(Main.dbName);

        //Update invalid employee
        assertFalse(stub.updateScheduleByID(-9999, "week 20", "Septemarch", "20xx"));
        assertFalse(stub.updateScheduleByID(-1, "week 20", "Septemarch", "20xx"));
        assertFalse(stub.updateScheduleByID(105, "week 20", "Septemarch", "20xx"));
        assertFalse(stub.updateScheduleByID(1000, "week 20", "Septemarch", "20xx"));

        try
        {
            stub.updateScheduleByID(100, "", "April", "2019");
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            stub.updateScheduleByID(100, null, "April", "2019");
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            stub.updateScheduleByID(100, "1", "", "2019");
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            stub.updateScheduleByID(100, "1", null, "2019");
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            stub.updateScheduleByID(100, "1", "April", "");
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            stub.updateScheduleByID(100, "1", "April", null);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        System.out.println("Finished testValidUpdateSchedule");
    }

    public static void testDeleteSchedule()
    {
        System.out.println("\nStarting testDeleteSchedule");
        stub = Services.getDataAccess(Main.dbName);

        // Delete valid employee
        assertTrue(stub.deleteScheduleByID(100));
        assertNull(stub.getScheduleByID(100));
        assertFalse(stub.deleteScheduleByID(100));

        assertTrue(stub.deleteScheduleByID(103));
        assertNull(stub.getScheduleByID(103));
        assertFalse(stub.deleteScheduleByID(103));

        //Delete invalid employee
        assertFalse(stub.deleteScheduleByID(-9999));
        assertFalse(stub.deleteScheduleByID(-1));
        assertFalse(stub.deleteScheduleByID(105));
        assertFalse(stub.deleteScheduleByID(9999));

        System.out.println("Finished testDeleteSchedule");
    }

    public static void testGetAllShifts()
    {
        System.out.println("\nStarting testGetAllShifts");
        stub = Services.getDataAccess(Main.dbName);

        allShifts = stub.getAllShifts();

        assertNotNull(allShifts);
        assertEquals(5, allShifts.size());

        shift1 = allShifts.get(0);
        assertEquals(Weekday.FRI, shift1.getWeekday());

        shift1 = allShifts.get(1);
        assertEquals(Weekday.SAT, shift1.getWeekday());

        shift1 = allShifts.get(2);
        assertEquals(Weekday.MON, shift1.getWeekday());

        shift1 = allShifts.get(3);
        assertEquals(Weekday.SUN, shift1.getWeekday());

        shift1 = allShifts.get(4);
        assertEquals(Weekday.TUE, shift1.getWeekday());

        System.out.println("Finished testGetAllShifts");
    }

    public static void testGetShiftByID()
    {
        System.out.println("\nStarting testGetShiftByID");
        stub = Services.getDataAccess(Main.dbName);

        allShifts = stub.getAllShifts();

        // Seeded Schedule 1
        shift1 = stub.getShiftByID(allShifts.get(0).getEmployeeID(),
                allShifts.get(0).getScheduleID(), allShifts.get(0).getWeekday());
        assertEquals(100, shift1.getEmployeeID());
        assertEquals(Weekday.FRI, shift1.getWeekday());
        assertEquals(10.0, shift1.getStartTime());
        assertEquals(15.0, shift1.getEndTime());

        // Seeded Schedule 2
        shift1 = stub.getShiftByID(allShifts.get(1).getEmployeeID(),
                allShifts.get(1).getScheduleID(), allShifts.get(1).getWeekday());
        assertEquals(100, shift1.getEmployeeID());
        assertEquals(Weekday.SAT, shift1.getWeekday());
        assertEquals(10.0, shift1.getStartTime());
        assertEquals(15.0, shift1.getEndTime());

        // get invalid shifts
        assertNull(stub.getShiftByID(-10000, allShifts.get(1).getScheduleID(), allShifts.get(1).getWeekday()));
        assertNull(stub.getShiftByID(-1, allShifts.get(1).getScheduleID(), allShifts.get(1).getWeekday()));
        assertNull(stub.getShiftByID(0, allShifts.get(1).getScheduleID(), allShifts.get(1).getWeekday()));
        assertNull(stub.getShiftByID(105, allShifts.get(1).getScheduleID(), allShifts.get(1).getWeekday()));
        assertNull(stub.getShiftByID(10000, allShifts.get(1).getScheduleID(), allShifts.get(1).getWeekday()));

        System.out.println("Finished testGetShiftByID");
    }

    public static void testValidAddShift()
    {
        System.out.println("\nStarting testValidAddShift");
        stub = Services.getDataAccess(Main.dbName);

        allEmployees = stub.getAllEmployees();
        allSchedules = stub.getAllSchedules();

        // Adding a valid employee
        shift1 = new Shift(allEmployees.get(0).getEmployeeID(),
                allSchedules.get(4).getSchedID(), Weekday.FRI, 10, 15);
        assertTrue(stub.addShift(shift1));

        shift2 = stub.getShiftByID(allEmployees.get(0).getEmployeeID(),
                allSchedules.get(4).getSchedID(), Weekday.FRI);
        assertTrue(shift1.equals(shift2));
        assertEquals(shift1.getStartTime(), shift2.getStartTime());
        assertEquals(shift1.getEndTime(), shift2.getEndTime());

        System.out.println("Finished testValidAddShift");
    }

    public static void testInvalidAddShift()
    {
        System.out.println("\nStarting testInvalidAddShift");
        stub = Services.getDataAccess(Main.dbName);

        //Adding a null object
        assertFalse(stub.addShift(null));

        try
        {
            stub.addShift(new Shift(-100, 103, Weekday.FRI, 10, 15));
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            stub.addShift(new Shift(100, -103, Weekday.FRI, 10, 15));
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            stub.addShift(new Shift(100, 103, Weekday.FRI, -10, 15));
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            stub.addShift(new Shift(100, 103, Weekday.FRI, 10, -15));
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        System.out.println("Finished testInvalidAddShift");
    }

    public static void testValidUpdateShift()
    {
        System.out.println("\nStarting testValidUpdateShift");
        stub = Services.getDataAccess(Main.dbName);

        allShifts = stub.getAllShifts();

        shift1 = allShifts.get(0);
        // Update valid employee
        assertTrue(stub.updateShiftByID(shift1.getEmployeeID(), shift1.getScheduleID(), Weekday.FRI, 5, 10));
        shift2 = stub.getShiftByID(shift1.getEmployeeID(), shift1.getScheduleID(), Weekday.FRI);

        assertTrue(shift1.equals(shift2));
        assertEquals(5.0, shift2.getStartTime());
        assertEquals(10.0, shift2.getEndTime());

        System.out.println("Finished testValidUpdateShift");
    }

    public static void testInvalidUpdateShift()
    {
        System.out.println("\nStarting testValidUpdateShift");
        stub = Services.getDataAccess(Main.dbName);

        // Update invalid employee
        assertFalse(stub.updateShiftByID(-98320, 100, Weekday.FRI, 5, 10));
        assertFalse(stub.updateShiftByID(-1, 100, Weekday.FRI, 5, 10));
        assertFalse(stub.updateShiftByID(0, 100, Weekday.FRI, 5, 10));
        assertFalse(stub.updateShiftByID(105, 100, Weekday.FRI, 5, 10));
        assertFalse(stub.updateShiftByID(1000, 100, Weekday.FRI, 5, 10));

        // Update wrong Weekday
        assertFalse(stub.updateShiftByID(100, 100, Weekday.MON, 5, 10));

        // update wrong start and end times
        try
        {
            stub.updateShiftByID(100, 100, Weekday.FRI, -10, 15);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            stub.updateShiftByID(100, 100, Weekday.FRI, 10, -15);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            stub.updateShiftByID(100, 100, Weekday.FRI, 30, 15);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        try
        {
            stub.updateShiftByID(100, 100, Weekday.FRI, 10, 30);
            fail("Expected the illegal argument.");
        }
        catch (IllegalArgumentException e)
        {
        }

        System.out.println("Finished testValidUpdateShift");
    }

    public static void testDeleteShift()
    {
        System.out.println("\nStarting testDeleteShift");
        stub = Services.getDataAccess(Main.dbName);

        allShifts = stub.getAllShifts();

        shift1 = allShifts.get(0);

        // Delete valid employee
        assertTrue(stub.deleteShiftbyID(shift1.getEmployeeID(), shift1.getScheduleID(), Weekday.FRI));
        assertNull(stub.getShiftByID(shift1.getEmployeeID(), shift1.getScheduleID(), Weekday.FRI));
        assertFalse(stub.deleteShiftbyID(shift1.getEmployeeID(), shift1.getScheduleID(), Weekday.FRI));

        // Delete invalid employee
        assertFalse(stub.deleteShiftbyID(-999, 100, Weekday.FRI));
        assertFalse(stub.deleteShiftbyID(-1, 100, Weekday.FRI));
        assertFalse(stub.deleteShiftbyID(0, 100, Weekday.FRI));
        assertFalse(stub.deleteShiftbyID(105, 100, Weekday.FRI));
        assertFalse(stub.deleteShiftbyID(10000, 100, Weekday.FRI));

        // Delete invalid employee
        assertFalse(stub.deleteShiftbyID(100, -999, Weekday.FRI));
        assertFalse(stub.deleteShiftbyID(100, -1, Weekday.FRI));
        assertFalse(stub.deleteShiftbyID(100, 0, Weekday.FRI));
        assertFalse(stub.deleteShiftbyID(100, 105, Weekday.FRI));
        assertFalse(stub.deleteShiftbyID(100, 10000, Weekday.FRI));

        // Delete invalid weekday
        assertFalse(stub.deleteShiftbyID(100, 100, Weekday.FRI));
        assertFalse(stub.deleteShiftbyID(100, 100, Weekday.WED));
        assertFalse(stub.deleteShiftbyID(100, 100, Weekday.SUN));

        System.out.println("Finished testDeleteShift");
    }
}
