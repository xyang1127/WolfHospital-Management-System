package csc540proj.information;

import csc540proj.reporting.TestUtil;
import csc540proj.utility.CommandLine;
import csc540proj.utility.Helper;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WardInfoTest {

    @Before
    public void resetDatabase() throws Exception {
        TestUtil.resetDatabase();
    }

    @Test
    public void createsAWardWithNewChargePerDay() throws Exception {
        final String inputString = "99\n4\n4\n25\n";
        final String expectedOutput = "Enter ward number: " +
                "Enter ward type (1, 2, 4): " +
                "Enter ward capacity: " +
                "Enter ward charge per day (or leave blank for current ward charge per day): " +
                "Entering Ward...\n" +
                "Entering 4 beds\n" +
                "Done.\n";
        runTest(inputString, expectedOutput, "25");
    }

    @Test
    public void createsAWardWithExistingChargePerDay() throws Exception {
        final String inputString = "99\n4\n4\n\n";
        final String expectedOutput = "Enter ward number: " +
                "Enter ward type (1, 2, 4): " +
                "Enter ward capacity: " +
                "Enter ward charge per day (or leave blank for current ward charge per day): " +
                "Entering Ward...\n" +
                "Entering 4 beds\n" +
                "Done.\n";
        runTest(inputString, expectedOutput, "50");
    }

    @Test
    public void createsAWardWithABadChargePerDay() throws Exception {
        final String inputString = "99\n4\n4\n1.a\na.1\n123.51\n";
        final String expectedOutput = "Enter ward number: " +
                "Enter ward type (1, 2, 4): " +
                "Enter ward capacity: " +
                "Enter ward charge per day (or leave blank for current ward charge per day): " +
                "Invalid format\n" +
                "Enter ward charge per day (or leave blank for current ward charge per day): " +
                "Invalid format\n" +
                "Enter ward charge per day (or leave blank for current ward charge per day): " +
                "Entering Ward...\n" +
                "Entering 4 beds\n" +
                "Done.\n";
        runTest(inputString, expectedOutput, "123.51");
    }

    private static void runTest(
            final String inputString,
            final String expectedOutput,
            final String expectedChargePerDay
    ) throws Exception {
        final InputStream input = new ByteArrayInputStream(inputString.getBytes());
        final OutputStream output = new TestUtil.MultiOutputStream()
                .addOutputStream(new ByteArrayOutputStream())
                .addOutputStream(System.out);
        final CommandLine commandLine = new CommandLine("", input, output);
        WardInfo wardInfo = new WardInfo(commandLine, TestUtil.getConnection());

        wardInfo.enterWard();

        final String actualOutput = output.toString();
        assertEquals(expectedOutput, actualOutput);

        Helper.queryAndHandle(TestUtil.getConnection(), "SELECT * FROM Ward WHERE ward_number=99", rs -> {
            assertTrue(rs.next());
            assertEquals(rs.getString("ward_type"), "4");
            assertEquals(rs.getString("capacity"), "4");
            assertEquals(rs.getString("availability"), "4");
        });

        Helper.queryAndHandle(TestUtil.getConnection(), "SELECT * FROM WardType WHERE ward_type=4", rs -> {
            assertTrue(rs.next());
            assertEquals(rs.getString("charge_per_day"), expectedChargePerDay);
        });
    }
}