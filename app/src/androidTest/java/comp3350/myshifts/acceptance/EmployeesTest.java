package comp3350.myshifts.acceptance;

import comp3350.myshifts.presentation.HomeActivity;
import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;
import junit.framework.Assert;

public class EmployeesTest extends ActivityInstrumentationTestCase2<HomeActivity> {

    private Solo solo;

    public EmployeesTest()
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

    public void testViewEmployees(){
        solo.waitForActivity("HomeActivity");
        Assert.assertTrue(solo.searchButton("Employee"));
        solo.clickOnButton("Employee");
        solo.assertCurrentActivity("Expected activity EmployeeOptionsActivity", "EmployeeOptionsActivity");

        Assert.assertTrue(solo.searchButton("View Employees"));
        solo.clickOnButton("View Employees");
        solo.assertCurrentActivity("Expected activity EmployeeActivity", "EmployeeActivity");
    }

    public void testViewShifts(){
        solo.waitForActivity("HomeActivity");
        Assert.assertTrue(solo.searchButton("Employee"));
        solo.clickOnButton("Employee");
        solo.assertCurrentActivity("Expected activity EmployeeOptionsActivity", "EmployeeOptionsActivity");

        Assert.assertTrue(solo.searchButton("View Employee"));
        solo.clickOnButton("View Employees");
        solo.assertCurrentActivity("Expected activity EmployeeActivity", "EmployeeActivity");

        solo.clickInList(1);
        Assert.assertTrue(solo.searchButton("Shifts"));
        solo.clickOnButton("Shifts");

    }

    public void testViewSchedule(){
        solo.waitForActivity("HomeActivity");
        solo.clickOnButton("Employee");
        solo.assertCurrentActivity("Expected activity EmployeeOptionsActivity", "EmployeeOptionsActivity");

        Assert.assertTrue(solo.searchButton("View Schedules"));
        solo.clickOnButton("View Schedules");
        solo.assertCurrentActivity("Expected activity EmployeeScheduleActivity", "EmployeeScheduleActivity");

        solo.clickInList(2);
        Assert.assertTrue(solo.searchButton("Shifts"));
        solo.clickOnButton("Shifts");
    }
}
