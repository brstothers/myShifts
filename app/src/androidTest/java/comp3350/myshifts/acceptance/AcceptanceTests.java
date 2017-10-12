package comp3350.myshifts.acceptance;

/**
 * Created by Liam on 2017-04-10.
 */

import junit.framework.Test;
import junit.framework.TestSuite;

public class AcceptanceTests
{
    public static TestSuite suite;

    public static Test suite()
    {
        suite = new TestSuite("Acceptance tests");
        suite.addTestSuite(EmployeesTest.class);
        suite.addTestSuite(ManagerTest.class);
        return suite;
    }
}
