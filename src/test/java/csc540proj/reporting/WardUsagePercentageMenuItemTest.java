package csc540proj.reporting;

import org.junit.BeforeClass;
import org.junit.Test;

public class WardUsagePercentageMenuItemTest {

    @BeforeClass
    public static void resetDatabase() throws Exception {
        TestUtil.resetDatabase();
    }

    @Test
    public void printsWardUsage() throws Exception {
        final String expected = "Ward  1, Usage: 50%\n" +
                "Ward  2, Usage: 25%\n" +
                "Ward  3, Usage: 00%\n" +
                "Ward  4, Usage: 00%\n";
        TestUtil.assertMenuItemBehavior("", expected, WardUsagePercentageMenuItem.class);
    }

}