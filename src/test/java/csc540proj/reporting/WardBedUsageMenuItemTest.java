package csc540proj.reporting;

import org.junit.BeforeClass;
import org.junit.Test;

public class WardBedUsageMenuItemTest {

    @BeforeClass
    public static void resetDatabase() throws Exception {
        TestUtil.resetDatabase();
    }

    @Test
    public void getsWardBedUsage() throws Exception {
        final String expected = "Ward  1, Bed  1, Available: NO\n" +
                "Ward  1, Bed  4, Available: YES\n" +
                "Ward  1, Bed  3, Available: YES\n" +
                "Ward  1, Bed  2, Available: NO\n" +
                "Ward  2, Bed  3, Available: YES\n" +
                "Ward  2, Bed  4, Available: YES\n" +
                "Ward  2, Bed  2, Available: YES\n" +
                "Ward  2, Bed  1, Available: NO\n" +
                "Ward  3, Bed  1, Available: YES\n" +
                "Ward  3, Bed  2, Available: YES\n" +
                "Ward  4, Bed  2, Available: YES\n" +
                "Ward  4, Bed  1, Available: YES\n";
        TestUtil.assertMenuItemBehavior("", expected, WardBedUsageMenuItem.class);
    }
}