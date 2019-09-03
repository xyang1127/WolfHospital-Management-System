package csc540proj.information;

import csc540proj.Main;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The MedicalRecord class provides the processing for creating and updating
 * medical records.  Medical records fall into 3 categories: 1) records associated
 * with check-in and check-out, 2) records associated with tests, and 3) records
 * associated with treatment.
 * <p>
 * The MedicalRecord class provides menus for collecting information and, once
 * gathered, interfaces with the database to update records appropriately.
 */
public class MedicalRecord {

    /**
     * The printMenu method prints to the screen the top level MedicalRecord
     * menu and requests the choice from the user.
     *
     * @throws Exception
     */
    public void printMenu() throws Exception {
        System.out.println("**************************************************");
        System.out.println("[WHMS] Medical Record Menu:");
        System.out.println("[WHMS]");
        System.out.println("[WHMS] 1 - Enter New Check-In Medical Record");
        System.out.println("[WHMS] 2 - Enter New Test Medical Record");
        System.out.println("[WHMS] 3 - Enter New Treatment Medical Record");
        System.out.println("[WHMS] 4 - Update Check-In Medical Record");
        System.out.println("[WHMS] 5 - Update Test Medical Record");
        System.out.println("[WHMS] 6 - Update Treatment Medical Record");
        System.out.println("[WHMS] 7 - Delete Medical Record");
        System.out.println("[WHMS] 8 - List Patient Medical Records");
        System.out.println("[WHMS] 9 - Close Open Medical Records");
        System.out.println("[WHMS]");
        System.out.println("[WHMS] Enter - Return to main menu");
        System.out.println("[WHMS]");

        System.out.print("[WHMS] Choice: ");  // Enter choice here

        // Get user choice and call action to process
        action(getChoice());
    }

    /**
     * The getChoice method reads the menu choice from the user from the
     * console.
     */
    private static String getChoice() {
        // Create scanner object
        Scanner scanner = new Scanner(System.in);
        // Read line and return result
        return scanner.nextLine();
    }

    /**
     * The action method takes as input the menu choice from the user
     * and directs the program to the appropriate place using a switch
     * statement.  If the user enters a choice that is not in the menu,
     * the program will return to the main menu.
     *
     * @param choice from user
     * @throws Exception
     */
    public void action(String choice) throws Exception {
        switch (choice) {
            case "1":  // Enter check-in medical record
                enterCheckInMedicalRecord(); // call method to enter medical record
                break;
            case "2":  // Enter test medical record
                enterTestMedicalRecord();
                break;
            case "3": // Enter treatment medical record
                enterTreatmentMedicalRecord();
                break;
            case "4":  // Update check-in medical record
                updateCheckInMedicalRecord();
                break;
            case "5":  // Update test medical record
                updateTestMedicalRecord();
                break;
            case "6": // Update treatment medical record
                updateTreatmentMedicalRecord();
                break;
            case "7": // Delete medical record
                deleteMedicalRecord();
                break;
            case "8": // List a patient's medical records
                listMedicalRecords();
                break;
            case "9": // Close open medical records
                closeOpenMedicalRecords();
                break;

            default:  // Default - if user does not enter one of the
                // menu choices - return to top level menu
                System.out.println("Returning to main menu.");
                Main.printMenu();
                String main_choice = getChoice();
                Main.action(main_choice);
        }
    }

    /**
     * overloaded version of enterCheckinMedicalRecord(int, String)
     *
     * @throws Exception
     */
    private void enterCheckInMedicalRecord() throws Exception {
        // Call enterCheckInMedicalRecord method with parameters
        // entered by user
        enterCheckInMedicalRecord(findPatientID(), getStartDate());
        // Return to medical record menu
        printMenu();
    }

    /**
     * The enterCheckInMedicalRecord is the method used to
     * enter a medical record when a patient checks in.  A
     * check-in medical record is one that has only a start
     * date, an end date, and a doctor (all other attributes
     * are NULL).  The enterCheckInMedicalRecord method only asks
     * the user for the start date (mandatory) and the doctor
     * (optional).
     * <p>
     * Basic structure -
     * - findPatientID method is called to get the patient ID
     * - listMedicalRecords method is called to provide a list
     * of the patient's current medical records so the user
     * can determine the record id of the next to select
     * - the getValidMedicalRecordID method is called to get
     * the record id
     * - the getStartDate method is called to get the start date
     * - the user is asked if they want to enter the doctor
     * - if so, the findDoctorID method is called to get the
     * assigned doctor id
     * - a connection is made to the database and the medical record
     * is created with the entered information
     * - if successful, control is returned to the WHMS medical records
     * menu
     * - if an error is made that causes a SQL error, an error
     * message is generated and the user is returned to the
     * MedicalRecord menu
     *
     * @param patientID
     * @param startDate
     * @throws Exception
     */
    public void enterCheckInMedicalRecord(int patientID, String startDate) throws Exception {

        // List the medical records for the patient
        listMedicalRecords(patientID);

        // Get medical record ID
        int recordID = getValidMedicalRecordID("add", patientID);

        // Determine if user wants to enter doctor
        System.out.println("[WHMS] Enter doctor (Y or N)? ");
        // Get yes or no response
        String response = getYNResponse();
        // Initialize variable for doctorID
        int doctorID = 0;
        // If user wants to enter doctor, process
        if (response.equals("Y")) {
            // Call findDoctorID method to get doctor ID
            doctorID = findDoctorID();
        }

        // Connect to database and execute SQL statement to insert medical record
        // Create connection object that can be used throughout try/catch/finally
        Connection connection = null;
        // Create statement object that can be used throughout try/catch/finally
        Statement statement = null;
        try {
            // Get a connection to the database
            connection = getDBConnection();
            // Create a statement object to send the SQL statement to the database
            statement = connection.createStatement();
            // Execute the insert
            // Insert for doctorID = 0
            if (doctorID == 0) {
                statement.executeUpdate("INSERT INTO MedicalRecord " +
                        "(record_id, record_type, patient_id," +
                        "start_date, end_date, " + "diagnosis_details," +
                        "prescription, specialist, test_status," +
                        "test_result, doctor)" +
                        "VALUES(" +
                        " " + recordID + ", 'checkin', " + patientID + ", " +
                        " '" + startDate + "' , NULL, NULL," +
                        " NULL, NULL, NULL, " +
                        " NULL, NULL)");
                // Insert for doctorID != 0
            } else {
                statement.executeUpdate("INSERT INTO MedicalRecord " +
                        "(record_id, record_type, patient_id, " +
                        "start_date, end_date, diagnosis_details," +
                        "prescription, specialist, test_status," +
                        "test_result, doctor)" +
                        "VALUES(" +
                        " " + recordID + ", 'checkin', " + patientID + "," +
                        " '" + startDate + "' , NULL, NULL, " +
                        " NULL, NULL, NULL, " +
                        " NULL, " + doctorID + ")");
            }

            // Catch any SQL exception (entry of invalid information)
        } catch (SQLException exception) {
            // Call recovery message method
            recoverFromSqlException();
            // Close statement and connection no matter what
        } finally {
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
     * The enterTestMedicalRecord is the method used to
     * enter a medical record for a test.  A
     * test medical record is one that has the following fields
     * - record_id
     * - patient_id
     * - start_date
     * - end_date
     * - specialist (doctor performing the test)
     * - test_status
     * - test_result
     * - doctor
     * - diagnosis_details = NULL
     * - prescription = NULL
     * The enterTestMedicalRecord method asks the
     * user for the information from above to enter the record.
     * <p>
     * Basic structure -
     * - findPatientID method is called to get the patient ID
     * - listMedicalRecords method is called to provide a list
     * of the patient's current medical records so the user
     * can determine the record id of the next to select
     * - the user is requested to enter the required and optional
     * information
     * - findDoctorID method is called to get the assigned doctor id
     * and specialist
     * - a connection is made to the database and the medical record
     * is created with the information entered
     * - if successful, control is returned to the main WHMS menu
     * - if an error is made that causes a SQL error, an error
     * message is generated and the user is returned to the
     * MedicalRecord menu
     *
     * @throws Exception
     */
    public void enterTestMedicalRecord() throws Exception {
        // Call findPatientID method to get patient id
        int patientID = findPatientID();

        // List the medical records for the patient
        listMedicalRecords(patientID);

        // Get medical record ID
        int recordID = getValidMedicalRecordID("add", patientID);

        // Get start date
        String startDate = getStartDate();

        // Determine if user wants to enter test_status
        System.out.println("[WHMS] Enter test status (Y or N)? ");
        // Get yes or no response
        String response = getYNResponse();
        // Initialize test status
        String testStatus = null;
        // Get test status if user wants to enter test status
        if (response.equals("Y")) {
            System.out.println("[WHMS] Enter test status: ");
            testStatus = getChoice();
        }

        // Determine if user wants to enter specialist
        System.out.println("[WHMS] Enter specialist (Y or N)? ");
        // Get yes or no response
        response = getYNResponse();
        // Initialize variable for specialist
        int specialist = 0;
        // Get specialist if user wants to enter test status
        if (response.equals("Y")) {
            specialist = findDoctorID();
        }

        // Determine if user wants to enter doctor
        System.out.println("[WHMS] Enter doctor (Y or N)? ");
        // Get yes or no response
        response = getYNResponse();
        // Initialize variable for doctorID
        int doctorID = 0;
        // Get doctor ID if user wants to enter doctor
        if (response.equals("Y")) {
            // Call findDoctorID method to get doctor ID
            doctorID = findDoctorID();
        }

        // Connect to database and execute SQL statement to insert medical record
        // Create connection object that can be used throughout try/catch/finally
        Connection connection = null;
        // Create statement object that can be used throughout try/catch/finally
        Statement statement = null;
        try {
            // Get a connection to the database
            connection = getDBConnection();
            // Create a statement object to send the SQL statement to the database
            statement = connection.createStatement();

            // Execute the insert
            // Insert for doctorID = 0 and specialist = 0
            if (doctorID == 0 && specialist == 0) {
                statement.executeUpdate("INSERT INTO MedicalRecord (record_id, record_type, patient_id, start_date, end_date, diagnosis_details, prescription, specialist, test_status, test_result, doctor)" +
                        "VALUES('" + recordID + "', 'test', '" + patientID + "', '" + startDate + "' , NULL, NULL, NULL, NULL, '" + testStatus + "', NULL, NULL )");
//                statement.executeUpdate("INSERT INTO MedicalRecord " +
//                        "(record_id, record_type, patient_id, " +
//                        "start_date, end_date, diagnosis_details, " + " " +
//                        "prescription, specialist, test_status, " +
//                        "test_result, doctor)" +
//                        "VALUES(" +
//                        " ' " + recordID + " ', 'test', ' " + patientID + " '," +
//                        " '" + startDate + "' , NULL, NULL," +
//                        "NULL, NULL, '" + testStatus + "'," +
//                        " NULL, NULL ')");
                // Insert for doctorID = 0 and specialist != 0
            } else if (doctorID == 0 && specialist != 0) {
                statement.executeUpdate("INSERT INTO MedicalRecord (record_id, record_type, patient_id, start_date, end_date, diagnosis_details, prescription, specialist, test_status, test_result, doctor)" +
                        "VALUES( " + recordID + " , 'test', " + patientID + " , '" + startDate + "' , NULL, NULL, NULL, " + specialist + " , '" + testStatus + "', NULL, NULL )");
//                statement.executeUpdate("INSERT INTO MedicalRecord " +
//                        "(record_id, record_type, patient_id, " +
//                        "start_date, end_date, diagnosis_details, " + " " +
//                        "prescription, specialist, test_status, " +
//                        "test_result, doctor)" +
//                        "VALUES(" +
//                        "  " + recordID + " , 'test',  " + patientID + " ," +
//                        " '" + startDate + "' , NULL, NULL," +
//                        "NULL, '"+specialist+"', '" + testStatus + "'," +
//                        " NULL, NULL ')");
                // Insert for doctor != 0 and specialist = 0
            } else if (doctorID != 0 && specialist == 0) {
                statement.executeUpdate("INSERT INTO MedicalRecord (record_id, record_type, patient_id, start_date, end_date, diagnosis_details, prescription, specialist, test_status, test_result, doctor)" +
                        "VALUES('" + recordID + "', 'test', '" + patientID + "', '" + startDate + "' , NULL, NULL, NULL, NULL, '" + testStatus + "', NULL, '" + doctorID + "' )");
//                statement.executeUpdate("INSERT INTO MedicalRecord " +
//                        "(record_id, record_type, patient_id, " +
//                        "start_date, end_date, diagnosis_details, " + " " +
//                        "prescription, specialist, test_status, " +
//                        "test_result, doctor)" +
//                        "VALUES(" +
//                        " '" + recordID + "', 'test', '"+patientID+"'," +
//                        " '"+startDate+"' , NULL, NULL," +
//                        "NULL, NULL, '"+testStatus+"'," +
//                        " NULL, '"+doctorID+"' )");
                // Insert for doctor != 0, specialist != 0
            } else {
                statement.executeUpdate("INSERT INTO MedicalRecord (record_id, record_type, patient_id, start_date, end_date, diagnosis_details, prescription, specialist, test_status, test_result, doctor)" +
                        "VALUES( " + recordID + ", 'test',  " + patientID + " , '" + startDate + "' , NULL, NULL, NULL,  " + specialist + " , '" + testStatus + "', NULL,  " + doctorID + " )");
//                statement.executeUpdate("INSERT INTO MedicalRecord " +
//                        "(record_id, record_type, patient_id, " +
//                        "start_date, end_date, diagnosis_details, " + " " +
//                        "prescription, specialist, test_status, " +
//                        "test_result, doctor)" +
//                        "VALUES(" +
//                        " '"+recordID+"', 'test', '"+patientID+"'," +
//                        " '"+startDate+"' , NULL, NULL," +
//                        "NULL, '"+specialist+"', '"+testStatus+"'," +
//                        " NULL, '"+doctorID+"')");
            }

            // Catch any SQL exception (entry of invalid information)
        } catch (SQLException exception) {
            // Call recovery message method
            recoverFromSqlException();

            // Close statement and connection no matter what
        } finally {
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

        // Return to medical records menu
        printMenu();
    }


    /**
     * The getPatientID method gets the patient ID for a patient and returns
     * the value.  It firsts asks the user to enter the patient ID. The user can
     * enter the patient ID (integer) directly or, if the user does not
     * know the patient ID, the user can enter a '?' to search for the patient ID
     * by the patient's first and last name. If the user searches for the
     * patient ID by first and last name, patient information (last name, first name,
     * DOB, and patient ID) is returned for all patients with the entered first and
     * last name.  The user can then select the desired patient ID.
     * <p>
     * The findPatientID method makes use of the isValidPatientID method to
     * ensure the the patientID is a valid integer and that the patient ID
     * is in the database.
     *
     * @return patientID
     * @throws Exception
     */
    public int findPatientID() throws Exception {
        // Variable representing the patientID
        int patientID;
        // Variable for Connection object
        Connection connection = null;
        // Variable for PreparedStatement object
        PreparedStatement ps = null;

        // Request patientID from user and get response
        System.out.println("[WHMS] Enter patient ID or '?' to search for patient ID by patient name.");
        System.out.println("[WHMS] Hit enter to return to Medical Record menu.");
        System.out.print("[WHMS] Choice: ");
        String response = getChoice();

        // Handle case where user wants to return to the Medical Record menu
        if (response.equals("")) {
            // Return to medical record menu
            printMenu();
        }

        // Handle case where user wants to get the patient ID by entering the patient's
        // first and last name
        if (response.equals("?")) {

            // Get patient's last and first names
            System.out.print("[WHMS] Enter patient last name: ");
            String patient_last_name = getChoice();
            System.out.print("[WHMS] Enter patient first name: ");
            String patient_first_name = getChoice();

            // Query data base for patients with entered last and first names
            try {
                // Crete connection to database
                connection = getDBConnection();
                // Create prepared statement for SELECT query
                ps = connection.prepareStatement("SELECT last_name, first_name, DOB, patient_id FROM Patient WHERE last_name = ? AND first_name = ?");
                // Set parameters for prepared statement
                ps.setString(1, patient_last_name);
                ps.setString(2, patient_first_name);
                // Execure query and get result set
                ResultSet rs = ps.executeQuery();

                // Print results to terminal
                // Print table headers
                System.out.println("\nLast Name        First Name       DOB         PatientID");
                System.out.println("---------------  ---------------  ----------  ---------");
                // Step through and print results to terminal
                while (rs.next()) {
                    String lastName = rs.getString("last_name");
                    String firstName = rs.getString("first_name");
                    Date dateOfBirth = rs.getDate("DOB");
                    int patID = rs.getInt("patient_id");
                    System.out.printf("%-15s  %-15s  %-10s  %9d\n", lastName, firstName, dateOfBirth, patID);
                }
                //Skip a line for clarity
                System.out.println();
                // Close statement and connection
                ps.close();
                connection.close();
            } catch (SQLException exception) {
                // Print sql exception error message
                recoverFromSqlException();
                // Close statement and connection
                ps.close();
                connection.close();
            }
        }
        // Handle the case where the user wants to enter the patient ID directly

        // Loop for re-requesting valid patient ID when invalid patient ID
        // is entered.
        while (!(isValidPatientID(response))) {
            System.out.println("Enter valid patient ID or Enter to return to Medical Record menu.");
            System.out.print("Choice: ");
            response = getChoice();
            // Option for user to return to the Medical Record menu
            if (response.equals("")) {
                // Return to medical record menu
                printMenu();
            }
        }
        // Set patientID variable to the valid entered patientID
        patientID = Integer.parseInt(response);

        //return patientID;
        return patientID;
    }


    /**
     * The findDoctorID method gets the doctor ID for a doctor and returns
     * the value.  It firsts asks the user to enter the doctor ID. The user can
     * enter the doctor ID (integer) directly or, if the user does not
     * know the doctor ID, the user can enter a '?' to search for the doctor ID
     * by the doctor's first and last name. If the user searches for the
     * doctor ID by first and last name, doctor information (last name, first name,
     * specialty, and doctor ID) is returned for all doctors with the entered first and
     * last name.  The user can then select the desired doctor ID.
     * <p>
     * The findDoctorID method makes use of the isValidDoctorID method to
     * ensure the the doctor ID is a valid integer and that the doctor ID
     * is in the database.
     *
     * @return doctorID
     * @throws Exception
     */
    public int findDoctorID() throws Exception {
        // Variable representing the patientID
        int doctorID;
        // Variable for Connection object
        Connection connection = null;
        // Varriable for PreparedStatement object
        PreparedStatement ps = null;

        // Request patientID from user and get response
        System.out.println("[WHMS] Enter doctor ID or '?' to search for doctor ID by doctor name.");
        System.out.println("[WHMS] Hit enter to return to Medical Record menu.");
        // Get user response
        System.out.print("[WHMS] Choice: ");
        String response = getChoice();

        // Handle case where user wants to return to the Medical Record menu
        if (response.equals("")) {
            // return to medical record menu
            printMenu();
        }

        // Handle case where user wants to get the doctor ID by entering the doctor's
        // first and last name
        if (response.equals("?")) {

            // Get doctor's last and first names
            System.out.print("[WHMS] Enter doctor last name: ");
            String doctor_last_name = getChoice();
            System.out.print("[WHMS] Enter doctor first name: ");
            String doctor_first_name = getChoice();

            // Query data base for doctors with entered last and first names
            try {
                // Get connection
                connection = getDBConnection();
                // Create PreparedStatement
                ps = connection.prepareStatement("SELECT last_name, first_name, specialty, Doctor.staff_id FROM Staff, Doctor WHERE Staff.staff_id = Doctor.staff_id AND last_name = ? AND first_name = ?");
                // Set parameters for prepared statement
                ps.setString(1, doctor_last_name);
                ps.setString(2, doctor_first_name);
                // Execute query and get ResultSet
                ResultSet rs = ps.executeQuery();

                // Print results to terminal
                // Print header information
                System.out.println("\nLast Name        First Name       Specialty             Staff ID");
                System.out.println("---------------  ---------------  --------------------  ---------");
                // Step through and print results to terminal
                while (rs.next()) {
                    String lastName = rs.getString("last_name");
                    String firstName = rs.getString("first_name");
                    String specialty = rs.getString("specialty");
                    int staffID = rs.getInt("Doctor.staff_id");
                    System.out.printf("%-15s  %-15s  %-20s  %9d\n", lastName, firstName, specialty, staffID);
                }
                // Skip line for clarity
                System.out.println();
                // Close PreparedStatement and Connection
                ps.close();
                connection.close();
                // Handle sql exception
            } catch (SQLException exception) {
                // Print sql exception error message
                recoverFromSqlException();
                // Close statement and connection
                ps.close();
                connection.close();
            }
        }
        // Handle the case where the user wants to enter the doctor ID directly

        // Loop for re-requesting valid doctor ID when invalid patient ID
        // is entered.
        while (!(isValidDoctorID(response))) {
            System.out.println("Enter valid doctor ID or Enter to return to Medical Record menu.");
            System.out.print("Choice: ");
            // Get user response
            response = getChoice();
            // Option for user to return to the Medical Record menu
            if (response.equals("")) {
                printMenu();
            }
        }
        // Set doctorID variable to the valid entered doctorID
        doctorID = Integer.parseInt(response);

        //return doctorID;
        return doctorID;
    }


    /**
     * The enterTreatmentMedicalRecord is the method used to
     * enter a medical record for a treatment.  A
     * test medical record is one that has the following fields
     * - record_id
     * - patient_id
     * - start_date
     * - end_date
     * - specialist (doctor performing the test) = NULL
     * - test_status = NULL
     * - test_result = NULL
     * - doctor
     * - diagnosis_details
     * - prescription
     * The enterTreatmentMedicalRecord method asks the
     * user for the information from above to enter the record.
     * <p>
     * Basic structure -
     * - findPatientID method is called to get the patient ID
     * - listMedicalRecords method is called to provide a list
     * of the patient's current medical records so the user
     * can determine the record id of the next to select
     * - the user is requested to enter the required and optional
     * information
     * - findDoctorID method is called to get the assigned doctor id
     * and specialist
     * - a connection is made to the database and the medical record
     * is created with the information entered
     * - if successful, control is returned to the main WHMS menu
     * - if an error is made that causes a SQL error, an error
     * message is generated and the user is returned to the
     * MedicalRecord menu
     *
     * @throws Exception
     */
    public void enterTreatmentMedicalRecord() throws Exception {
        // Call findPatientID method to get patient id
        int patientID = findPatientID();

        // List the medical records for the patient
        listMedicalRecords(patientID);

        // Get medical record ID
        int recordID = getValidMedicalRecordID("add", patientID);

        // Get start date
        String startDate = getStartDate();

        // Determine if user wants to enter prescription
        System.out.println("[WHMS] Enter prescription (Y or N)? ");
        // Get yes or no response
        String response = getYNResponse();
        // Initialize prescription
        String prescription = null;
        // Get prescription if user wants to enter prescription
        if (response.equals("Y")) {
            System.out.println("[WHMS] Enter prescription: ");
            prescription = getChoice();
        }

        // Determine if user wants to enter diagnosis
        System.out.println("[WHMS] Enter diagnosis (Y or N)? ");
        // Get yes or no answer
        response = getYNResponse();
        // Initialize diagnosis
        String diagnosis = null;
        // Get diagnosis if user wants to enter diagnosis
        if (response.equals("Y")) {
            System.out.println("[WHMS] Enter diagnosis: ");
            diagnosis = getChoice();
        }

        // Determine if user wants to enter doctor
        System.out.println("[WHMS] Enter doctor (Y or N)? ");
        // Get yes or no answer
        response = getYNResponse();
        // Initialize doctor = 0
        int doctorID = 0;
        // Get doctor ID if user wants to enter doctor
        if (response.equals("Y")) {
            // Call findDoctorID method to get doctor ID
            doctorID = findDoctorID();
        }

        // Connect to database and execute SQL statement to insert medical record
        // Create connection object that can be used throughout try/catch/finally
        Connection connection = null;
        // Create statement object that can be used throughout try/catch/finally
        Statement statement = null;
        try {
            // Get a connection to the database
            connection = getDBConnection();
            // Set autocommit to off (false)
            connection.setAutoCommit(false);
            // Create a statement object to send the SQL statement to the database
            statement = connection.createStatement();

            // Execute the insert
            // Insert for doctorID = 0
            if (doctorID == 0) {
                statement.executeUpdate("INSERT INTO MedicalRecord (record_id, record_type, patient_id, start_date, end_date, diagnosis_details, prescription, specialist, test_status, test_result, doctor)" +
                        "VALUES(" + recordID + ", 'treatment', " + patientID + ", '" + startDate + " ' , NULL, '" + diagnosis + "', '" + prescription + "', NULL, NULL, NULL, NULL )");
//                statement.executeUpdate("INSERT INTO MedicalRecord " +
//                        "(record_id, record_type, patient_id," +
//                        "start_date, end_date, diagnosis_details," +
//                        "prescription, specialist, test_status," +
//                        "test_result, doctor)" +
//                        "VALUES(" +
//                        " "+recordID+", 'treatment', "+patientID+", " +
//                        " '"+startDate+"' , NULL, '"+diagnosis+"'," +
//                        " '"+prescription+"', NULL, NULL," +
//                        "NULL, NULL )");
                // Insert for doctorID != 0
            } else {
                statement.executeUpdate("INSERT INTO MedicalRecord (record_id, record_type, patient_id, start_date, end_date, diagnosis_details, prescription, specialist, test_status, test_result, doctor)" +
                        "VALUES(" + recordID + ", 'treatment', " + patientID + ", '" + startDate + " ' , NULL, '" + diagnosis + "','" + prescription + "', NULL, NULL, NULL, " + doctorID + " )");
//                statement.executeUpdate("INSERT INTO MedicalRecord " +
//                        "(record_id, record_type, patient_id," +
//                        "start_date, end_date, diagnosis_details," +
//                        "prescription, specialist, test_status," +
//                        "test_result, doctor)" +
//                        "VALUES(" +
//                        " "+recordID+", 'treatment', "+patientID+"," +
//                        " '"+startDate+"' , NULL, '"+diagnosis+"'," +
//                        " '"+prescription+"', NULL, NULL, " +
//                        "NULL, "+doctorID+" )");
            }
            // Commit the changes
            connection.commit();

            // Catch any SQL exception (entry of invalid information)
        } catch (SQLException exception) {
            // Call recovery message method
            recoverFromSqlException();
            // Close statement and connection no matter what
        } finally {
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
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException();
                }
            }
        }
        // Return to medical record menu
        printMenu();
    }

    /**
     * The updateCheckInMedicalRecord is the method used to
     * update an existing check-in medical record.  A
     * check-in medical record is one that has the following fields
     * - record_id
     * - patient_id
     * - start_date
     * - end_date
     * - specialist (doctor performing the test) = NULL
     * - test_status = NULL
     * - test_result = NULL
     * - doctor
     * - diagnosis_details = NULL
     * - prescription = NULL
     * The updateCheckInMedicalRecord method asks the
     * user for the end_date and doctor as all other fields should
     * remain the same.
     * <p>
     * Basic structure -
     * - findPatientID method is called to get the patient ID
     * - listMedicalRecords method is called to provide a list
     * of the patient's current medical records so the user
     * can determine the record id of the one to update
     * - the user is prompted to enter the record id of the
     * record to update
     * - the user is prompted to update the start_date
     * - the user is prompted to enter/update the end date
     * - the user is prompted to update the assigned doctor
     * -  findDoctorID method is called to get the assigned doctor id
     * if needed
     * - a connection is made to the database and the medical record
     * is updated with the information entered
     * - if successful, control is returned to the main WHMS menu
     * - if an error is made that causes a SQL error, an error
     * message is generated and the user is returned to the
     * MedicalRecord menu
     *
     * @throws Exception
     */
    public void updateCheckInMedicalRecord() throws Exception {
        // Variable for connection object
        Connection connection = null;
        // Variable for statement object
        Statement statement = null;

        // Call findPatientID method to get patient id
        int patientID = findPatientID();

        // List the medical records for the patient
        listMedicalRecords(patientID);

        // Get medical record ID
        int recordID = getValidMedicalRecordID("update", patientID);

        // Code for update of medical record using try/catch for
        // processing database updates.
        try {
            ///////// Connect to database and prepare for update ///////

            // Connect to data base
            connection = getDBConnection();
            // Set auto commit to off (false)
            connection.setAutoCommit(false);   ////  START TRANSACTION  ///
            // Create statement object for sending SQL statements to db
            statement = connection.createStatement();

            ////////////  Update start date processing  //////////////////

            // Prompt user if they want to update the start date.  If yes,
            // get the start date and update the database.
            System.out.println("[WHMS] Update start date (Y or N)? ");
            // Get yes/no response from user
            String response = getYNResponse();

            // Case for user selecting Y - update start date
            if (response.equals("Y")) {
                // Get start date
                String startDate = getStartDate();
                // Execute sql statement to update start_date
                statement.executeUpdate("UPDATE MedicalRecord SET start_date = '" + startDate + "' WHERE patient_id = '" + patientID + "' AND record_id = '" + recordID + "' ");

            }

            ////////////  Update end date processing  //////////////////

            // Prompt user if they want to enter/update the end date.  If yes,
            // get the end date and update the database.
            System.out.println("[WHMS] Update end date (Y or N)? ");
            // Get yes/no response from user
            response = getYNResponse();

            // Case for user selecting Y - update end date
            if (response.equals("Y")) {
                // Get end date
                String endDate = getEndDate(patientID, recordID);
                // Execute sql statement to update end_date
                statement.executeUpdate("UPDATE MedicalRecord SET end_date = '" + endDate + "' WHERE patient_id = '" + patientID + "' AND record_id = '" + recordID + "' ");
            }

            ////////////  Update doctor processing  //////////////////

            // Prompt user if they want to update the responsible doctor.  If yes,
            // get the new doctor id and update the database.
            System.out.println("[WHMS] Update doctor (Y or N)? ");
            // Get yes/no response from user
            response = getYNResponse();

            // Case for user selecting Y - update doctor
            if (response.equals("Y")) {
                // Get user input for doctor
                int doctorID = findDoctorID();
                // Execute sql statement to update doctor
                statement.executeUpdate("UPDATE MedicalRecord SET doctor = '" + doctorID + "' WHERE patient_id = '" + patientID + "' AND record_id = '" + recordID + "' ");
            }

            // Commit the changes
            connection.commit();


            // Handle sql exception
        } catch (SQLException exception) {
            recoverFromSqlException();
            // If connection is open, rollback changes
            if (connection != null) {
                try {
                    connection.rollback();
                    // Throw sql exception if error
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            // Reset autocommit, close statement and connection no matter what
        } finally {
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
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException();
                }
            }
        }
        // Return the user to the menu
        printMenu();
    }

    /**
     * The updateTestMedicalRecord is the method used to
     * update an existing test medical record.  A
     * test medical record is one that has the following fields
     * - record_id
     * - patient_id
     * - start_date
     * - end_date
     * - specialist (doctor performing the test)
     * - test_status
     * - test_result
     * - doctor
     * - diagnosis_details = NULL
     * - prescription = NULL
     * The updateTestMedicalRecord method asks the
     * user for updates to all relevant test medical record
     * fields.
     * <p>
     * Basic structure -
     * - findPatientID method is called to get the patient ID
     * - listMedicalRecords method is called to provide a list
     * of the patient's current medical records so the user
     * can determine the record id of the one to update
     * - the user is prompted to enter the record id of the
     * record to update
     * - the user is prompted to update the start_date
     * - the user is prompted to enter/update the end_date
     * - the user is prompted to enter/update the specialist
     * -  findDoctorID method is called to get the specialist id
     * if needed
     * - the user is prompted to enter/update the test_status
     * - the user is prompted to enter/update the test_result
     * - the user is prompted to update the assigned doctor
     * -  findDoctorID method is called to get the assigned doctor id
     * if needed
     * - a connection is made to the database and the medical record
     * is updated with the information entered
     * - if successful, control is returned to the main WHMS menu
     * - if an error is made that causes a SQL error, an error
     * message is generated and the user is returned to the
     * MedicalRecord menu
     *
     * @throws Exception
     */
    public void updateTestMedicalRecord() throws Exception {
        // Variable for connection object
        Connection connection = null;
        // Variable for statement object
        Statement statement = null;

        // Call findPatientID method to get patient id
        int patientID = findPatientID();

        // List the medical records for the patient
        listMedicalRecords(patientID);

        // Get medical record ID
        int recordID = getValidMedicalRecordID("update", patientID);

        // Code for update of medical record using try/catch for
        // processing database updates.
        try {
            ///////// Connect to database and prepare for update ///////

            // Connect to data base
            connection = getDBConnection();
            // Set auto commit to off (false)
            connection.setAutoCommit(false);   ///// START TRANSACTION ////
            // Create statement object for sending SQL statements to db
            statement = connection.createStatement();

            ////////////  Update start date processing  //////////////////

            // Prompt user if they want to update the start date.  If yes,
            // get the start date and update the database.
            System.out.println("[WHMS] Update start date (Y or N)? ");
            // Get yes/no response from user
            String response = getYNResponse();

            // Case for user selecting Y - update start date
            if (response.equals("Y")) {
                // Get start date
                String startDate = getStartDate();
                // Execute sql statement to update start_date
                statement.executeUpdate("UPDATE MedicalRecord SET start_date = '" + startDate + "' WHERE patient_id = '" + patientID + "' AND record_id = '" + recordID + "' ");
            }

            ////////////  Update end date processing  //////////////////

            // Prompt user if they want to enter/update the end date.  If yes,
            // get the end date and update the database.
            System.out.println("[WHMS] Update end date (Y or N)? ");
            // Get yes/no response from user
            response = getYNResponse();

            // Case for user selecting Y - update end date
            if (response.equals("Y")) {
                // Get end date
                String endDate = getEndDate(patientID, recordID);
                // Execute sql statement to update end_date
                statement.executeUpdate("UPDATE MedicalRecord SET end_date = '" + endDate + "' WHERE patient_id = '" + patientID + "' AND record_id = '" + recordID + "' ");
            }

            ////////////  Update specialist processing  //////////////////

            // Prompt user if they want to update the specialist.  If yes,
            // get the specialist doctor id and update the database.
            System.out.println("[WHMS] Update specialist (Y or N)? ");
            // Get yes/no response from user
            response = getYNResponse();

            // Case for user selecting Y - update specialist
            if (response.equals("Y")) {
                // Get user input for doctor
                int specialistID = findDoctorID();
                // Execute sql statement to update specialist
                statement.executeUpdate("UPDATE MedicalRecord SET specialist = '" + specialistID + "' WHERE patient_id = '" + patientID + "' AND record_id = '" + recordID + "' ");
            }

            ////////////  Update test status processing  //////////////////

            // Prompt user if they want to enter/update test status.  If yes,
            // get the new test status and update the database.
            System.out.println("[WHMS] Update test status (Y or N)? ");
            // Get yes/no response from user
            response = getYNResponse();

            // Case for user selecting Y - update test status
            if (response.equals("Y")) {
                // Get user input for test status
                System.out.println("[WHMS] Enter test status: ");
                response = getChoice();
                // Store user response as testStatus
                String testStatus = response;
                // Execute sql statement to update test status
                statement.executeUpdate("UPDATE MedicalRecord SET test_status = '" + testStatus + "' WHERE patient_id = '" + patientID + " ' AND record_id = '" + recordID + "' ");
            }

            ////////////  Update test result processing  //////////////////

            // Prompt user if they want to enter/update test result.  If yes,
            // get the new test result and update the database.
            System.out.println("[WHMS] Update test result (Y or N)? ");
            // Get yes/no response from user
            response = getYNResponse();

            // Case for user selecting Y - update test result
            if (response.equals("Y")) {
                // Get user input for test result
                System.out.println("[WHMS] Enter test result: ");
                response = getChoice();
                // Store user response as testResult
                String testResult = response;
                // Execute sql statement to update test result
                statement.executeUpdate("UPDATE MedicalRecord SET test_result = '" + testResult + "' WHERE patient_id = '" + patientID + "' AND record_id = '" + recordID + "' ");
            }

            ////////////  Update doctor processing  //////////////////

            // Prompt user if they want to update the responsible doctor.  If yes,
            // get the new doctor id and update the database.
            System.out.println("[WHMS] Update doctor (Y or N)? ");
            // Get yes/no response from user
            response = getYNResponse();

            // Case for user selecting Y - update doctor
            if (response.equals("Y")) {
                // Get user input for doctor
                int doctorID = findDoctorID();
                // Execute sql statement to update doctor
                statement.executeUpdate("UPDATE MedicalRecord SET doctor = '" + doctorID + "' WHERE patient_id = '" + patientID + "' AND record_id = '" + recordID + "' ");
            }

            // Commit the changes
            connection.commit();


            // Handle sql exception
        } catch (SQLException exception) {
            recoverFromSqlException();
            // If connection is open, rollback changes, set autocommit
            // to true, and close the connection
            if (connection != null) {
                try {
                    connection.rollback();
                    // Throw sql exception if error
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            // Reset autocommit, close statement and connection no matter what
        } finally {
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
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException();
                }
            }
        }
        // Return the user to the menu
        printMenu();
    }


    /**
     * The updateTreatmentMedicalRecord is the method used to
     * update an existing treatment medical record.  A
     * treatment medical record is one that has the following fields
     * - record_id
     * - patient_id
     * - start_date
     * - end_date
     * - specialist (doctor performing the test) = NULL
     * - test_status = NULL
     * - test_result = NULL
     * - doctor
     * - diagnosis_details
     * - prescription
     * The updateTreatmentMedicalRecord method asks the
     * user for updates to all relevant treatment medical record
     * fields.
     * <p>
     * Basic structure -
     * - findPatientID method is called to get the patient ID
     * - listMedicalRecords method is called to provide a list
     * of the patient's current medical records so the user
     * can determine the record id of the one to update
     * - the user is prompted to enter the record id of the
     * record to update
     * - the user is prompted to update the start_date
     * - the user is prompted to enter/update the end_date
     * - the user is prompted to enter/update the diagnosis_details
     * - the user is prompted to enter/update the prescription
     * - the user is prompted to update the assigned doctor
     * -  findDoctorID method is called to get the assigned doctor id
     * if needed
     * - a connection is made to the database and the medical record
     * is updated with the information entered
     * - if successful, control is returned to the main WHMS menu
     * - if an error is made that causes a SQL error, an error
     * message is generated and the user is returned to the
     * MedicalRecord menu
     *
     * @throws Exception
     */
    public void updateTreatmentMedicalRecord() throws Exception {
        // Variable for connection object
        Connection connection = null;
        // Variable for statement object
        Statement statement = null;

        // Call findPatientID method to get patient id
        int patientID = findPatientID();

        // List the medical records for the patient
        listMedicalRecords(patientID);

        // Get medical record ID
        int recordID = getValidMedicalRecordID("update", patientID);

        // Code for update of medical record using try/catch for
        // processing database updates.
        try {
            ///////// Connect to database and prepare for update ///////

            // Connect to data base
            connection = getDBConnection();
            // Set auto commit to off (false)
            connection.setAutoCommit(false);   //// START TRANSACTION ///
            // Create statement object for sending SQL statements to db
            statement = connection.createStatement();

            ////////////  Update start date processing  //////////////////

            // Prompt user if they want to update the start date.  If yes,
            // get the start date and update the database.
            System.out.println("[WHMS] Update start date (Y or N)? ");
            // Get yes/no response from user
            String response = getYNResponse();

            // Case for user selecting Y - update start date
            if (response.equals("Y")) {
                // Get start date
                String startDate = getStartDate();
                // Execute sql statement to update start_date
                statement.executeUpdate(
                        "UPDATE MedicalRecord SET start_date = '" + startDate +
                                "' WHERE patient_id = '" + patientID +
                                "' AND record_id = '" + recordID + "' ");

            }

            ////////////  Update end date processing  //////////////////

            // Prompt user if they want to enter/update the end date.  If yes,
            // get the end date and update the database.
            System.out.println("[WHMS] Update end date (Y or N)? ");
            // Get yes/no response from user
            response = getYNResponse();

            // Case for user selecting Y - update end date
            if (response.equals("Y")) {
                String endDate = getEndDate(patientID, recordID);
                // Execute sql statement to update end_date
                statement.executeUpdate(
                        "UPDATE MedicalRecord SET end_date = '" + endDate +
                                "' WHERE patient_id = '" + patientID +
                                "' AND record_id = '" + recordID + "' ");

            }

            ////////////  Update diagnosis details processing  //////////////////

            // Prompt user if they want to enter/update diagnosis details.  If yes,
            // get the diagnosis details and update the database.
            System.out.println("[WHMS] Update diagnosis (Y or N)? ");
            // Get yes/no response from user
            response = getYNResponse();

            // Case for user selecting Y - update diagnosis details
            if (response.equals("Y")) {
                // Get user input for diagnosis details
                System.out.println("[WHMS] Enter diagnosis details: ");
                response = getChoice();
                // Store user response as diagnosisDetails
                String diagnosisDetails = response;
                // Execute sql statement to update diagnosis details
                statement.executeUpdate(
                        "UPDATE MedicalRecord SET diagnosis_details = '" + diagnosisDetails +
                                "' WHERE patient_id = '" + patientID +
                                "' AND record_id = '" + recordID + "' ");
            }

            ////////////  Update prescription processing  //////////////////

            // Prompt user if they want to enter/update the prescription.  If yes,
            // get the prescription and update the database.
            System.out.println("[WHMS] Update prescription (Y or N)? ");
            // Get yes/no response from user
            response = getYNResponse();

            // Case for user selecting Y - update prescription
            if (response.equals("Y")) {
                // Get user input for prescription
                System.out.println("[WHMS] Enter prescription: ");
                response = getChoice();
                // Store user response as prescription
                String prescription = response;
                // Execute sql statement to update prescription
                statement.executeUpdate(
                        "UPDATE MedicalRecord SET prescription = '" + prescription +
                                "' WHERE patient_id = '" + patientID +
                                "' AND record_id = '" + recordID + "' ");
            }

            ////////////  Update doctor processing  //////////////////

            // Prompt user if they want to update the responsible doctor.  If yes,
            // get the new doctor id and update the database.
            System.out.println("[WHMS] Update doctor (Y or N)? ");
            // Get yes/no response from user
            response = getYNResponse();

            // Case for user selecting Y - update doctor
            if (response.equals("Y")) {
                // Get user input for doctor
                int doctorID = findDoctorID();
                // Execute sql statement to update doctor
                statement.executeUpdate(
                        "UPDATE MedicalRecord SET doctor = '" + doctorID +
                                "' WHERE patient_id = '" + patientID +
                                "' AND record_id = '" + recordID + "' ");
            }

            // Commit the changes
            connection.commit();

            // Handle sql exception
        } catch (SQLException exception) {
            recoverFromSqlException();
            // If connection is open, rollback changes, set autocommit
            // to true, and close the connection
            if (connection != null) {
                try {
                    connection.rollback();
                    // Throw sql exception if error
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            // Reset autocommit, close statement and connection no matter what
        } finally {
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
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException();
                }
            }
        }
        // Return the user to the menu
        printMenu();
    }

    /**
     * The deleteMedicalRecord method deletes the selected
     * medical record.  The method uses the findPatientID
     * method to identify the patient for the record to be
     * deleted.  It then uses the listMedicalRecords method
     * to list the medical records for that patient. The user
     * is then prompted for the record ID to delete.  The
     * deleteMedicalRecord method then connects to the database
     * and deletes the medical record for that patient with the
     * selected record ID.
     *
     * @throws Exception
     */
    public void deleteMedicalRecord() throws Exception {
        //  Call patientID method to get patient ID
        int patientID = findPatientID();
        // Variable for Connection object
        Connection connection = null;
        // Variable for PreparedStatement object
        PreparedStatement ps = null;

        // List medical records for the selected patient
        listMedicalRecords(patientID);

        // Call getValidMedicalRecordID method to get recordToDelete
        int recordToDelete = getValidMedicalRecordID("delete", patientID);

        //  Connect to database and delete the identified medical record
        try {
            connection = getDBConnection();
            ps = connection.prepareStatement("DELETE FROM MedicalRecord WHERE patient_id = ? AND record_id = ?");
            ps.setInt(1, patientID);
            ps.setInt(2, recordToDelete);
            ps.executeQuery();
            // Handle sql exception
        } catch (SQLException exception) {
            recoverFromSqlException();
            // Close PreparedStatement and Connection no matte what
        } finally {
            ps.close();
            connection.close();
        }

        // Return to medical record menu
        printMenu();
    }

    /**
     * This listMedicalRecord method prompts the user to enter a patientID
     * and then lists the medical records for that patient.  It uses
     * the listMedicalRecords(int patientID) method to do the work.
     */
    public void listMedicalRecords() throws Exception {
        // Get patient ID
        int patientID = findPatientID();

        // List the records
        listMedicalRecords(patientID);
    }

    /**
     * The listMedicalRecords method lists the medical records
     * associated with the patientID provided to the method
     * as an input.
     *
     * @param patientID
     * @throws Exception
     */
    public void listMedicalRecords(int patientID) throws Exception {
        // Variable for Connection object
        Connection connection = null;
        // Variable for PreparedStatement object
        PreparedStatement ps = null;
        // Variable for PreparedStatement object
        PreparedStatement ps1 = null;
        // Variable for ResultSet
        ResultSet rs = null;
        // Variable for ResultSet
        ResultSet rs1 = null;

        // Connect to the database and perform the SQL query to
        // return a list of the medical records associated with
        // the selected patient.
        try {
            // Connect to the data base
            connection = getDBConnection();
            // Query the database for all medical records for the selected patientID
            ps = connection.prepareStatement("SELECT * FROM MedicalRecord WHERE patient_id = ?");
            // Set parameters for PreparedStatement
            ps.setInt(1, patientID);
            // Execute query and get ResultSet
            rs = ps.executeQuery();

            // Query the database for the patient's last and first name
            ps1 = connection.prepareStatement("SELECT last_name, first_name FROM Patient WHERE patient_id = ?");
            ps1.setInt(1, patientID);
            rs1 = ps1.executeQuery();

            // Store query results for patient's first and last name in
            // lastName and firstName variables
            rs1.next();
            String lastName = rs1.getString("last_name");
            String firstName = rs1.getString("first_name");

            // Print to the terminal a label identifying the patient
            System.out.println();
            System.out.printf("Current medical records for patient: %s, %s", lastName, firstName);

            // Print to the terminal headings for the output
            System.out.println("\nrecord_id   record_type   patient_id   start_date     end_date        diagnosis_ details     prescription        specialist       test_status        test_result       doctor ");
            System.out.println("---------   -----------   ----------   ------------   ------------   --------------------   ---------------   ---------------   ---------------   ---------------   ----------");


            // Print to the terminal each medical record found for the selected patientID
            while (rs.next()) {
                int recordID = rs.getInt("record_id");
                String recordType = rs.getString("record_type");
                int patID = rs.getInt("patient_id");
                String startDate = rs.getString("start_date");
                String endDate = rs.getString("end_date");
                String diagnosis = rs.getString("diagnosis_details");
                String prescrip = rs.getString("prescription");
                String specialist = rs.getString("specialist");
                String testStatus = rs.getString("test_status");
                String testResult = rs.getString("test_result");
                String doctor = rs.getString("doctor");
                System.out.printf("%9d   %-11s   %10d   %12s   %12s   %-20s   %-15s   %15s   %-15s   %-15s   %10s\n",
                        recordID, recordType, patID, startDate, endDate, diagnosis, prescrip, specialist, testStatus, testResult, doctor);
            }
            // Skip a line for clarity
            System.out.println();

            // Handle sql exception
        } catch (SQLException exception) {
            recoverFromSqlException();

            // Close PreparedStatements and Connection no matter what
        } finally {
            rs.close();
            rs1.close();
            ps.close();
            ps1.close();
            connection.close();
        }


    }

//    /**
//     * The returnPatientType asks the user if the patient is new.
//     * It returns the user's response (Y, N).
//     *
//     * @return
//     */
//    public String returnPatientType() {
//        System.out.println("[WHMS] Is patient new (Y, N)?");
//        System.out.print("[WHMS] Choice: ");  // Enter choice here
//        return(getChoice());
//    }


    /**
     * The isValidPatientID method takes as an input information entered
     * by the user when prompted to enter a patient ID.  The method will
     * validate the the input is an integer and that the patient ID exists
     * in the database.  If the ID is valid, the method returns true;
     * if the ID is not valid or does not exist in the database, it
     * returns false.
     *
     * @param s (variable for patientID)
     * @return isValidPatientID
     * @throws Exception
     */
    public boolean isValidPatientID(String s) throws Exception {
        // Variable for patientID
        int patientID;
        // boolean variable for the validity of the patient ID
        boolean isValidPatientID = false;
        // Variable for Connection object
        Connection connection = null;
        // Variable for Statement object
        Statement statement = null;
        // Variable for ResultSet
        ResultSet rs = null;

        // Logic for checking the validity of the patient ID.
        // The input is first checked to see if it is an integer.
        // If not, an exception is thrown.  If the input is a valid
        // integer, then the database is queried to determine if
        // the patient ID is in the database.  If valid, the
        // isValidPatient variable is set to true and returned to
        // the calling method.  If not, the isValidPatient variable
        // is returned to the calling method as false.
        try {
            // Parse the input to see if it is an integer.
            patientID = Integer.parseInt(s);
            // s is a valid integer
            // catch for parsing of input other than a String
        } catch (NumberFormatException ex) {
            // s is not an integer
            return isValidPatientID;
        }

        //  Connect to the data base and query the integer input as patientID
        //  to determine if it is in the data base.
        try {
            // Get Connection
            connection = getDBConnection();
            // Create statement
            statement = connection.createStatement();
            // Execute query and get ResultSet
            rs = statement.executeQuery("SELECT COUNT(*) AS total FROM Patient WHERE patient_id = " + patientID + " ");
            rs.next();  // Need to move cursor to first row

            // Logic for setting isValidPatientID to true if valid
            if (rs.getInt("total") != 0) {
                isValidPatientID = true;
            }

            // catch for exception in database query
        } catch (SQLException exception) {
            recoverFromSqlException();
            // Close ResultSet, Statement, and Connection no matter what
        } finally {
            rs.close();
            statement.close();
            connection.close();

        }

        // return isValidPatientID value to calling method
        return isValidPatientID;
    }

    /**
     * The isValidDoctorID method takes as an input information entered
     * by the user when prompted to enter a doctor ID.  The method will
     * validate the the input is an integer and that the doctor ID exists
     * in the database.  If the ID is valid, the method returns true;
     * if the ID is not valid or does not exist in the database, it
     * returns false.
     *
     * @param s (user input for doctor ID)
     * @return isValidDoctorID
     * @throws Exception
     */
    public boolean isValidDoctorID(String s) throws Exception {
        // boolean variable for the validity of the doctor ID
        boolean isValidDoctorID = false;
        // Variable for Connection object
        Connection connection = null;
        // Variable for Statement
        Statement statement = null;
        // Variable for result set
        ResultSet rs = null;

        // Logic for checking the validity of the doctor ID.
        // The input is first checked to see if it is an integer.
        // If not, an exception is thrown.  If the input is a valid
        // integer, then the database is queried to determine if
        // the doctor ID is in the database.  If valid, the
        // isValidDoctor variable is set to true and returned to
        // the calling method.  If not, the isValidDoctor variable
        // is returned to the calling method as false.
        try {
            // Parse the input to see if it is an integer.
            int doctorID = Integer.parseInt(s);
            // s is a valid integer

            //  Connect to the data base and query the integer input as patientID
            //  to determine if it is in the data base.
            try {
                // Get connection
                connection = getDBConnection();
                // Create statement
                statement = connection.createStatement();
                // Execute query and get ResultSet
                rs = statement.executeQuery("SELECT COUNT(*) AS total FROM Doctor WHERE staff_id = " + doctorID + " ");
                rs.next();  // Need to move cursor to first row

                // Logic for setting isValidDoctorID to true if valid
                if (rs.getInt("total") != 0) {
                    isValidDoctorID = true;
                }

                // catch for exception in database query
            } catch (SQLException exception) {
                throw new RuntimeException(exception);
                // No matter what, close connection, result set and statement
            } finally {
                rs.close();
                statement.close();
                connection.close();
            }

            // catch for parsing of input other than a String
        } catch (NumberFormatException ex) {
            // s is not an integer
        }

        // return isValidPatientID value to calling method
        return isValidDoctorID;
    }

    /**
     * Method for getting a valid medical record ID.
     *
     * @param action
     * @param patientID
     * @return validMedicalRecordID
     * @throws Exception
     */
    public int getValidMedicalRecordID(String action, int patientID) throws Exception {
        // Set variable for validRecordID.  False indicates invalid.
        boolean validRecordID = false;
        // Initialize variable for recordID
        int recordID = 0;
        // Variable for Connection object
        Connection connection = null;
        // Variable for Statement object
        Statement statement = null;
        // Variable for ResultSet
        ResultSet rs = null;

        // Loop for getting valid record id.  User is
        // repeatedly asked until a valid integer is entered.
        while (!validRecordID) {
            // Prompt user to enter record id
            System.out.println("[WHMS] Enter record id to " + action + " or press Enter to return to medical record menu: ");
            // Get the user input for record id
            String response = getChoice();

            // If user enters "" to return to medical record menu
            if (response.equals("")) {
                printMenu();
            }
            // Try/catch for evaluating input from user.  If
            // an integer, user input is stored in recordID and
            // the validRecordID variable is changed to true to
            // indicate a valid record ID was entered.
            try {
                // Parse user response for integer and store in recordID
                recordID = Integer.parseInt(response);
                // Get connection
                connection = getDBConnection();
                // Create statement
                statement = connection.createStatement();
                // Execute query and get ResultSet
                rs = statement.executeQuery("SELECT Count(*) AS total FROM MedicalRecord WHERE patient_id = " + patientID + " AND record_id = " + recordID + " ");
                // Go to first row of ResultSet
                rs.next();
                // Store total retrieved in query in count
                int count = rs.getInt("total");
                // If count = 0 and action is delete or update, throw exception
                if ((action.equals("delete") || action.equals("update")) && count == 0) {
                    throw new NumberFormatException();
                }
                // If count > 0 and action is add, throw exception
                if (action.equals("add") && count > 0) {
                    throw new NumberFormatException();
                }
                validRecordID = true;

                // Catch inputs that are not integers and re-prompt
                // user for valid input
            } catch (NumberFormatException exception) {
                System.out.println("Invalid input");
            } catch (SQLException ex) {
                // Call recoverFromSqlException to get message in case of sql error
                recoverFromSqlException();
                // Close ResultSet, Statement, and Connection no matter what
            } finally {
                rs.close();
                statement.close();
                connection.close();
            }
        }
        // Return medical recordI
        return recordID;
    }


    /**
     * The getYNResponse method continually asks the user to enter
     * a Y or a N until one is entered.
     *
     * @return response
     */
    public String getYNResponse() {
        // Variable for response
        String response = "";
        // While loop for getting Y or N answer
        while (!response.equals("Y") && !response.equals("N")) {
            // Get user response and store in "response"
            response = getChoice();
            // Case for where input meets criteria
            if (response.equals("Y") || response.equals("N")) {
                break;
                // Case for where user does not enter a Y or and N
            } else {
                System.out.println("Please enter a 'Y' or a 'N' ");
            }
        }
        // Return response
        return response;
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

    /**
     * Method that provides text in the event of a SQL error
     */
    public void recoverFromSqlException() {
        // Print to terminal error message
        System.out.println();
        System.out.println("Invalid entry.  May be duplicate entry or invalid input.");
        System.out.println("Returning to MedicalRecord menu.");
    }


    /**
     * getStartDate gets a start date for the user when a start date
     * is required.  It validates that the user entered something in the
     * correct format.  It depends on the SQL statement outside the method
     * to validate the actual content.
     *
     * @return startDate
     */
    public String getStartDate() {
        // Prompt user for start date
        System.out.println("[WHMS] Enter start date (xxxx-xx-xx): ");
        // Get user input
        String response = getChoice();
        // while loop for making user enter start date
        while (response.equals("") || !isValidDate(response)) {
            System.out.println("[WHMS] Valid start date is required.");
            System.out.println("[WHMS] Enter start date (xxxx-xx-xx): ");
            response = getChoice();
        }
        // Return start date to user
        return response;
    }

    /**
     * getEndDate gets an end date from the user in the proper format.  It ensures
     * that the end date is greater than or equal to the start date
     *
     * @param patientID
     * @param recordID
     * @return endDate
     * @throws Exception
     */
    public String getEndDate(int patientID, int recordID) throws Exception {
        // Prompt user for end date
        System.out.println("{WHMS} Enter end date (xxxx-xx-xx) or Enter to return to medical record menu: ");
        // Get user input
        String response = getChoice();

        // Case for Enter to return to medical record menu
        if (response.equals("")) {
            //return to medical record menu
            printMenu();
        }

        // while loop for making user enter correctly formatted date
        while (!isValidDate(response) || !dateIsLaterThanStartDate(patientID, recordID, response)) {
            // Case for the input is not properly formatted
            if (!isValidDate(response)) {
                System.out.println("[WHMS] Enter properly formatted end date: ");
            }
            // Case for endDate input is earlier that the startDate
            else if (!dateIsLaterThanStartDate(patientID, recordID, response)) {
                System.out.println("[WHMS] End date must be later than start date:");
            }
            // Get response from user
            response = getChoice();
        }
        // return properly formatted and consistent end date
        return response;
    }

    /**
     * Checks that a date is formatted correctly
     *
     * @param date
     * @return boolean
     */
    public boolean isValidDate(String date) {
        // Variable for expression
        String regex;
        // Variable for pattern object
        Pattern pattern;
        // Variable for Matcher object
        Matcher matcher;

        // Define proper date format
        regex = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
        // Transform regex into pattern for matching
        pattern = Pattern.compile(regex);
        // Create a Matcher
        matcher = pattern.matcher(date);
        // Determine if there is a match and return result
        return matcher.matches();
    }

    /**
     * Close all open medical records, i.e., enter end date
     * into all medical records without an end date.
     *
     * @throws Exception
     * @input patientID
     * @input date
     */
    public void closeOpenMedicalRecords(int patientID, String date) throws Exception {
        // Variable for connection object
        Connection connection = null;
        // Variable for statement object
        Statement statement = null;
        try {
            // Connect to data base
            connection = getDBConnection();
            // Set auto commit to off (false)
            connection.setAutoCommit(false);   //// START TRANSACTION ///
            // Create statement object for sending SQL statements to db
            statement = connection.createStatement();

            // Update records
            statement.executeUpdate("UPDATE MedicalRecord SET end_date = '" + date + "' WHERE patient_id = '" + patientID + "' AND end_date IS NULL ");

            // Commit the changes
            connection.commit();

            // Handle SWL exception
        } catch (SQLException exception) {
            recoverFromSqlException();
            // If connection is open, rollback changes, set autocommit
            // to true, and close the connection
            if (connection != null) {
                try {
                    connection.rollback();
                    // Throw sql exception if error
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            // Reset autocommit, close statement and connection no matter what
        } finally {
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
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException();
                }
            }
        }
        // Return the user to the menu
        printMenu();
    }

    /**
     * This version of closeOpenMedicalRecords requests
     * the patientID and end date from the user, then
     * adds the end date to all open records with
     * that patient_id.  It calls the
     * closeOpenMedicalRecords(patient_id, end_date)
     * method for completing the transaction.
     *
     * @throws Exception
     */
    public void closeOpenMedicalRecords() throws Exception {

        // Get patient id
        int patientID = findPatientID();

        // Get end date
        String endDate = getEarliestValidEndDate(patientID);

        // Call closeOpenMedicalRecords method to do work
        closeOpenMedicalRecords(patientID, endDate);
    }

    /**
     * The dateIsLaterThanStartDate method compares the end
     * date provided as an input to the start date in the
     * medical record referenced by the inputs patientID and
     * recordID.  It returns true if the end date is greater
     * than or equal to the start date, false otherwise.
     *
     * @param patientID
     * @param recordID
     * @param endDate
     * @return boolean
     * @throws Exception
     */
    public boolean dateIsLaterThanStartDate(int patientID, int recordID, String endDate) throws Exception {
        // Variable for connection object
        Connection connection = null;
        // Variable for statement object
        Statement statement = null;
        // Variable for result set of query
        ResultSet rs = null;
        // Variable for date compare
        boolean result = false;

        try {
            // Connect to database
            connection = getDBConnection();
            // Create statement object for sending SQL statements to db
            statement = connection.createStatement();
            // Execute query and get ResultSet
            rs = statement.executeQuery("SELECT start_date FROM MedicalRecord WHERE patient_id = ' " + patientID + " ' AND record_id = ' " + recordID + "' ");
            // Move to first row of results
            rs.next();
            // Store the start_date result from the query in the variable startDate
            String startDate = rs.getString("start_date");
            // Convert startDate and endDate to Date type
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date start_Date = sdf.parse(startDate);
            Date end_Date = sdf.parse(endDate);
            // If start_Date > end_Date, set result to false
            if (start_Date.compareTo(end_Date) > 0) {
                result = false;
                // If start_Date <= end_Date, set result to true
            } else {
                result = true;
            }
            // Catch sql exception
        } catch (SQLException exception) {
            recoverFromSqlException();

            // Reset autocommit, close statement and connection no matter what
        } finally {

            // Close ResultSet
            rs.close();
            //Close statement
//            statement.close();

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
        // Return result
        return result;

    }

    /**
     * getEarliestValidEndDate gets an end date from the user that is in the proper format
     * and that is not earlier that the latest start date of the open medical records
     * for the specified patient.
     *
     * @param patientID
     * @return valid date
     * @throws Exception
     */
    public String getEarliestValidEndDate(int patientID) throws Exception {
        // Prompt user for end date
        System.out.println("[WHMS] Enter end date (xxxx-xx-xx) or Enter to return to medical record menu: ");
        // Get user input
        String response = getChoice();

        // Case for user entering Enter to return to medical record menu
        if (response.equals("")) {
            // Return to medical record menu
            printMenu();
        }

        // while loop for making user enter correctly formatted date
        while (!isValidDate(response) || !dateIsLaterThanLatestStartDate(patientID, response)) {
            // Case for response not in proper format
            if (!isValidDate(response)) {
                System.out.println("[WHMS] Enter properly formatted end date: ");
            }
            // Case for date not being later than latest start date
            else if (!dateIsLaterThanLatestStartDate(patientID, response)) {
                System.out.println("[WHMS] End date must be later than latest start date:");
            }
            // Get response from user
            response = getChoice();
        }
        // return properly formatted end date
        return response;
    }

    /**
     * The dateIsLaterThanLatestStartDate returns true if the date entered by the user
     * for a specific patient is later than the latest start date.  Otherwise, it returns
     * false
     *
     * @param patientID
     * @param date
     * @return boolean
     * @throws Exception
     */
    public boolean dateIsLaterThanLatestStartDate(int patientID, String date) throws Exception {
        // Variable for connection object
        Connection connection = null;
        // Variable for statement object
        Statement statement = null;
        // Variable for result set of query
        ResultSet rs = null;
        // Variable for date compare
        boolean result = false;

        try {
            // Connect to database
            connection = getDBConnection();
            // Create statement object for sending SQL statements to db
            statement = connection.createStatement();
            // Execute query and get ResultSet
            rs = statement.executeQuery("SELECT MAX( start_date ) FROM MedicalRecord WHERE patient_id = ' " + patientID + " ' AND end_date IS NULL ");
            // Skip to first row of results
            rs.next();

            // Store the query result in the variable startDate
            String startDate = rs.getString(1);
            // Convert startDate and date to Date types
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date start_Date = sdf.parse(startDate);
            Date end_Date = sdf.parse(date);
            // Case for start_Date > end_Date
            if (start_Date.compareTo(end_Date) > 0) {
                result = false;
                // Case for start_Date <= end_Date
            } else {
                result = true;
            }

            // Handle sql exception
        } catch (SQLException exception) {
            recoverFromSqlException();

            // Reset autocommit, close statement and connection no matter what
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
        return result;

    }

    public void printAllPatientsInformation() throws Exception {

        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;

        try {
            connection = getDBConnection();
            String sqlQuery = "SELECT Patient.patient_id, ssn, first_name, last_name," +
                    "DOB, gender, TIMESTAMPDIFF(YEAR, DOB, CURDATE()) AS 'age'," +
                    "phone_number, street_address, city, state, zip_code" +
                    "Patient.status, IF(PatientAssignedBed.end_date IS NULL AND" +
                    " PatientAssignedBed.status = 'Assigned', 'yes', 'no' ) AS " +
                    "'in_ward', IF(MedicalRecord.end_date IS NULL, 'no', 'yes')" +
                    "AS 'completing_treatment', PatientAssignedBed.end_date AS" +
                    " 'release_date', MedicalRecord.end_date AS 'checkout_date' " +
                    " FROM Patient JOIN PatientAssignedBed JOIN MedicalRecord" +
                    " WHERE Patient.patient_id = PatientAssignedBed.patient_id" +
                    "   AND Patient.patient_id = MedicalRecord.patient_id" +
                    "   AND record_type = 'checkin' ORDER BY in_ward";
            statement = connection.createStatement();
            rs = statement.executeQuery(sqlQuery);


            int patientID = 0;
            int currentPatientID = 0;
            while (rs.next()) {
                patientID = rs.getInt("Patient.patientID");

                if (currentPatientID == patientID) {
                    continue;
                }
                currentPatientID = patientID;

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
                int zipCode = rs.getInt("zip_code");
                String status = rs.getString("Patient.status");
                String inWard = rs.getString("in_Ward");
                String completingTreatment = rs.getString("completing_treatment");
//                String bedReleaseDate = rs.getString("release_date");
//                String checkOutDate = rs.getString("checkout_date");

                System.out.println("[WHMS] Patient ID: " + patientID);
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
                System.out.println("[WHMS] In ward: " + inWard);
                System.out.println("[WHMS] Completing treatment: " + completingTreatment);

            }

        } catch (SQLException exception) {
            recoverFromSqlException();
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

}

