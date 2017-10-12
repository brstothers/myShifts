package comp3350.myshifts.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

import comp3350.myshifts.tests.business.AccessEmployeesTest;
import comp3350.myshifts.tests.business.AccessSchedulesTest;
import comp3350.myshifts.tests.business.AccessShiftsTest;
import comp3350.myshifts.tests.business.SummarizeTest;
import comp3350.myshifts.tests.integration.BusinessPersistenceSeamTest;
import comp3350.myshifts.tests.integration.DataAccessHSQLDBTest;
import comp3350.myshifts.tests.objects.EmployeeSummaryTest;
import comp3350.myshifts.tests.objects.EmployeeTest;
import comp3350.myshifts.tests.objects.ScheduleSummaryTest;
import comp3350.myshifts.tests.objects.ScheduleTest;
import comp3350.myshifts.tests.objects.ShiftTest;
import comp3350.myshifts.tests.persistence.DataAccessStubTest;


public class AllTests
{
	public static TestSuite suite;

    public static Test suite()
    {
        suite = new TestSuite("All tests");
        testObjects();
        testBusiness();
        testPersistence();
        testIntegration();
        return suite;
    }

    private static void testObjects()
    {
        suite.addTestSuite(EmployeeTest.class);
        suite.addTestSuite(ShiftTest.class);
        suite.addTestSuite(ScheduleTest.class);
        suite.addTestSuite(EmployeeSummaryTest.class);
        suite.addTestSuite(ScheduleSummaryTest.class);
    }

    private static void testBusiness()
    {
        suite.addTestSuite(AccessEmployeesTest.class);
        suite.addTestSuite(AccessSchedulesTest.class);
        suite.addTestSuite(AccessShiftsTest.class);
        suite.addTestSuite(SummarizeTest.class);
    }

    private static void testIntegration()
    {
        suite.addTestSuite(DataAccessHSQLDBTest.class);
        suite.addTestSuite(BusinessPersistenceSeamTest.class);
    }

    private static void testPersistence()
    {
        suite.addTestSuite(DataAccessStubTest.class);
    }

}
