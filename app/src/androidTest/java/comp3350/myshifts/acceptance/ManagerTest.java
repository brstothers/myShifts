package comp3350.myshifts.acceptance;

import comp3350.myshifts.presentation.HomeActivity;
import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;
import junit.framework.Assert;

public class ManagerTest extends ActivityInstrumentationTestCase2<HomeActivity>
{
    private Solo solo;

    public ManagerTest()
    {
        super(HomeActivity.class);
    }

    public void setUp() throws Exception
    {
        solo = new Solo(getInstrumentation(), getActivity());

        // Disable this for full acceptance test
        // System.out.println("Injecting stub database.");
        // Services.createDataAccess(new DataAccessStub(Main.dbName));
    }

    @Override
    public void tearDown() throws Exception
    {
        solo.finishOpenedActivities();
    }

    public void testCreateEmployee()
    {
        solo.waitForActivity("HomeActivity");
        Assert.assertTrue(solo.searchButton("Manager"));
        solo.clickOnButton("Manager");
        solo.assertCurrentActivity("Expected activity ManagerOptionsActivity", "ManagerOptionsActivity");

        Assert.assertTrue(solo.searchButton("Manage Employees"));
        solo.clickOnButton("Manage Employees");

        Assert.assertTrue(solo.searchButton("Create"));
        solo.clickOnButton("Create");
        solo.waitForDialogToOpen();

        solo.clearEditText(0);
        solo.enterText(0,"Ismail");
        solo.clearEditText(1);
        solo.enterText(1, "2049634512");
        solo.clearEditText(2);
        solo.enterText(2,"15.1");

        Assert.assertTrue(solo.searchButton("Submit"));
        solo.clickOnButton("Submit");
        solo.waitForDialogToClose();

        Assert.assertTrue(solo.searchText("Ismail"));
        Assert.assertTrue(solo.searchText("2049634512"));
        Assert.assertTrue(solo.searchText("15.1"));
    }

    public void testInvalidCreateEmployee()
    {
        solo.waitForActivity("HomeActivity");
        Assert.assertTrue(solo.searchButton("Manager"));
        solo.clickOnButton("Manager");
        solo.assertCurrentActivity("Expected activity ManagerOptionsActivity", "ManagerOptionsActivity");

        Assert.assertTrue(solo.searchButton("Manage Employees"));
        solo.clickOnButton("Manage Employees");

        Assert.assertTrue(solo.searchButton("Create"));
        solo.clickOnButton("Create");
        solo.waitForDialogToOpen();

        Assert.assertTrue(solo.searchButton("Submit"));
        solo.clickOnButton("Submit");
        solo.waitForDialogToClose();

        boolean actual = solo.searchButton("Create");
        assertEquals("Employee not Created, form must be complete.",true, actual);

        Assert.assertTrue(solo.searchButton("Create"));
        solo.clickOnButton("Create");
        solo.waitForDialogToOpen();

        solo.clearEditText(0);
        solo.enterText(0,"Ismail");
        solo.clearEditText(1);
        solo.enterText(1, "2049634512");

        Assert.assertTrue(solo.searchText("Ismail"));
        Assert.assertTrue(solo.searchText("2049634512"));

        Assert.assertTrue(solo.searchButton("Submit"));
        solo.clickOnButton("Submit");
        solo.waitForDialogToClose();

        actual = solo.searchButton("Create");
        assertEquals("Employee not Created, form must be complete.",true, actual);

        Assert.assertTrue(solo.searchButton("Create"));
        solo.clickOnButton("Create");
        solo.waitForDialogToOpen();

        solo.clearEditText(0);
        solo.enterText(0,"Ismail");
        solo.clearEditText(1);
        solo.enterText(1, "2049634");
        solo.enterText(2, "14.1");

        Assert.assertTrue(solo.searchText("Ismail"));
        Assert.assertTrue(solo.searchText("2049634"));

        Assert.assertTrue(solo.searchButton("Submit"));
        solo.clickOnButton("Submit");
        solo.waitForDialogToClose();

        actual = solo.searchButton("Create");
        assertEquals("Employee not Created, form must be complete.",true, actual);

        Assert.assertTrue(solo.searchButton("Create"));
        solo.clickOnButton("Create");
        solo.waitForDialogToOpen();

        solo.clearEditText(0);
        solo.enterText(0,"Ismail");
        solo.clearEditText(1);
        solo.enterText(1, "2049634512");
        solo.enterText(2, "0");

        Assert.assertTrue(solo.searchText("Ismail"));
        Assert.assertTrue(solo.searchText("2049634"));

        Assert.assertTrue(solo.searchButton("Submit"));
        solo.clickOnButton("Submit");
        solo.waitForDialogToClose();

        actual = solo.searchButton("Create");
        assertEquals("Employee not Created, form must be complete.",true, actual);

    }

    public void testUpdateEmployee()
    {
        solo.waitForActivity("HomeActivity");
        Assert.assertTrue(solo.searchButton("Manager"));
        solo.clickOnButton("Manager");
        solo.assertCurrentActivity("Expected activity ManagerOptionsActivity", "ManagerOptionsActivity");

        Assert.assertTrue(solo.searchButton("Manage Employees"));
        solo.clickOnButton("Manage Employees");

        solo.clickInList(0);

        Assert.assertTrue(solo.searchButton("Update"));
        solo.clickOnButton("Update");
        solo.waitForDialogToOpen();
        solo.clearEditText(1);
        solo.enterText(1, "2049639603");

        Assert.assertTrue(solo.searchText("2049639603"));

        Assert.assertTrue(solo.searchButton("Submit"));
        solo.clickOnButton("Submit");
        solo.waitForDialogToClose();

        solo.clickInList(1);
        Assert.assertTrue(solo.searchButton("Update"));
        solo.clickOnButton("Update");
        solo.waitForDialogToOpen();
        solo.clearEditText(2);
        solo.enterText(2,"25.1");

        Assert.assertTrue(solo.searchButton("Submit"));
        solo.clickOnButton("Submit");
        solo.waitForDialogToClose();

        Assert.assertTrue(solo.searchText("2049639603"));
        Assert.assertTrue(solo.searchText("25.1"));
    }

    public void testInvalidUpdateEmployee()
    {
        solo.waitForActivity("HomeActivity");
        Assert.assertTrue(solo.searchButton("Manager"));
        solo.clickOnButton("Manager");
        solo.assertCurrentActivity("Expected activity ManagerOptionsActivity", "ManagerOptionsActivity");

        Assert.assertTrue(solo.searchButton("Manage Employees"));
        solo.clickOnButton("Manage Employees");

        solo.clickInList(0);

        Assert.assertTrue(solo.searchButton("Update"));
        solo.clickOnButton("Update");
        solo.waitForDialogToOpen();
        solo.clearEditText(1);

        Assert.assertTrue(solo.searchButton("Submit"));
        solo.clickOnButton("Submit");
        solo.waitForDialogToClose();

        boolean actual = solo.searchButton("Update");
        assertEquals("Employee not updated, form must be complete.",true, actual);
    }

    public void testDeleteEmployee()
    {
        solo.waitForActivity("HomeActivity");
        Assert.assertTrue(solo.searchButton("Manager"));
        solo.clickOnButton("Manager");
        solo.assertCurrentActivity("Expected activity ManagerOptionsActivity", "ManagerOptionsActivity");

        Assert.assertTrue(solo.searchButton("Manage Employees"));
        solo.clickOnButton("Manage Employees");

        solo.clickInList(3);

        Assert.assertTrue(solo.searchButton("Delete"));
        solo.clickOnButton("Delete");
        solo.waitForDialogToOpen();
        Assert.assertTrue(solo.searchButton("Delete"));
        solo.clickOnButton("Delete");
        solo.waitForDialogToClose();
    }

    public void testCreateSchedule()
    {
        solo.waitForActivity("HomeActivity");
        Assert.assertTrue(solo.searchButton("Manager"));
        solo.clickOnButton("Manager");
        solo.assertCurrentActivity("Expected activity ManagerOptionsActivity", "ManagerOptionsActivity");

        Assert.assertTrue(solo.searchButton("Manage Schedules"));
        solo.clickOnButton("Manage Schedules");
        solo.assertCurrentActivity("Expected activity ManagerScheduleActivity", "ManagerScheduleActivity");

        Assert.assertTrue(solo.searchButton("Create"));
        solo.clickOnButton("Create");
        solo.waitForDialogToOpen();

        solo.pressSpinnerItem(0, 2);
        solo.pressSpinnerItem(1, 10);
        solo.clearEditText(0);
        solo.enterText(0, "2017");

        Assert.assertTrue(solo.isSpinnerTextSelected(0, "Week 3"));
        Assert.assertTrue(solo.isSpinnerTextSelected(1, "November"));
        Assert.assertTrue(solo.searchText("2017"));

        Assert.assertTrue(solo.searchButton("Submit"));
        solo.clickOnButton("Submit");
        solo.waitForDialogToClose();

        Assert.assertTrue(solo.searchText("Week 3"));
        Assert.assertTrue(solo.searchText("November 2017"));
    }

    public void testInvalidCreateSchedule()
    {
        solo.waitForActivity("HomeActivity");
        Assert.assertTrue(solo.searchButton("Manager"));
        solo.clickOnButton("Manager");
        solo.assertCurrentActivity("Expected activity ManagerOptionsActivity", "ManagerOptionsActivity");

        Assert.assertTrue(solo.searchButton("Manage Schedules"));
        solo.clickOnButton("Manage Schedules");
        solo.assertCurrentActivity("Expected activity ManagerScheduleActivity", "ManagerScheduleActivity");

        Assert.assertTrue(solo.searchButton("Create"));
        solo.clickOnButton("Create");
        solo.waitForDialogToOpen();

        solo.pressSpinnerItem(0, 2);
        solo.pressSpinnerItem(1, 10);
        solo.clearEditText(0);

        Assert.assertTrue(solo.isSpinnerTextSelected(0, "Week 3"));
        Assert.assertTrue(solo.isSpinnerTextSelected(1, "November"));

        Assert.assertTrue(solo.searchButton("Submit"));
        solo.clickOnButton("Submit");
        solo.waitForDialogToClose();

        boolean actual = solo.searchButton("Create");
        assertEquals("Schedule not Created, form must be complete.",true, actual);

        Assert.assertTrue(solo.searchButton("Create"));
        solo.clickOnButton("Create");
        solo.waitForDialogToOpen();

        solo.pressSpinnerItem(0, 2);
        solo.pressSpinnerItem(1, 10);
        solo.clearEditText(0);
        solo.enterText(0, "1999");

        Assert.assertTrue(solo.isSpinnerTextSelected(0, "Week 3"));
        Assert.assertTrue(solo.isSpinnerTextSelected(1, "November"));
        Assert.assertTrue(solo.searchText("1999"));

        solo.clickOnButton("Submit");
        solo.waitForDialogToClose();

        actual = solo.searchButton("Create");
        assertEquals("Schedule not Created, year must be between 2000 and 2100.",true, actual);
    }

    public void testUpdateSchedule()
    {
        solo.waitForActivity("HomeActivity");
        Assert.assertTrue(solo.searchButton("Manager"));
        solo.clickOnButton("Manager");
        solo.assertCurrentActivity("Expected activity ManagerOptionsActivity", "ManagerOptionsActivity");

        Assert.assertTrue(solo.searchButton("Manage Schedules"));
        solo.clickOnButton("Manage Schedules");
        solo.assertCurrentActivity("Expected activity ManagerScheduleActivity", "ManagerScheduleActivity");

        solo.clickInList(0);

        Assert.assertTrue(solo.searchButton("Update"));
        solo.clickOnButton("Update");
        solo.waitForDialogToOpen();

        solo.pressSpinnerItem(0,-4);
        solo.pressSpinnerItem(1,-10);
        solo.clearEditText(0);
        solo.enterText(0, "2016");

        Assert.assertTrue(solo.isSpinnerTextSelected(0, "Week 1"));
        Assert.assertTrue(solo.isSpinnerTextSelected(1, "January"));
        Assert.assertTrue(solo.searchText("2016"));

        Assert.assertTrue(solo.searchButton("Submit"));
        solo.clickOnButton("Submit");
        solo.waitForDialogToClose();
    }

    public void testInvalidUpdateSchedule()
    {
        solo.waitForActivity("HomeActivity");
        Assert.assertTrue(solo.searchButton("Manager"));
        solo.clickOnButton("Manager");
        solo.assertCurrentActivity("Expected activity ManagerOptionsActivity", "ManagerOptionsActivity");

        Assert.assertTrue(solo.searchButton("Manage Schedules"));
        solo.clickOnButton("Manage Schedules");
        solo.assertCurrentActivity("Expected activity ManagerScheduleActivity", "ManagerScheduleActivity");

        solo.clickInList(1);

        Assert.assertTrue(solo.searchButton("Update"));
        solo.clickOnButton("Update");
        solo.waitForDialogToOpen();

        solo.pressSpinnerItem(0,-4);
        solo.pressSpinnerItem(1,-10);
        solo.clearEditText(0);
        solo.enterText(0, "22000");

        Assert.assertTrue(solo.isSpinnerTextSelected(0, "Week 1"));
        Assert.assertTrue(solo.isSpinnerTextSelected(1, "January"));
        Assert.assertTrue(solo.searchText("22000"));

        Assert.assertTrue(solo.searchButton("Submit"));
        solo.clickOnButton("Submit");
        solo.waitForDialogToClose();

        boolean actual = solo.searchButton("Update");
        assertEquals("Schedule not Updated, year must be between 2000 and 2100.",true, actual);

        Assert.assertTrue(solo.searchButton("Update"));
        solo.clickOnButton("Update");
        solo.waitForDialogToOpen();

        solo.pressSpinnerItem(0,-4);
        solo.pressSpinnerItem(1,-10);
        solo.clearEditText(0);

        Assert.assertTrue(solo.isSpinnerTextSelected(0, "Week 1"));
        Assert.assertTrue(solo.isSpinnerTextSelected(1, "January"));

        Assert.assertTrue(solo.searchButton("Submit"));
        solo.clickOnButton("Submit");
        solo.waitForDialogToClose();

        actual = solo.searchButton("Update");
        assertEquals("Schedule not Updated, form must be complete.",true, actual);
    }

    public void testDeleteSchedule()
    {
        solo.waitForActivity("HomeActivity");
        Assert.assertTrue(solo.searchButton("Manager"));
        solo.clickOnButton("Manager");
        solo.assertCurrentActivity("Expected activity ManagerOptionsActivity", "ManagerOptionsActivity");

        Assert.assertTrue(solo.searchButton("Manage Schedules"));
        solo.clickOnButton("Manage Schedules");
        solo.assertCurrentActivity("Expected activity ManagerScheduleActivity", "ManagerScheduleActivity");

        solo.clickInList(0);

        Assert.assertTrue(solo.searchButton("Delete"));
        solo.clickOnButton("Delete");
        solo.waitForDialogToOpen();

        Assert.assertTrue(solo.searchButton("Delete"));;
        solo.clickOnButton("Delete");
        solo.waitForDialogToClose();
    }

    public void testCreateShift()
    {
        solo.waitForActivity("HomeActivity");
        Assert.assertTrue(solo.searchButton("Manager"));
        solo.clickOnButton("Manager");
        solo.assertCurrentActivity("Expected activity ManagerOptionsActivity", "ManagerOptionsActivity");

        Assert.assertTrue(solo.searchButton("Manage Schedules"));
        solo.clickOnButton("Manage Schedules");
        solo.assertCurrentActivity("Expected activity ManagerScheduleActivity", "ManagerScheduleActivity");

        solo.clickInList(1);
        Assert.assertTrue(solo.searchButton("Shifts"));
        solo.clickOnButton("Shifts");
        solo.assertCurrentActivity("Expected activity Manager", "ManagerShiftActivity");

        Assert.assertTrue(solo.searchButton("Create"));
        solo.clickOnButton("Create");

        solo.pressSpinnerItem(0, 0);
        solo.pressSpinnerItem(2, 4);
        solo.enterText(0,"9");
        solo.enterText(1,"17");

        Assert.assertTrue(solo.isSpinnerTextSelected(2, "Friday"));
        Assert.assertTrue(solo.searchText("9"));
        Assert.assertTrue(solo.searchText("17"));

        Assert.assertTrue(solo.searchButton("Submit"));
        solo.clickOnButton("Submit");

        solo.waitForDialogToClose();
    }

    public void testInvalidCreateShift()
    {
        solo.waitForActivity("HomeActivity");
        Assert.assertTrue(solo.searchButton("Manager"));
        solo.clickOnButton("Manager");
        solo.assertCurrentActivity("Expected activity ManagerOptionsActivity", "ManagerOptionsActivity");

        Assert.assertTrue(solo.searchButton("Manage Schedules"));
        solo.clickOnButton("Manage Schedules");
        solo.assertCurrentActivity("Expected activity ManagerScheduleActivity", "ManagerScheduleActivity");

        solo.clickInList(1);
        Assert.assertTrue(solo.searchButton("Shifts"));
        solo.clickOnButton("Shifts");
        solo.assertCurrentActivity("Expected activity Manager", "ManagerShiftActivity");

        Assert.assertTrue(solo.searchButton("Create"));
        solo.clickOnButton("Create");

        solo.pressSpinnerItem(0, 0);
        solo.pressSpinnerItem(2, 4);
        solo.enterText(0,"9");

        Assert.assertTrue(solo.isSpinnerTextSelected(2, "Friday"));
        Assert.assertTrue(solo.searchText("9"));

        Assert.assertTrue(solo.searchButton("Submit"));
        solo.clickOnButton("Submit");

        boolean actual = solo.searchButton("Submit");
        assertEquals("Shift not Created, form must be complete.",true, actual);

        Assert.assertTrue(solo.searchButton("Cancel"));
        solo.clickOnButton("Cancel");

        Assert.assertTrue(solo.searchButton("Create"));
        solo.clickOnButton("Create");

        solo.pressSpinnerItem(0, 0);
        solo.pressSpinnerItem(2, 4);
        solo.enterText(0,"9");
        solo.enterText(1,"25");

        Assert.assertTrue(solo.isSpinnerTextSelected(2, "Friday"));
        Assert.assertTrue(solo.searchText("9"));
        Assert.assertTrue(solo.searchText("25"));

        Assert.assertTrue(solo.searchButton("Submit"));
        solo.clickOnButton("Submit");

        assertEquals("Start and End Time Must Be Between 0 and 24.",true, actual);

        Assert.assertTrue(solo.searchButton("Cancel"));
        solo.clickOnButton("Cancel");

        Assert.assertTrue(solo.searchButton("Create"));
        solo.clickOnButton("Create");

        solo.pressSpinnerItem(0, 0);
        solo.pressSpinnerItem(2, 4);
        solo.enterText(0,"9");

        Assert.assertTrue(solo.isSpinnerTextSelected(2, "Friday"));
        Assert.assertTrue(solo.searchText("9"));

        Assert.assertTrue(solo.searchButton("Submit"));
        solo.clickOnButton("Submit");

        actual = solo.searchButton("Submit");
        assertEquals("Shift not Created, form must be complete.",true, actual);

        Assert.assertTrue(solo.searchButton("Cancel"));
        solo.clickOnButton("Cancel");

        Assert.assertTrue(solo.searchButton("Create"));
        solo.clickOnButton("Create");

        solo.pressSpinnerItem(0, 0);
        solo.pressSpinnerItem(2, 4);
        solo.enterText(0,"5");
        solo.enterText(1,"2");

        Assert.assertTrue(solo.isSpinnerTextSelected(2, "Friday"));
        Assert.assertTrue(solo.searchText("5"));
        Assert.assertTrue(solo.searchText("2"));

        Assert.assertTrue(solo.searchButton("Submit"));
        solo.clickOnButton("Submit");

        actual = solo.searchButton("Submit");
        assertEquals("Start time must be less than End time.",true, actual);

        Assert.assertTrue(solo.searchButton("Cancel"));
        solo.clickOnButton("Cancel");
    }

    public void testUpdateShift()
    {
        solo.waitForActivity("HomeActivity");
        Assert.assertTrue(solo.searchButton("Manager"));
        solo.clickOnButton("Manager");
        solo.assertCurrentActivity("Expected activity ManagerOptionsActivity", "ManagerOptionsActivity");

        Assert.assertTrue(solo.searchButton("Manage Schedules"));
        solo.clickOnButton("Manage Schedules");
        solo.assertCurrentActivity("Expected activity ManagerScheduleActivity", "ManagerScheduleActivity");

        solo.clickInList(1);
        Assert.assertTrue(solo.searchButton("Shifts"));
        solo.clickOnButton("Shifts");
        solo.assertCurrentActivity("Expected activity Manager", "ManagerShiftActivity");

        solo.clickInList(0);

        Assert.assertTrue(solo.searchButton("Update"));
        solo.clickOnButton("Update");
        solo.waitForDialogToOpen();

        solo.clearEditText(3);
        solo.enterText(3, "10");
        solo.clearEditText(4);
        solo.enterText(4, "16");

        Assert.assertTrue(solo.searchText("10"));
        Assert.assertTrue(solo.searchText("16"));

        Assert.assertTrue(solo.searchButton("Submit"));
        solo.clickOnButton("Submit");
        solo.waitForDialogToClose();
    }

    public void testInvalidUpdateShift()
    {
        solo.waitForActivity("HomeActivity");
        Assert.assertTrue(solo.searchButton("Manager"));
        solo.clickOnButton("Manager");
        solo.assertCurrentActivity("Expected activity ManagerOptionsActivity", "ManagerOptionsActivity");

        Assert.assertTrue(solo.searchButton("Manage Schedules"));
        solo.clickOnButton("Manage Schedules");
        solo.assertCurrentActivity("Expected activity ManagerScheduleActivity", "ManagerScheduleActivity");

        solo.clickInList(1);
        Assert.assertTrue(solo.searchButton("Shifts"));
        solo.clickOnButton("Shifts");
        solo.assertCurrentActivity("Expected activity Manager", "ManagerShiftActivity");

        solo.clickInList(0);

        Assert.assertTrue(solo.searchButton("Update"));
        solo.clickOnButton("Update");
        solo.waitForDialogToOpen();

        solo.clearEditText(3);
        solo.enterText(3, "10");
        solo.clearEditText(4);
        solo.enterText(4, "35");

        Assert.assertTrue(solo.searchText("10"));
        Assert.assertTrue(solo.searchText("35"));

        solo.clickOnButton("Submit");
        boolean actual = solo.searchButton("Submit");
        assertEquals("Start and End Time Must Be Between 0 and 24.",true, actual);

        Assert.assertTrue(solo.searchButton("Cancel"));
        solo.clickOnButton("Cancel");
        solo.waitForDialogToClose();


        solo.clickInList(1);
        solo.clickInList(1);

        Assert.assertTrue(solo.searchButton("Update"));
        solo.clickOnButton("Update");
        solo.waitForDialogToOpen();

        solo.clearEditText(3);
        solo.enterText(3, "10");
        solo.clearEditText(4);

        Assert.assertTrue(solo.searchText("10"));

        Assert.assertTrue(solo.searchButton("Submit"));
        solo.clickOnButton("Submit");
        actual = solo.searchButton("Submit");
        assertEquals("Shift not Updated, form must be complete.",true, actual);
        solo.clickOnButton("Cancel");
        solo.waitForDialogToClose();
        solo.waitForDialogToClose();


    }

    public void testDeleteShift()
    {
        solo.waitForActivity("HomeActivity");
        Assert.assertTrue(solo.searchButton("Manager"));
        solo.clickOnButton("Manager");
        solo.assertCurrentActivity("Expected activity ManagerOptionsActivity", "ManagerOptionsActivity");

        Assert.assertTrue(solo.searchButton("Manage Schedules"));
        solo.clickOnButton("Manage Schedules");
        solo.assertCurrentActivity("Expected activity ManagerScheduleActivity", "ManagerScheduleActivity");

        solo.clickInList(1);
        Assert.assertTrue(solo.searchButton("Shifts"));
        solo.clickOnButton("Shifts");
        solo.assertCurrentActivity("Expected activity Manager", "ManagerShiftActivity");

        solo.clickInList(0);
        solo.waitForDialogToOpen();

        Assert.assertTrue(solo.searchButton("Delete"));
        solo.clickOnButton("Delete");
        solo.waitForDialogToClose();
    }

    public void testScheduleSummary(){
        solo.waitForActivity("HomeActivity");
        Assert.assertTrue(solo.searchButton("Manager"));
        solo.clickOnButton("Manager");
        solo.assertCurrentActivity("Expected activity ManagerOptionsActivity", "ManagerOptionsActivity");

        Assert.assertTrue(solo.searchButton("Manage Schedules"));
        solo.clickOnButton("Manage Schedules");
        solo.assertCurrentActivity("Expected activity ManagerScheduleActivity", "ManagerScheduleActivity");

        solo.clickInList(1);
        Assert.assertTrue(solo.searchButton("Summary"));
        solo.clickOnButton("Summary");
        solo.assertCurrentActivity("Expected activity Manager", "ManagerScheduleActivity");
    }

    public void testShiftSummary(){
        solo.waitForActivity("HomeActivity");
        Assert.assertTrue(solo.searchButton("Manager"));
        solo.clickOnButton("Manager");
        solo.assertCurrentActivity("Expected activity ManagerOptionsActivity", "ManagerOptionsActivity");

        Assert.assertTrue(solo.searchButton("Manage Employees"));
        solo.clickOnButton("Manage Employees");
        solo.assertCurrentActivity("Expected activity ManagerActivity", "ManagerActivity");

        solo.clickInList(1);
        Assert.assertTrue(solo.searchButton("Summary"));
        solo.clickOnButton("Summary");
    }
}
