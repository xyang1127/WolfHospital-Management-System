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

import static org.junit.Assert.*;

public class ReservationsTest {

    @Before
    public void resetDatabase() throws Exception {
        TestUtil.resetDatabase();
    }

    @Test
    public void assignsABed() throws Exception {
        final String inputString = "1\n4\nLucy\nDun\n1004\n";
        final String expectedOutput = "Ward Number: " +
                "Bed Number: " +
                "Enter Patient First Name: " +
                "Enter Patient Last Name: " +
                "Finding Patients...\n" +
                "\n" +
                "patient_id: 1004\n" +
                "first_name: Lucy\n" +
                "last_name: Dun\n" +
                "ssn: 000-04-1234\n" +
                "gender: F\n" +
                "DOB: 1985-01-30\n" +
                "phone_number: 919-838-7123\n" +
                "street_address: 10 TBC St\n" +
                "city: Raleigh\n" +
                "state: NC\n" +
                "zip_code: 27730\n" +
                "status: processing treatment plan 5\n" +
                "\n" +
                "Confirm ID of Patient: " +
                "Patient 1004 is assigned to bed 4 in ward 1\n";
        final InputStream input = new ByteArrayInputStream(inputString.getBytes());
        final OutputStream output = new TestUtil.MultiOutputStream()
                .addOutputStream(new ByteArrayOutputStream())
                .addOutputStream(System.out);
        final CommandLine commandLine = new CommandLine("", input, output);
        Reservations wardInfo = new Reservations(commandLine, TestUtil.getConnection());

        wardInfo.assignBed();

        final String actualOutput = output.toString();
        assertEquals(expectedOutput, actualOutput);

        String query = "SELECT * FROM Ward WHERE ward_number=1";
        Helper.queryAndHandle(TestUtil.getConnection(), query, rs -> {
            assertTrue(rs.next());
            assertEquals(rs.getString("ward_type"), "4");
            assertEquals(rs.getString("capacity"), "4");
            assertEquals(rs.getString("availability"), "1");
        });

        query = "SELECT * FROM Bed WHERE ward_number=1 AND bed_number=4";
        Helper.queryAndHandle(TestUtil.getConnection(), query, rs -> {
            assertTrue(rs.next());
            assertEquals(rs.getString("availability"), "0");
        });

        query = "SELECT * FROM PatientAssignedBed WHERE ward_number=1 AND bed_number=4";
        Helper.queryAndHandle(TestUtil.getConnection(), query, rs -> {
            assertTrue(rs.next());
            assertEquals(rs.getString("patient_id"), "1004");
        });

        assertTrue(TestUtil.getConnection().getAutoCommit());
    }

    @Test
    public void recoversFromABadQuery() throws Exception {
        String query = "ALTER TABLE Ward CHANGE COLUMN availability availability_new INT";
        Helper.insertAndHandle(TestUtil.getConnection(), query);

        final String inputString = "1\n4\nLucy\nDun\n1004\n";
        final String expectedOutput = "Ward Number: " +
                "Bed Number: " +
                "Enter Patient First Name: " +
                "Enter Patient Last Name: " +
                "Finding Patients...\n" +
                "\n" +
                "patient_id: 1004\n" +
                "first_name: Lucy\n" +
                "last_name: Dun\n" +
                "ssn: 000-04-1234\n" +
                "gender: F\n" +
                "DOB: 1985-01-30\n" +
                "phone_number: 919-838-7123\n" +
                "street_address: 10 TBC St\n" +
                "city: Raleigh\n" +
                "state: NC\n" +
                "zip_code: 27730\n" +
                "status: processing treatment plan 5\n" +
                "\n" +
                "Confirm ID of Patient: " +
                "Rolling back changes.\n";
        final InputStream input = new ByteArrayInputStream(inputString.getBytes());
        final OutputStream output = new TestUtil.MultiOutputStream()
                .addOutputStream(new ByteArrayOutputStream())
                .addOutputStream(System.out);
        final CommandLine commandLine = new CommandLine("", input, output);
        Reservations wardInfo = new Reservations(commandLine, TestUtil.getConnection());

        wardInfo.assignBed();

        final String actualOutput = output.toString();
        assertEquals(expectedOutput, actualOutput);

        query = "SELECT * FROM Ward WHERE ward_number=1";
        Helper.queryAndHandle(TestUtil.getConnection(), query, rs -> {
            assertTrue(rs.next());
            assertEquals(rs.getString("ward_type"), "4");
            assertEquals(rs.getString("capacity"), "4");
            assertEquals(rs.getString("availability_new"), "2");
        });

        query = "SELECT * FROM Bed WHERE ward_number=1 AND bed_number=4";
        Helper.queryAndHandle(TestUtil.getConnection(), query, rs -> {
            assertTrue(rs.next());
            assertEquals(rs.getString("availability"), "1");
        });

        query = "SELECT * FROM PatientAssignedBed WHERE ward_number=1 AND bed_number=4";
        Helper.queryAndHandle(TestUtil.getConnection(), query, rs -> {
            assertFalse(rs.next());
        });

        assertTrue(TestUtil.getConnection().getAutoCommit());
    }

    @Test
    public void printsOutUnassignableBed() throws Exception {
        final String inputString = "1\n1\nLucy\nDun\n1004\n";
        final String expectedOutput = "Ward Number: " +
                "Bed Number: " +
                "Bed is not available. Returning to previous menu\n";
        final InputStream input = new ByteArrayInputStream(inputString.getBytes());
        final OutputStream output = new TestUtil.MultiOutputStream()
                .addOutputStream(new ByteArrayOutputStream())
                .addOutputStream(System.out);
        final CommandLine commandLine = new CommandLine("", input, output);
        Reservations wardInfo = new Reservations(commandLine, TestUtil.getConnection());

        wardInfo.assignBed();

        final String actualOutput = output.toString();
        assertEquals(expectedOutput, actualOutput);

        String query = "SELECT * FROM Ward WHERE ward_number=1";
        Helper.queryAndHandle(TestUtil.getConnection(), query, rs -> {
            assertTrue(rs.next());
            assertEquals(rs.getString("ward_type"), "4");
            assertEquals(rs.getString("capacity"), "4");
            assertEquals(rs.getString("availability"), "2");
        });

        query = "SELECT * FROM Bed WHERE ward_number=1 AND bed_number=1";
        Helper.queryAndHandle(TestUtil.getConnection(), query, rs -> {
            assertTrue(rs.next());
            assertEquals(rs.getString("availability"), "0");
        });

        query = "SELECT * FROM PatientAssignedBed WHERE ward_number=1 AND bed_number=1";
        Helper.queryAndHandle(TestUtil.getConnection(), query, rs -> {
            assertTrue(rs.next());
            assertEquals(rs.getString("patient_id"), "1001");
        });

        assertTrue(TestUtil.getConnection().getAutoCommit());
    }
}