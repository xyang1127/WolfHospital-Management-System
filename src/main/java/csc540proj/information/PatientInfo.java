package csc540proj.information;

import csc540proj.utility.CommandLine;
import csc540proj.utility.Helper;

import java.sql.*;
import java.time.LocalDate;

/**
 * This class is a menu that provides access to the individual
 * patient information operations.
 */
public class PatientInfo {

    private final CommandLine commandLine;
    private final Connection connection;

    public PatientInfo(final CommandLine commandLine, final Connection connection) {
        this.commandLine = commandLine;
        this.connection = connection;
    }

    /**
     * This method runs prints the menu for the patient information section
     */
    private void printMenu() {
        commandLine.println("**************************************************");
        commandLine.println("Patient Information Menu:");
        commandLine.println("");
        commandLine.println("1 - Enter New Patient");
        commandLine.println("2 - Update Existing Patient");
        commandLine.println("3 - Remove Patient");
        commandLine.println("4 - Print Patients");
        commandLine.println("");
        commandLine.println("Enter - Return to previous menu");
        commandLine.println("");

        commandLine.print("Choice: ");
    }

    /**
     * This method gets the choice of the user and calls the corresponding methods.
     */
    private void action(String choice) throws Exception {
        switch (choice) {
            case "1":
                enterPatient();
                run();
                break;
            case "2":
                updatePatient();
                run();
                break;
            case "3":
                deletePatient();
                run();
                break;
            case "4":
                printAllPatientsInformation();
                run();
                break;

            default:
                commandLine.println("Returning to information processing menu.");
                InfoProcessing IP = new InfoProcessing(commandLine, connection);
                IP.run();
        }
    }

    /**
     * This method runs the functionality associated with the patient information system
     */
    public void run() throws Exception {
        printMenu();
        action(Helper.getChoice());
    }

    /**
     * This method allows users to add patients as specified by standard input.
     */
    private void enterPatient() {
        String firstName, lastName, ssn, gender, dob, phoneNumber, streetAddr, city, state, zip;
        String PatientID;
        //  receive information about patient to enter.
        PatientID = Helper.confirmInput(commandLine, "Enter Patient ID (leave blank to auto assign): ", "^(?:[0-9]{4})?$");
        firstName = Helper.confirmInput(commandLine, "Enter First Name: ", "^(?!\\s*$).+");
        lastName = Helper.confirmInput(commandLine, "Enter Last Name: ", "^(?!\\s*$).+");
        ssn = Helper.confirmInput(commandLine, "Enter SSN (leave blank if not available): ", "^(?:[0-9]{3}-[0-9]{2}-[0-9]{4})?$");
        gender = Helper.confirmInput(commandLine, "Enter gender ('M' for male. 'F' for female): ", "[M|F]");
        dob = Helper.getDate(commandLine, "DOB");
        phoneNumber = Helper.confirmInput(commandLine, "Enter Phone Number (leave blank if not available): ", "^(?:(?!\\s*$).+)?$");
        streetAddr = Helper.confirmInput(commandLine, "Enter Street Address (leave blank if not available): ", "^(?:(?!\\s*$).+)?$");
        city = Helper.confirmInput(commandLine, "Enter City (leave blank if not available): ", "^(?:(?!\\s*$).+)?$");
        state = Helper.confirmInput(commandLine, "Enter State (leave blank if not available): ", "^(?:[A-Z]{2})?$");
        zip = Helper.confirmInput(commandLine, "Enter Zip Code (leave blank if not available): ", "^(?:[0-9]{5})?$");

        commandLine.println("Entering Patient...");

        String sql;
        if (PatientID.equals("")) {
            sql = "INSERT INTO Patient (first_name, last_name, ssn, gender, DOB, phone_number, street_address, city, state, zip_code, status)" +
                    "VALUES ('" + firstName + "', '" + lastName + "', '" + ssn + "', '" + gender + "', '" + dob + "', '" + phoneNumber + "', '" + streetAddr + "', '" + city + "', '" + state + "', '" + zip + "', 'NEW' )";
        } else {
            sql = "INSERT INTO Patient (patient_id, first_name, last_name, ssn, gender, DOB, phone_number, street_address, city, state, zip_code, status)" +
                    "VALUES (" + Integer.parseInt(PatientID) + ", '" + firstName + "', '" + lastName + "', '" + ssn + "', '" + gender + "', '" + dob + "', '" + phoneNumber + "', '" + streetAddr + "', '" + city + "', '" + state + "', '" + zip + "', 'NEW' )";
        }
        //  passes connection and sql statement to general insert function that handles the connection.
        Helper.insertAndHandle(connection, sql);
        commandLine.println("Done.");
    }

    /**
     * This method removes a specified patient.
     */
    private void deletePatient() {
        int PatientID = Helper.getPatient(connection, commandLine);
        Reservations Res = new Reservations(commandLine, connection);
        Helper.queryAndHandle(connection, "SELECT ward_number, bed_number FROM PatientAssignedBed WHERE patient_id = " + PatientID + " AND end_date IS NULL", resultSet -> {
            while (resultSet.next()) {
                commandLine.println("Releasing Bed: " + resultSet.getInt(2) + " in Ward: " + resultSet.getInt(1) + "...");
                Res.releaseBed(PatientID, LocalDate.now(), resultSet.getInt(1), resultSet.getInt(2));
            }
            commandLine.println("No more beds are reserved or assigned to patient: " + PatientID);
        });
        String query = "SELECT * FROM Patient WHERE patient_id = " + PatientID + "";
        //   connection and sql query are passed to general query function that handles the connection.
        //   result set is handled in function override
        Helper.queryAndHandle(connection, query, resultSet -> {
            if (resultSet.next()) {
                String sql = "DELETE FROM Patient WHERE patient_id = " + PatientID + "";
                //   delete statement is sent to general function to handle connection.
                Helper.insertAndHandle(connection, sql);
                commandLine.println("Patient Deleted.");
            } else
                commandLine.println("ID does not match a current patient.");
        });
    }

    /**
     * This method allows users to update an attribute of an existing patient
     */
    private void updatePatient() {
        int patientID = Helper.getPatient(connection, commandLine);

        String query = "SELECT * FROM Patient WHERE patient_id = " + patientID + "";
        //  query is handled by general function in Helper class
        //  function is overridden to handle resultSet\
        String repeat = "y";
        while (repeat.equals("y")) {
            Helper.queryAndHandle(connection, query, resultSet -> {
                if (resultSet.next()) {
                    ResultSetMetaData rsmd = resultSet.getMetaData();
                    int cNum = rsmd.getColumnCount();
                    int i;
                    //  allows users to repeat update with same patient id without going through menu again
                    //  print all attributes
                    for (i = 1; i <= cNum; i++)
                        commandLine.println("[" + i + "] " + rsmd.getColumnName(i) + ": " + resultSet.getString(i));

                    int column = Integer.parseInt(Helper.confirmInput(commandLine, "Select the column number you wish to edit (0 to cancel): ", "^[0-9][0-9]{1,2}$|^\\d$"));

                    if (column == 1) {
                        commandLine.println("You can not edit Patient ID");
                        return;
                    } else if (column == 0) {
                        commandLine.println("Canceled update.(Enter to return to previous menu");
                        return;
                    }
                    commandLine.println("Enter updated " + rsmd.getColumnName(column) + ": ");
                    String ud = Helper.getChoice();
                    String sql = "UPDATE Patient SET " + rsmd.getColumnName(column) + " = '" + ud + "' WHERE patient_id = '" + patientID + "'";
                    //  handle connection in general insert function in Helper class
                    Helper.insertAndHandle(connection, sql);

                    commandLine.println("Patient Updated.");

                    commandLine.println("Continue updating patient? (y/n): ");
                } else
                    commandLine.println("Id does not match a current patient. Returning to main menu.");
            });
            repeat = Helper.getChoice();
        }
    }


    /**
     * This method prints patient information to the terminal.
     *
     * @throws Exception
     */
    public void printAllPatientsInformation() throws Exception {
        // Variable for Connection object
        Connection connection = null;
        // Variable for Statement object
        Statement statement = null;
        // Variable for ResultSet object
        ResultSet rs = null;

        // try/catch/finally for making sql query
        try {
            // Get connection
            connection = getDBConnection();
            // Query logic
            String sqlQuery = "SELECT patient_id, ssn, first_name, last_name, DOB, gender, TIMESTAMPDIFF(YEAR, DOB, CURDATE()) AS age, phone_number, street_address, " +
                    "city, state, zip_code, status FROM Patient";
            // Create statement for processing query
            statement = connection.createStatement();
            // Execute query and place in ResultSet
            rs = statement.executeQuery(sqlQuery);

            // Initialize variable for patientID
            int patientID = 0;
            // Initialize variable for currentPatientID
            int currentPatientID = 0;
            // While look for getting query results
            while (rs.next()) {
                // Get patientID
                patientID = rs.getInt("Patient.patient_id");
                // Check if the patientID is the first tuple with a particular
                // patientID.  If so, go to next tuple.  Query orders results
                // by patientID, then in_ward.  The top tuple for a particular
                // patientID determines the attributes for the patientID report.
                if (currentPatientID == patientID) {
                    continue;
                }
                // Set currentPatientID to patientID.  This statement is
                // executed only when the patientID is currently not equal
                // to the currentPatient ID
                currentPatientID = patientID;

                // Get remaining results from ResultSet
                String ssn = rs.getString("ssn");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String DOB = rs.getString("DOB");
                String gender = rs.getString("gender");
                int age = rs.getInt("age");
                String phoneNumber = rs.getString("phone_number");
                String streetAddress = rs.getString("street_address");
                String city = rs.getString("city");
                String state = rs.getString("state");
                String zipCode = rs.getString("zip_code");
                String status = rs.getString("Patient.status");
                //String inWard = rs.getString("in_ward");
                //String completingTreatment = rs.getString("completing_treatment");
//                String bedReleaseDate = rs.getString("release_date");
//                String checkOutDate = rs.getString("checkout_date");

                // Print results to terminal
                System.out.println("***********************************");
                System.out.println("[WHMS] Patient ID: " + patientID);
                System.out.println("[WHMS] SSN: " + ssn);
                System.out.println("[WHMS] First name: " + firstName);
                System.out.println("[WHMS] Last name: " + lastName);
                System.out.println("[WHMS] DOB: " + DOB);
                System.out.println("[WHMS] Gender: " + gender);
                System.out.println("[WHMS] Age: " + age);
                System.out.println("{WHMS] Phone number: " + phoneNumber);
                System.out.println("[WHMS] Street address: " + streetAddress);
                System.out.println("[WHMS] City: " + city);
                System.out.println("[WHMS] State: " + state);
                System.out.println("[WHMS] Zip code: " + zipCode);
                System.out.println("[WHMS] Status: " + status);
                //System.out.println("[WHMS] In ward: " + inWard);
                //System.out.println("[WHMS] Completing treatment: " + completingTreatment);

                Helper.queryAndHandle(connection, "SELECT * FROM PatientAssignedBed WHERE patient_id = "+patientID+" AND end_date IS NULL AND status = 'assigned'", resultSet -> {
                    String inWard;
                    if (resultSet.next()) {
                        inWard = "yes";
                    } else {
                        inWard = "no";
                    }
                    System.out.println("[WHMS] In ward: " + inWard);
                });
                Helper.queryAndHandle(connection, "SELECT * FROM MedicalRecord WHERE patient_id = "+patientID+" AND end_date IS NULL AND record_type = 'checkin'", resultSet -> {
                    String completed;
                    if (resultSet.next()) {
                        completed = "no";
                    } else {
                        completed = "yes";
                    }
                    System.out.println("[WHMS] Completing treatment: " + completed);
                });

            }
            // Catch SQL exception
        } catch (SQLException exception) {
            // Print to terminal error message
            System.out.println();
//            System.out.println(exception.getMessage());
            System.out.println("Invalid entry.  ");
            System.out.println("Returning to previous menu.");
            // Close ResultSet, Statement, and Connection no matter what
        } finally {

            // Close ResultSet
            rs.close();

            // Close statement if not null
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    throw new RuntimeException();
                }
            }
            // Close connection if not null
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException();
                }
            }
        }
    }

    /**
     * Method that establishes a connection with the database
     *
     * @return connection
     * @throws Exception
     */
    public Connection getDBConnection() throws Exception {
        // Establish connection
        Connection connection = DriverManager.getConnection("jdbc:mariadb://classdb2.csc.ncsu.edu/gjohnso", "gjohnso", "proj540");
        // Return connection to calling program
        return connection;
    }

}
