package csc540proj.reporting;

import csc540proj.utility.Helper;
import org.junit.BeforeClass;
import org.junit.Test;

public class DoctorOfPatientsMenuItemTest {

    @BeforeClass
    public static void setupDatabase() throws Exception {
        TestUtil.resetDatabase();

        final String query = "INSERT INTO MedicalRecord " +
                "(record_id, record_type, patient_id, start_date, end_date, diagnosis_details, prescription, specialist, test_status, test_result, doctor) " +
                "VALUES (12345, 'test', 1002, '2019/01/02', '2019/01/03', 'all good', 'advil', 103, 'done', 'negative', 100)";
        Helper.insertAndHandle(TestUtil.getConnection(), query);
    }

    @Test
    public void printsDoctorsPatients() throws Exception {
        String inputString = "Mary\nSmith\n100\n";
        String expected = "\n" +
                "BEGIN PATIENT 1001\n" +
                "First name: David\n" +
                "Last name: Aldridge\n" +
                "Gender: M\n" +
                "DOB: 1980-01-30\n" +
                "Age: 39\n" +
                "Status: processing treatment plan 20\n" +
                "\n" +
                "BEGIN PATIENT 1002\n" +
                "First name: Sarah\n" +
                "Last name: Belford\n" +
                "Gender: F\n" +
                "DOB: 1971-01-30\n" +
                "Age: 48\n" +
                "Status: processing treatment plan 20\n" +
                "\n" +
                "BEGIN PATIENT 1003\n" +
                "First name: Joseph\n" +
                "Last name: Crawford\n" +
                "Gender: M\n" +
                "DOB: 1987-01-30\n" +
                "Age: 32\n" +
                "Status: processing treatment plan 10\n";
        TestUtil.assertMenuItemBehavior(inputString, expected, DoctorOfPatientsMenuItem.class, true);
    }

    @Test
    public void whenTheStaffIdIsInvalid() throws Exception {
        String inputString = "Some\nDoctor\nsome invalid staff id\n";
        String expected = "Invalid staff ID: some invalid staff id\n";
        TestUtil.assertMenuItemBehavior(inputString, expected, DoctorOfPatientsMenuItem.class, true);
    }

    @Test
    public void whenTheDoctorDoesntHavePatients() throws Exception {
        String inputString = "Peter\nBrown\n105\n";
        String expected = "No patients for this doctor\n";
        TestUtil.assertMenuItemBehavior(inputString, expected, DoctorOfPatientsMenuItem.class, true);
    }

    @Test
    public void whenTheStaffIdIsntADoctor() throws Exception {
        String inputString = "Peter\nBrown\n102\n";
        String expected = "No patients for this doctor\n";
        TestUtil.assertMenuItemBehavior(inputString, expected, DoctorOfPatientsMenuItem.class, true);
    }
}