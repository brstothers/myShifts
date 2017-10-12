package comp3350.myshifts;

import junit.framework.Test;
import junit.framework.TestSuite;

import comp3350.myshifts.acceptance.AcceptanceTests;
import comp3350.myshifts.acceptance.EmployeesTest;

/**
 * Created by Liam on 2017-04-10.
 */

public class RunAcceptanceTests {

    public static TestSuite suite;

    public static Test suite()
    {
        suite = new TestSuite("Acceptance tests");
        suite.addTest(AcceptanceTests.suite());
        return suite;
    }
}
