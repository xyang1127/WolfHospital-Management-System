package csc540proj.reporting;

import csc540proj.utility.CommandLine;
import csc540proj.utility.Helper;
import csc540proj.utility.menu.AbstractMenuItem;

import java.sql.Connection;

/**
 * This class provides a menu item that prints out the medical history
 * of a patient from within a date range.
 */
public class MedicalHistoryMenuItem extends AbstractMenuItem {

    /**
     * This constructor initializes this menu item with a title.
     */
    public MedicalHistoryMenuItem() {
        super("Medical History");
    }

    /**
     * This method runs the functionality associated with this menu item.
     */
    @Override
    public void run(final CommandLine commandLine, final Connection connection) {
        // Get the patient ID provided by the user.
        int patientId = Helper.getPatient(connection, commandLine, commandLine::getInput);
        if (patientId == -1) {
            return;
        }

        // Get the date range provided by the user.
        String fromDate = Helper.getDate(commandLine, "begin date");
        String toDate = Helper.getDate(commandLine, "end date");

        // Submit a query to get all medical records associated with a patient
        // from the provided start date to provided end date.
        String query = String.format(
                "SELECT * " +
                        "FROM MedicalRecord " +
                        "WHERE patient_id=%d " +
                        "AND ((start_date BETWEEN '%s' AND '%s') " +
                        "  OR ('%s' BETWEEN start_date AND end_date) " +
                        "  OR ('%s' >= start_date AND end_date IS NULL))",
                patientId, fromDate, toDate, fromDate, fromDate);
        Helper.queryAndHandle(connection, query, resultSet -> {
            // Print out each returned medical record.
            boolean printed = false;
            while (resultSet.next()) {
                // Get the medical record details.
                int recordId = resultSet.getInt("record_id");
                String startDate = resultSet.getString("start_date");
                String endDate = resultSet.getString("end_date");
                String recordType = resultSet.getString("record_type");
                String diagnosisDetails = resultSet.getString("diagnosis_details");
                String prescription = resultSet.getString("prescription");
                String doctor = resultSet.getString("doctor");

                // Print out the details on the command line.
                // If the record is of type test, then print specific details about the test.
                commandLine.println("");
                commandLine.println("BEGIN RECORD " + recordId);
                commandLine.println("Start date: " + startDate);
                commandLine.println("End date: " + endDate);
                commandLine.println("Type: " + recordType);
                commandLine.println("Diagnosis: " + diagnosisDetails);
                commandLine.println("Prescription: " + prescription);
                commandLine.println("Doctor: " + doctor);
                if (recordType.equals("test")) {
                    String testStatus = resultSet.getString("test_status");
                    String testResult = resultSet.getString("test_result");
                    String specialist = resultSet.getString("specialist");
                    commandLine.println("Test status: " + testStatus);
                    commandLine.println("Test result: " + testResult);
                    commandLine.println("Specialist: " + specialist);
                }

                // Set a flag that says that there was at least one medical record returned.
                printed = true;
            }

            // If no medical records were returned, print a message.
            if (!printed) {
                commandLine.println("No medical records associated with this patient and date range");
            }

        });
    }
}
