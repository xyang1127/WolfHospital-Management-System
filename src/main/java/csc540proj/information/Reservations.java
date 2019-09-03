package csc540proj.information;

import csc540proj.utility.CommandLine;
import csc540proj.utility.Helper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class Reservations {

    private final CommandLine commandLine;
    private final Connection connection;

    public Reservations(final CommandLine commandLine, final Connection connection) {
        this.commandLine = commandLine;
        this.connection = connection;
    }

    private void printMenu() {
        commandLine.println("**************************************************");
        commandLine.println("Reservations Menu:");
        commandLine.println("");
        commandLine.println("1 - Check Availability");
        commandLine.println("2 - Reserve Bed For Patient");
        commandLine.println("3 - Assign Bed To Patient");
        commandLine.println("4 - Release Bed");
        commandLine.println("");
        commandLine.println("Enter - Return to previous menu");
        commandLine.println("");

        commandLine.print("Choice: ");
    }

    /**
     * run menu again after every function call
     * if no function is called, return to previous menu
     * @param choice
     * @throws Exception
     */
    private void action(String choice) throws Exception {
        switch (choice) {
            case "1":
                checkAvail();
                run();
                break;
            case "2":
                reserveBed();
                run();
                break;
            case "3":
                assignBed();
                run();
                break;
            case "4":
                releaseBed();
                run();
                break;

            default:
                commandLine.println("Returning to information processing menu.");
                InfoProcessing IP = new InfoProcessing(commandLine, connection);
                IP.run();
        }
    }

    public void run() throws Exception {
        printMenu();
        action(Helper.getChoice());
    }

    /**
     * checks the availability of ward my ward type (number of beds in ward)
     * outputs available beds in brackets []
     */
    private void checkAvail() {
        commandLine.print("Select number of beds in ward (1, 2, 4): ");
        int type = Integer.parseInt(Helper.getChoice());

        commandLine.println("Searching for " + type + "-bedroom wards...");

        //return ward numbers and associated availability for all wards with given ward type
        Helper.queryAndHandle(connection, "SELECT ward_number, availability FROM Ward WHERE ward_type = '" + type + "'", resultSet -> {
            while (resultSet.next())
                commandLine.println("[" + resultSet.getInt(1) + "] Ward Availability: " + resultSet.getInt(2));
        });

        //ask user to input ward number
        commandLine.println("Select ward to view beds: ");
        String wardNum = Helper.getChoice();
        Helper.queryAndHandle(connection, "SELECT bed_number FROM Bed WHERE ward_number = '" + wardNum + "' AND availability = TRUE", resultSet -> {
            while (resultSet.next())
                commandLine.println("[" + resultSet.getString(1) + "]");
        });

    }

    /**
     * overloaded function of reserveBed(int, String, int, int)
     * asks user for patient and start date, then passes as parameters to reserveBed()
     */
    private void reserveBed() {
        int patientID = Helper.getPatient(connection, commandLine);
        String startDate = Helper.getStartDate(commandLine);

        Helper.queryAndHandle(connection, "SELECT ward_number, bed_number FROM Bed WHERE availability = 1", resultSet -> {
            if (resultSet.next()) {
                commandLine.println("Reserving Bed: " + resultSet.getInt(2) + " in Ward: " + resultSet.getInt(1) + "...");
                reserveBed(patientID, startDate, resultSet.getInt(1), resultSet.getInt(2));
            } else commandLine.println("No beds available. Returning to previous menu.");
        });
    }

    /**
     * reserve bed creates a new entry into PatientAssignedBed table
     * this entry has status 'assigned'
     * the first available bed of any type will be reserved for given patient
     * ward availability and bed availability are automatically updated
     */
    public void reserveBed(int patientID, String startDate, int wardNum, int bedNum) {

        Helper.queryAndHandle(connection, "SELECT * FROM Ward JOIN Bed ON Ward.ward_number = Bed.ward_number " +
                "WHERE Ward.ward_number = " + wardNum + " AND Bed.bed_number = " + bedNum + " AND Bed.availability = TRUE", resultSet -> {

            if (resultSet.next()) {
                //insert new entry and update associated values
                Helper.insertAndHandle(connection, "INSERT INTO PatientAssignedBed(patient_id, bed_number, ward_number, start_date, end_date, status) " +
                        "VALUES (" + patientID + ", " + bedNum + ", " + wardNum + ", '" + startDate + "', NULL, 'Reserved')");
                Helper.insertAndHandle(connection, "UPDATE Ward SET availability = availability-1 WHERE ward_number = " + wardNum + "");
                Helper.insertAndHandle(connection, "UPDATE Bed Set availability = FALSE " +
                        "WHERE ward_number = " + wardNum + " AND bed_number = " + bedNum + "");

                commandLine.println("Patient " + patientID + " is reserved to bed " + bedNum + " in ward " + wardNum);
            } else {
                commandLine.println("Bed is not available. Returning to previous menu");
            }
        });

    }

    /**
     * This function assigns a patient to a bed.
     * The user is prompted for the bed and the patient.
     */
    public void assignBed() {
        // Ask the user the ward and bed that they want to assign.
        int wardNum = Integer.parseInt(Helper.confirmInput(
                commandLine,
                "Ward Number: ", "^[0-9][0-9]{1,2}$|^\\d$"));
        int bedNum = Integer.parseInt(Helper.confirmInput(
                commandLine,
                "Bed Number: ", "^[0-9][0-9]{1,2}$|^\\d$"));

        // Declare statement and resultSet at this scope so they can be closed below.
        Statement statement = null;
        ResultSet resultSet = null;
        String query = null;
        try {
            // Set auto commit false. To begin transaction.
            connection.setAutoCommit(false);

            // Create the statement.
            statement = connection.createStatement();

            // Query to see if the requested bed is not taken already.
            query = "SELECT * FROM Ward JOIN Bed ON Ward.ward_number = Bed.ward_number " +
                    "WHERE Ward.ward_number = " + wardNum +
                    " AND Bed.bed_number = " + bedNum +
                    " AND Bed.availability = TRUE";
            resultSet = statement.executeQuery(query);

            // If the bed is not taken already, then assign a patient.
            // If it is taken, then print out a message to the user.
            if (resultSet.next()) {
                // Prompt the user for the patient to be assigned.
                int patientID = Helper.getPatient(connection, commandLine, commandLine::getInput);

                // Add an entry into PatientAssignedBed for this bed assignment.
                LocalDate today = java.time.LocalDate.now();
                query = "INSERT INTO PatientAssignedBed(" +
                        "patient_id, bed_number, ward_number, start_date, end_date, status)" +
                        " " +
                        "VALUES (" +
                        patientID + ", " +
                        bedNum + ", " +
                        wardNum + ", '" +
                        today +
                        "', NULL, 'Assigned')";
                statement.execute(query);

                // Decrement the Ward availability by one.
                query = "UPDATE Ward SET availability = availability-1 WHERE ward_number = " + wardNum + "";
                statement.execute(query);

                // Set the bed availability to false.
                query = "UPDATE Bed Set availability = FALSE " +
                        "WHERE ward_number = " + wardNum + " AND bed_number = " + bedNum + "";
                statement.execute(query);

                // Commit the new data.
                connection.commit();

                // Tell the user that the assignment was successful.
                commandLine.println("Patient " + patientID +
                        " is assigned to bed " + bedNum +
                        " in ward " + wardNum);
            } else {
                // Tell the user the assignment was not completed because the bed was already taken.
                commandLine.println("Bed is not available. Returning to previous menu");
            }
        } catch (final SQLException e) {
            // If we get a failure, then rollback the changes.
            try {
                connection.rollback();
            } catch (SQLException e0) {
                commandLine.println("Failed to rollback changes.");
            }
            commandLine.println("Rolling back changes.");
        } finally {
            // Close the resultSet, if it was assigned.
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                commandLine.println("Failed to close result set.");
            }

            // Close the statement, if it was assigned.
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                commandLine.println("Failed to close statement.");
            }

            // Reset auto commit back to true.
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                commandLine.println("Failed to set auto commit.");
            }
        }
    }

    /**
     * function overload of releaseBed(int, LocalDate, int, int)
     * asks user for patient Id and passes as parameter
     */
    private void releaseBed() {
        int patientID = Helper.getPatient(connection, commandLine);
        Helper.queryAndHandle(connection, "SELECT ward_number, bed_number FROM PatientAssignedBed WHERE patient_id = " + patientID + " AND end_date IS NULL", resultSet -> {
            while (resultSet.next()) {
                commandLine.println("Releasing Bed: " + resultSet.getInt(2) + " in Ward: " + resultSet.getInt(1) + "...");
                releaseBed(patientID, LocalDate.now(), resultSet.getInt(1), resultSet.getInt(2));
            }
            commandLine.println("No more beds are reserved or assigned to patient: " + patientID);
        });
    }

    /**
     * release bed adds an end date to any and all bed assignments in PatientAssignedBed table for a given patient_id
     * this should be done before a new bed assignment is made
     */
    public void releaseBed(int patientID, LocalDate endDate, int wardNum, int bedNum) {

        Helper.queryAndHandle(connection, "SELECT * FROM Ward JOIN Bed ON Ward.ward_number = Bed.ward_number " +
                "WHERE Ward.ward_number = " + wardNum + " AND Bed.bed_number = " + bedNum + " AND Bed.availability = FALSE", resultSet -> {
            if (resultSet.next()) {
                Helper.insertAndHandle(connection, "UPDATE PatientAssignedBed SET end_date = '" + endDate + "' WHERE patient_id = " + patientID + " " +
                        "AND ward_number = " + wardNum + " AND bed_number = " + bedNum + "");
                Helper.insertAndHandle(connection, "UPDATE Ward SET availability = availability+1 WHERE ward_number = " + wardNum + "");
                Helper.insertAndHandle(connection, "UPDATE Bed Set availability = TRUE " +
                        "WHERE ward_number = " + wardNum + " AND bed_number = " + bedNum + "");
                commandLine.println("Patient " + patientID + " is released from bed " + bedNum + " in ward " + wardNum);
            } else {
                commandLine.println("Bed is already available. Returning to previous menu");
            }
        });
    }

}
