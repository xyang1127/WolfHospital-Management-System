package csc540proj.reporting;

import csc540proj.utility.Helper;
import org.junit.BeforeClass;
import org.junit.Test;

public class MedicalHistoryMenuItemTest {

    @BeforeClass
    public static void resetDatabase() throws Exception {
        TestUtil.resetDatabase();

        String query = "INSERT INTO MedicalRecord " +
                "(record_id, record_type, patient_id, start_date, end_date, diagnosis_details, prescription, specialist, test_status, test_result, doctor) " +
                "VALUES (12345, 'test', 1001, '2019/01/02', '2019/01/05', 'all good', 'advil', 100, 'done', 'negative', 103)";
        Helper.insertAndHandle(TestUtil.getConnection(), query);

        query = "INSERT INTO MedicalRecord " +
                "(record_id, record_type, patient_id, start_date, end_date, diagnosis_details, prescription, specialist, test_status, test_result, doctor) " +
                "VALUES (12346, 'checkin', 1001, '2019/05/02', NULL, NULL, NULL, 100, NULL, NULL, 103)";
        Helper.insertAndHandle(TestUtil.getConnection(), query);
    }

    @Test
    public void getsPatientMedicalHistoryWithinDates() throws Exception {
        String inputString = "David\nAldridge\n1001\n2019/01/01\n2019/04/01\n";
        String expected = "\n" +
                "BEGIN RECORD 1\n" +
                "Start date: 2019-03-01\n" +
                "End date: null\n" +
                "Type: checkin\n" +
                "Diagnosis: null\n" +
                "Prescription: null\n" +
                "Doctor: 100\n" +
                "\n" +
                "BEGIN RECORD 2\n" +
                "Start date: 2019-03-01\n" +
                "End date: null\n" +
                "Type: treatment\n" +
                "Diagnosis: Hospitalization\n" +
                "Prescription: nervine\n" +
                "Doctor: 100\n" +
                "\n" +
                "BEGIN RECORD 12345\n" +
                "Start date: 2019-01-02\n" +
                "End date: 2019-01-05\n" +
                "Type: test\n" +
                "Diagnosis: all good\n" +
                "Prescription: advil\n" +
                "Doctor: 103\n" +
                "Test status: done\n" +
                "Test result: negative\n" +
                "Specialist: 100\n";
        TestUtil.assertMenuItemBehavior(inputString, expected, MedicalHistoryMenuItem.class, true);
    }

    @Test
    public void getsPatientMedicalHistoryWithinDatesWithSubsetRange() throws Exception {
        String inputString = "David\nAldridge\n1001\n2019/01/03\n2019/01/04\n";
        String expected = "\n" +
                "BEGIN RECORD 12345\n" +
                "Start date: 2019-01-02\n" +
                "End date: 2019-01-05\n" +
                "Type: test\n" +
                "Diagnosis: all good\n" +
                "Prescription: advil\n" +
                "Doctor: 103\n" +
                "Test status: done\n" +
                "Test result: negative\n" +
                "Specialist: 100\n";
        TestUtil.assertMenuItemBehavior(inputString, expected, MedicalHistoryMenuItem.class, true);
    }

    @Test
    public void getsPatientMedicalHistoryWithinDatesWithBeginningOverlappingRange() throws Exception {
        String inputString = "David\nAldridge\n1001\n2019/01/01\n2019/01/03\n";
        String expected = "\n" +
                "BEGIN RECORD 12345\n" +
                "Start date: 2019-01-02\n" +
                "End date: 2019-01-05\n" +
                "Type: test\n" +
                "Diagnosis: all good\n" +
                "Prescription: advil\n" +
                "Doctor: 103\n" +
                "Test status: done\n" +
                "Test result: negative\n" +
                "Specialist: 100\n";
        TestUtil.assertMenuItemBehavior(inputString, expected, MedicalHistoryMenuItem.class, true);
    }

    @Test
    public void getsPatientMedicalHistoryWithinDatesWithEndingOverlappingRange() throws Exception {
        String inputString = "David\nAldridge\n1001\n2019/01/04\n2019/01/06\n";
        String expected = "\n" +
                "BEGIN RECORD 12345\n" +
                "Start date: 2019-01-02\n" +
                "End date: 2019-01-05\n" +
                "Type: test\n" +
                "Diagnosis: all good\n" +
                "Prescription: advil\n" +
                "Doctor: 103\n" +
                "Test status: done\n" +
                "Test result: negative\n" +
                "Specialist: 100\n";
        TestUtil.assertMenuItemBehavior(inputString, expected, MedicalHistoryMenuItem.class, true);
    }

    @Test
    public void getsPatientMedicalHistoryWithinPrecedingDatesWithNULLEndDate() throws Exception {
        String inputString = "David\nAldridge\n1001\n2019/05/02\n2019/05/03\n";
        String expected = "\n" +
                "BEGIN RECORD 12346\n" +
                "Start date: 2019-05-02\n" +
                "End date: null\n" +
                "Type: checkin\n" +
                "Diagnosis: null\n" +
                "Prescription: null\n" +
                "Doctor: 103\n";
        TestUtil.assertMenuItemBehavior(inputString, expected, MedicalHistoryMenuItem.class, true);
    }

    @Test
    public void getsPatientMedicalHistoryWithinSucceedingDatesWithNULLEndDate() throws Exception {
        String inputString = "David\nAldridge\n1001\n2019/05/03\n2019/05/04\n";
        String expected = "\n" +
                "BEGIN RECORD 12346\n" +
                "Start date: 2019-05-02\n" +
                "End date: null\n" +
                "Type: checkin\n" +
                "Diagnosis: null\n" +
                "Prescription: null\n" +
                "Doctor: 103\n";
        TestUtil.assertMenuItemBehavior(inputString, expected, MedicalHistoryMenuItem.class, true);
    }

    @Test
    public void whenThereIsNoMedicalHistory() throws Exception {
        String inputString = "David\nAldridge\n1001\n2018/12/01\n2018/12/25\n";
        String expected = "No medical records associated with this patient and date range\n";
        TestUtil.assertMenuItemBehavior(inputString, expected, MedicalHistoryMenuItem.class, true);
    }

    @Test
    public void whenThePatientIdIsInvalid() throws Exception {
        String inputString = "David\nAldridge\ninvalid patient id\n";
        String expected = "Invalid patient ID: invalid patient id\n";
        TestUtil.assertMenuItemBehavior(inputString, expected, MedicalHistoryMenuItem.class, true);
    }

    @Test
    public void whenTheDateIsInvalidItGivesTheUserAnotherChance() throws Exception {
        String inputString = "David\nAldridge\n1001\n2019/01/01\n2019/02/0\n201/02/01\n2019/02/01\n";
        String expected = "\n" +
                "BEGIN RECORD 12345\n" +
                "Start date: 2019-01-02\n" +
                "End date: 2019-01-05\n" +
                "Type: test\n" +
                "Diagnosis: all good\n" +
                "Prescription: advil\n" +
                "Doctor: 103\n" +
                "Test status: done\n" +
                "Test result: negative\n" +
                "Specialist: 100\n";
        TestUtil.assertMenuItemBehavior(inputString, expected, MedicalHistoryMenuItem.class, true);
    }
}