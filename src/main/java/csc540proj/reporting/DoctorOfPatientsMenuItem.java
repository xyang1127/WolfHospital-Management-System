package csc540proj.reporting;

import csc540proj.utility.CommandLine;
import csc540proj.utility.Helper;
import csc540proj.utility.menu.AbstractMenuItem;

import java.sql.Connection;
import java.util.HashSet;
import java.util.Set;

/**
 * This class provides a menu item that prints out the patients
 * that are currently assigned to a doctor.
 */
public class DoctorOfPatientsMenuItem extends AbstractMenuItem {

    /**
     * This constructor initializes this menu item with a title.
     */
    public DoctorOfPatientsMenuItem() {
        super("Doctor's Patients");
    }

    /**
     * This method runs the functionality associated with this menu item.
     */
    @Override
    public void run(CommandLine commandLine, Connection connection) {
        // Get the staff ID for the doctor in question.
        // If the staff ID is invalid, then return here.
        int staffId = Helper.getStaff(connection, commandLine, "Doctor", commandLine::getInput);
        if (staffId == -1) {
            return;
        }

        // Submit a query to find the patients for the assigned doctor.
        String query = String.format(
                "SELECT P.patient_id, P.first_name, P.last_name, P.gender, P.DOB, P.status, MR.doctor, " +
                        "TIMESTAMPDIFF(YEAR, DOB, CURDATE()) AS age " +
                        "FROM MedicalRecord AS MR NATURAL JOIN Patient AS P " +
                        "WHERE doctor=%d",
                staffId);
        Helper.queryAndHandle(connection, query, resultSet -> {
            // Remember which patients have been printed, so we can avoid
            // printing duplicates.
            final Set<Integer> patientsPrinted = new HashSet<>();

            // Keep track of if we printed anything.
            boolean printed = false;

            // Print out each returned patient, but only once per patient.
            while (resultSet.next()) {
                final int patientId = resultSet.getInt("patient_id");
                final String firstName = resultSet.getString("first_name");
                final String lastName = resultSet.getString("last_name");
                final String gender = resultSet.getString("gender");
                final String dob = resultSet.getString("DOB");
                final String age = resultSet.getString("age");
                final String status = resultSet.getString("status");
                if (!patientsPrinted.contains(patientId)) {
                    commandLine.println("");
                    commandLine.println("BEGIN PATIENT " + patientId);
                    commandLine.println("First name: " + firstName);
                    commandLine.println("Last name: " + lastName);
                    commandLine.println("Gender: " + gender);
                    commandLine.println("DOB: " + dob);
                    commandLine.println("Age: " + age);
                    commandLine.println("Status: " + status);
                    patientsPrinted.add(patientId);
                    // TODO: more information from patient table? all information?
                }

                printed = true;
            }

            // If we haven't printed anything, tell the user that this doctor has no patients.
            if (!printed) {
                commandLine.println("No patients for this doctor");
            }
        });
    }

}
