package csc540proj.reporting;

import org.junit.BeforeClass;
import org.junit.Test;

public class PatientsPerMonthMenuItemTest {

    @BeforeClass
    public static void resetDatabase() throws Exception {
        TestUtil.resetDatabase();
    }

    @Test
    public void printsPatientsPerMonthWithPatients() throws Exception {
        String inputString = "2019\n03\n";
        String expected = "Enter year (YYYY): Enter month (MM): " +
                "Patient count: 4\n";
        TestUtil.assertMenuItemBehavior(inputString, expected, PatientsPerMonthMenuItem.class);
    }

    @Test
    public void printsPatientsPerMonthWithNoPatients() throws Exception {
        String inputString = "2014\n02\n";
        String expected = "Enter year (YYYY): Enter month (MM): " +
                "Patient count: 0\n";
        TestUtil.assertMenuItemBehavior(inputString, expected, PatientsPerMonthMenuItem.class);
    }

    @Test
    public void printsPoorlyFormattedYear() throws Exception {
        String inputString = "201\n";
        String expected = "Enter year (YYYY): Year format is invalid\n";
        TestUtil.assertMenuItemBehavior(inputString, expected, PatientsPerMonthMenuItem.class);
    }

    @Test
    public void printsPoorlyFormattedMonth() throws Exception {
        String inputString = "2013\nab\n";
        String expected = "Enter year (YYYY): Enter month (MM): Month format is invalid\n";
        TestUtil.assertMenuItemBehavior(inputString, expected, PatientsPerMonthMenuItem.class);
    }
}