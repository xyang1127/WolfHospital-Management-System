package csc540proj.information;

import csc540proj.utility.CommandLine;
import csc540proj.utility.Helper;

import java.sql.*;


public class WardInfo {

    private final CommandLine commandLine;
    private final Connection connection;

    public WardInfo(final CommandLine commandLine, final Connection connection) {
        this.commandLine = commandLine;
        this.connection = connection;
    }

    private void printMenu() {
        commandLine.println("**************************************************");
        commandLine.println("Patient Information Menu:");
        commandLine.println("");
        commandLine.println("1 - Enter New Ward");
        commandLine.println("2 - Update Existing Ward");
        commandLine.println("3 - Remove Ward");
        commandLine.println("4 - Print Ward");
        commandLine.println("5 - Assign Nurse");

        commandLine.println("6 - Remove Nurse Ward Assignment");

        commandLine.println("");
        commandLine.println("Enter - Return to previous menu");
        commandLine.println("");

        commandLine.print("Choice: ");
    }

    /**
     * After every function call, we return to this menu
     * retreat to previous menu if no or invalid input is passed
     *
     * @param choice
     * @throws Exception
     */
    private void action(String choice) throws Exception {
        switch (choice) {
            case "1":
                enterWard();
                run();
            case "2":
                updateWard();
                run();
                break;
            case "3":
                deleteWard();
                run();
                break;
            case "4":
                printWards();
                run();
                break;
            case "5":
                assignNurse();
                run();
                break;
            case "6":
                removeNurseWardAssignment();
                run();
                break;
            default:
                commandLine.println("Returning to information processing menu.");
                InfoProcessing IP = new InfoProcessing(commandLine, connection);
                IP.run();
        }
    }

    /**
     * prints menu and then runs menu choice system
     *
     * @throws Exception
     */
    public void run() throws Exception {
        printMenu();
        action(Helper.getChoice());
    }

    /**
     * print a list of all wards and all associated attributes as defined in demo data
     */
    private void printWards() {
        String query = "SELECT * FROM Ward w NATURAL JOIN WardType ORDER BY w.ward_number;";
        Helper.queryAndHandle(connection, query, resultSet -> {
            int currWard;
            String dateCheck;
            resultSet.next();
            while (!resultSet.isAfterLast()) {
                commandLine.println("");
                currWard = resultSet.getInt("ward_number");
                commandLine.println("Ward Number: " + currWard);
                commandLine.println("Capacity: " + resultSet.getInt("capacity"));
                commandLine.println("Charges per day: " + resultSet.getInt("charge_per_day"));
                commandLine.print("Assigned Nurse(s): ");
                //  iterate through all nurses associated with current ward and print them as a list
                Helper.queryAndHandle(connection, "SELECT * FROM NurseAssignedWard WHERE ward_number = " + currWard, resultSet1 -> {
                    if (!resultSet1.next()) {
                        System.out.print("- ");
                        return;
                    }
                    if (!resultSet1.isAfterLast())
                        System.out.print(resultSet1.getInt("staff_id"));
                    else
                        System.out.print(" - ");
                    while (resultSet1.next()) {
                        System.out.print(", " + resultSet1.getInt("staff_id"));
                    }
                });
                System.out.println();
                commandLine.print("Patients ID(s): ");
                Helper.queryAndHandle(connection, "SELECT * FROM PatientAssignedBed WHERE ward_number = "+currWard+" AND end_date IS NULL AND status = 'Assigned'", resultSet2 -> {
                    if (!resultSet2.next()) {
                        System.out.print("- ");
                        return;
                    }
                    if (!resultSet2.isAfterLast())
                        System.out.print(resultSet2.getInt("patient_id"));
                    else
                        System.out.print(" - ");
                    while (resultSet2.next()) {
                        System.out.print(", " + resultSet2.getInt("patient_id"));
                    }
                });
                resultSet.next();

                System.out.println();
            }
        });
    }

    /**
     * enter ward into the database
     */
    public void enterWard() {
        int wardNum, wardType, capacity;
        float[] chargePerDay = new float[1];

        //  error check inputs and process information about ward
        wardNum = Integer.parseInt(Helper.confirmInput(commandLine, "Enter ward number: ", "^[1-9][0-9]{1,2}$|^\\d$"));
        wardType = Integer.parseInt(Helper.confirmInput(commandLine, "Enter ward type (1, 2, 4): ", "[1|2|4]"));
        capacity = Integer.parseInt(Helper.confirmInput(commandLine, "Enter ward capacity: ", "^[1-9][0-9]{1,2}$|^\\d$"));
        try {
            chargePerDay[0] = Float.parseFloat(Helper.confirmInput(commandLine, "Enter ward charge per day (or leave blank for current ward charge per day): ", "^([0-9]*\\.?[0-9]*)?$|^\\d$"));
        } catch (NumberFormatException e) {
            //  get current charge of given ward type
            final String query = String.format("SELECT charge_per_day FROM WardType WHERE ward_type=%d", wardType);
            Helper.queryAndHandle(connection, query, rs -> {
                rs.next();
                chargePerDay[0] = rs.getFloat("charge_per_day");
            });
        }

        commandLine.println("Entering Ward...");
        //  insert ward information, capacity = availability when ward is created
        String query = "INSERT INTO Ward VALUES (" + wardNum + ", " + wardType + ", " + capacity + ", " + capacity + ")";
        Helper.insertAndHandle(connection, query);
        //  update wardtype table with possible new charge
        query = String.format("UPDATE WardType SET charge_per_day=%f WHERE ward_type=%d", chargePerDay[0], wardType);
        Helper.insertAndHandle(connection, query);

        // insert beds
        commandLine.println("Entering " + capacity + " beds");
        for (int i = 1; i <= capacity; i++) {
            query = "INSERT INTO Bed VALUES (" + i + ", " + wardNum + ", " + 1 + ")";
            Helper.insertAndHandle(connection, query);
        }

        commandLine.println("Done.");
    }

    /**
     * update ward information
     */
    private void updateWard() {
        commandLine.print("Enter ward number: ");
        int wardNum = Integer.parseInt(Helper.getChoice());

        commandLine.println("Entering Ward...");
        String repeat = "y";
        String query = "SELECT * FROM Ward NATURAL JOIN WardType WHERE ward_number = " + wardNum + "";
        //  pass query and handle connection
        //  repeat if user chooses
        while (repeat.equals("y")) {
            Helper.queryAndHandle(connection, query, resultSet -> {
                if (resultSet.next()) {
                    ResultSetMetaData rsmd = resultSet.getMetaData();
                    int cNum = rsmd.getColumnCount();
                    int i;
                    //  print attributes of ward
                    for (i = 1; i <= cNum; i++)
                        commandLine.println("[" + i + "] " + rsmd.getColumnName(i) + ": " + resultSet.getString(i));

                    int column = Integer.parseInt(Helper.confirmInput(commandLine, "Select the column number you wish to edit (0 to cancel): ", "^[0-9][0-9]{1,2}$|^\\d$"));
                    //  ward number is not editable
                    if (column == 2) {
                        commandLine.println("You can not edit Ward Number");
                        return;
                    }
                    //  editing availability would introduce inconsistencies in the database
                    if (column == 4) {
                        commandLine.println("You can not edit availability");
                        return;
                    } else if (column == 0) {
                        commandLine.println("Canceled update.(Enter to return to previous menu");
                        return;
                    }
                    commandLine.println("Enter updated " + rsmd.getColumnName(column) + ": ");
                    String ud = Helper.getChoice();

                    String sql = null;
                    //  if charge per day is edited, we must edit the ward type table
                    if (rsmd.getColumnName(column).equals("charge_per_day")) {
                        sql = "UPDATE WardType SET charge_per_day=" + ud + " WHERE ward_type = " + resultSet.getString("ward_type");
                    } else if (rsmd.getColumnName(column).equals("capacity")) {
                        int newAvailability = getNewCapacity(wardNum, ud);
                        if (newAvailability >= 0) {
                            Helper.queryAndHandle(connection, "SELECT bed_number FROM Bed WHERE ward_number = " + wardNum + " AND availability = 1 ORDER BY bed_number DESC", resultSet1 -> {
                                int k = resultSet.getInt("capacity") - Integer.parseInt(ud);
                                for (int j = 0; j < k; j++) {
                                    resultSet1.next();
                                    Helper.insertAndHandle(connection, "DELETE FROM Bed WHERE ward_number = " + wardNum + " AND bed_number = " + resultSet1.getInt(1));
                                    commandLine.println("deleting bed " + resultSet1.getInt(1) + " in ward " + wardNum);
                                }
                            });
                            Helper.queryAndHandle(connection, "Select bed_number FROM Bed WHERE ward_number = " + wardNum + " ORDER BY bed_number DESC", resultSet2 -> {
                                resultSet2.next();
                                int k = resultSet.getInt("capacity") - Integer.parseInt(ud);
                                int bedNum;
                                for (int j = 0; j > k; j--) {
                                    bedNum = (resultSet2.getInt(1) - j) + 1;
                                    Helper.insertAndHandle(connection, "INSERT INTO Bed VALUES (" + bedNum + ", " + wardNum + ", 1)");
                                }
                            });


                            sql = String.format(
                                    "UPDATE Ward SET capacity=%s, availability=%d WHERE ward_number=%d",
                                    ud, newAvailability, wardNum);
                        } else {
                            commandLine.println("Invalid new capacity");
                        }
                    } else {
                        sql = "UPDATE Ward SET " + rsmd.getColumnName(column) + " = '" + ud + "' WHERE ward_number = '" + wardNum + "'";
                    }

                    if (sql != null) {
                        Helper.insertAndHandle(connection, sql);
                        commandLine.println("Ward Updated.");
                    }

                    commandLine.println("Continue updating ward? (y/n): ");
                } else
                    commandLine.println("Number does not match a current ward. (Enter to return to previous menu)");
            });
            repeat = Helper.getChoice();
        }
    }

    /**
     * retrieves updated capacity for ward
     * availability cannot be greater than capacity
     *
     * @param wardNum
     * @param newCapacityString
     * @return
     */
    private int getNewCapacity(int wardNum, final String newCapacityString) {
        int newCapacity;
        try {
            newCapacity = Integer.parseInt(newCapacityString.trim());
        } catch (NumberFormatException e) {
            commandLine.printf("Cannot parse new capacity '%s' as a number", newCapacityString);
            return -1;
        }

        final int[] newAvailabilityRef = new int[]{-1};
        final String query = String.format("SELECT capacity,availability FROM Ward WHERE ward_number=%d", wardNum);
        Helper.queryAndHandle(connection, query, rs -> {
            if (rs.next()) {
                int oldCapacity = rs.getInt("capacity");
                int oldAvailability = rs.getInt("availability");
                newAvailabilityRef[0] = oldAvailability + (newCapacity - oldCapacity);
            } else {
                commandLine.println("Invalid ward number");
            }
        });
        return newAvailabilityRef[0];
    }

    /**
     * deletes ward in database
     * CASCADE deletes all entries where ward is foreign key
     */
    private void deleteWard() {
        commandLine.print("Enter ward number: ");
        int wardNum = Integer.parseInt(Helper.getChoice());

        commandLine.println("Are you sure you want to delete ward " + wardNum + " from the system? " +
                "This will also remove any beds and bed assignments associated with this ward. Type 'yes' to confirm.");
        String confirm = Helper.getChoice();

        if (confirm.equals("yes")) {
            //  delete ward from all tables
            Helper.insertAndHandle(connection, "DELETE FROM PatientAssignedBed WHERE ward_number = " + wardNum);
            Helper.insertAndHandle(connection, "DELETE FROM Bed WHERE ward_number = " + wardNum);
            Helper.insertAndHandle(connection, "DELETE FROM Ward WHERE ward_number = " + wardNum);

            commandLine.println("Ward " + wardNum + " has been removed from the system. Returning to ward information menu.");
        }
    }

    /**
     * assign nurse to ward
     * multiple nurses can be assigned to one ward
     * multiple wards can be assigned to one nurse
     */
    private void assignNurse() {

        try {
            commandLine.print("Enter ward number: ");
            String wardNum = Helper.getChoice();
            int newWard = Integer.parseInt(wardNum);
            String query = String.format("SELECT * FROM Ward WHERE ward_number=%s", wardNum);
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(query);
            if (!resultSet.next()) {
                commandLine.println("There is no such ward in the hospital");
                return;
            }

            commandLine.print("Enter the ID of the nurse: ");
            String nurse = Helper.getChoice();
            query = String.format("SELECT * FROM Staff WHERE staff_id=%s AND job_title='%s'", nurse, "Nurse");
            resultSet = statement.executeQuery(query);
            if (!resultSet.next()) {
                commandLine.println("There is no such nurse in the hospital");
                return;
            }

            query = String.format("SELECT * FROM NurseAssignedWard WHERE staff_id=%s", nurse);
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int curWard = resultSet.getInt("ward_number");
                if (curWard == newWard) {
                    System.out.println("[WHMS] this nurse assigned ward information already exists in the database");
                    return;
                }
            }

            query = "INSERT INTO NurseAssignedWard VALUES (" + nurse + ", " + wardNum + ")";
            Helper.insertAndHandle(connection, query);

            commandLine.println("nurse " + nurse + " has been successfully to ward " + wardNum);

        } catch (SQLException e) {
            System.out.println("[WHMS] Something went wrong when trying to assign nurse to ward");
        }
    }

    /**
     * Remove a nurse-ward assignment.
     * @throws Exception
     */
    private void removeNurseWardAssignment() throws Exception {
        // Variable for Connection
        Connection connect = null;
        // Variable for Statement
        Statement stment = null;
        // Variable for ResultSet
        ResultSet rs = null;

        // List current nurse ward assignments

        // Print a header for the information
        System.out.println("[WHMS] *******************************");
        System.out.println("[WHMS] Current Nurse Ward Assignments:");
        System.out.println("[WHMS] *******************************");
        System.out.println("[WHMS] Staff ID    Ward Number");
        System.out.println("[WHMS] --------    -----------");

        //  Connect to database, query database for all nurse-ward assignments, print results
        try {
            // Connect to database
            connect = DriverManager.getConnection("jdbc:mariadb://classdb2.csc.ncsu.edu/gjohnso", "gjohnso", "proj540");
            // Create statement object
            stment = connect.createStatement();
            // Create string for query
            String getNurseAssignmentsQuery = String.format("SELECT * FROM NurseAssignedWard");
            // Execute query and get results
            rs = stment.executeQuery(getNurseAssignmentsQuery);

            // While loop for printing results to terminal
            while ( rs.next() ) {
                int staffID = rs.getInt("staff_id");
                int wardNumber = rs.getInt("ward_number");
                System.out.printf("[WHMS]%7d    %11d\n", staffID, wardNumber);
            }
        // Catch sql exception
        } catch (SQLException ex) {
            System.out.println("Error in data");
        // Close ResultSet, Statement, and Connection no matter what
        } finally {

            // Close ResultSet
            if ( rs != null ) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    throw new RuntimeException();
                }
            }
            // Close statement if not null
            if (stment != null) {
                try {
                    stment.close();
                } catch (SQLException e) {
                    throw new RuntimeException();
                }
            }
            // Close connection if not null
            if (connect != null) {
                try {
                    connect.close();
                } catch (SQLException e) {
                    throw new RuntimeException();
                }
            }
        }

        // Request changes and update table

        // Variable for staff ID
        int staffID;
        // Variable for ward number
        int wardNumber;

        // Get valid staff ID by calling getValidNurseAssignedToWardsStaffID method
        staffID = getValidNurseAssignedToWardStaffID();

        if (staffID == -1) {
            return;
        }
        // Get valid ward number by calling getValidNurseAssignedWardNumber method
        wardNumber = getValidNurseAssignedWardNumber(staffID);
        if (wardNumber == -1) {
            return;
        }

        // Delete assignment by calling deleteNurseAssignment
        deleteNurseAssignment (staffID, wardNumber);

    }

    /**
     * Gets a valid nurse staff id.  A valid id is one that is in the
     * NurseAssignedWard table.
     *
     * @return valid nurse id
     * @throws Exception
     */
    private int getValidNurseAssignedToWardStaffID() throws Exception {
        // Variable for Connection
        Connection connect = null;
        // Variable for Statement
        Statement stment = null;
        // Variable for ResultSet
        ResultSet rs = null;
        // Variable for response
        String response = "";

        // Initial prompt to user to enter staff id of nurse
        System.out.println("[WHMS} Enter staff ID of nurse to remove (Enter to return to Ward menu):");
        // Get response
        response = Helper.getChoice();
        // If response "", return to Ward menu
        if (response.equals("")) {
            return -1;
        }

        // While look for getting staff id of nurse currently assigned to a ward
        while (!isValidNurseAssignedToWard(response)) {    // Continue until user enters valid input
            // If response "", return to Ward menu
            if (response.equals("")) {
                return -1;
            }
            // Print invalid entry message as the result of invalid input from user
            System.out.println("[WHMS] Invalid entry.");
            // Reprompt user to enter valid data
            System.out.println("[WHMS} Enter valid staff ID of nurse currently assigned to a ward (Enter to return to Ward menu):");
            // Get next response
            response = Helper.getChoice();

        }
        // Return valid staff id to calling method
        return Integer.parseInt(response);
    }

    /**
     * Check that the nurse is assigned to a (any) ward
     *
     * @param response user response to prompt for nurse id
     * @return boolean true if nurse is assigned to a ward
     */
    private boolean isValidNurseAssignedToWard( String response) {
        // Variable for isValid
        boolean isValid = false;
        // Variable for Connection
        Connection connect = null;
        // Variable for Statement
        Statement stment = null;
        // Variable for ResultSet
        ResultSet rs = null;

        // Connect to database, query to see if response is a nurse assigned to a ward.
        // Update isValid flag to true if value entered as a response is a nurse assigned to a ward.
        try {
            // Variable for staffID.  Convert response to int.  If not, exception will be thrown.
            int staffID = Integer.parseInt(response);

            // Connect to database
            connect = DriverManager.getConnection("jdbc:mariadb://classdb2.csc.ncsu.edu/gjohnso", "gjohnso", "proj540");
            // Create Statment object
            stment = connect.createStatement();
            // Define query statement for getting current staff_id's in table
            String staffIDQuery = String.format("SELECT staff_id FROM NurseAssignedWard WHERE staff_id  = %d", staffID);
            // Execute statement and get results
            rs = stment.executeQuery(staffIDQuery);
            // If a value is returned, staff_id was in table, change isValid flag to true, otherwise continue
            if ( rs.next() ) {
                isValid = true;
            }
        // Catch number format exception if user response was not an integer
        } catch (NumberFormatException e) {
            System.out.println("[WHMS] Invalid response. Response must be a number.");
        // Catch SQL exception
        } catch (SQLException f) {
            System.out.println("[WHMS] Invalid entry.  Response must be an existing nurse ID.");
        // Close ResultSet, Statement, and Connection no matter what
        } finally {
            // Close ResultSet
            if ( rs != null ) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    throw new RuntimeException();
                }
            }
            // Close statement if not null
            if (stment != null) {
                try {
                    stment.close();
                } catch (SQLException e) {
                    throw new RuntimeException();
                }
            }
            // Close connection if not null
            if (connect != null) {
                try {
                    connect.close();
                } catch (SQLException e) {
                    throw new RuntimeException();
                }
            }
        }
        // Return isValid flag
        return isValid;
    }

    /**
     * Get a valid nurse-assigned-ward-number.  A valid nurse-assigned-ward-number
     * is one that is in the NurseAssignedWard table.
     *
     * @param staffID staff id of nurse
     * @return wardNumber ward assignment
     * @throws Exception
     */
    private int getValidNurseAssignedWardNumber(int staffID) throws Exception {
        // Variable for Connection
        Connection connect = null;
        // Variable for Statement
        Statement stment = null;
        // Variable for ResultSet
        ResultSet rs = null;
        // Variable for response
        String response = "";

        // Print initial prompt for ward number
        System.out.println("[WHMS} Enter ward number (Enter to return to Ward menu):");
        // Get response
        response = Helper.getChoice();
        // If response "", return to Ward menu
        if (response.equals("")) {
            return -1;
        }

        // While loop for getting valid ward number
        // Continue until valid response from user or until user hits return to go back
        // to Ward menu.
        while (!isValidNurseAssignedWard(response, staffID)) {
            // If response a "", return to Ward menu
            if (response.equals("")) {
                return -1;
            }
            // Invalid entry message
            System.out.println("[WHMS] Invalid entry.  Check that ward entered is assigned to selected nurse.");
            // Reprompt user to ward number
            System.out.println("[WHMS} Enter valid ward number (Enter to return to Ward menu):");
            // Get response
            response = Helper.getChoice();

        }
        // Return ward number.  response at this point must be an integer.
        return Integer.parseInt(response);
    }


    /**
     * Check that the users response is a valid nurse-assigned-ward.
     * A valid nurse-asssigned-ward is one that exists in the NurseAssignedWard
     * table and is associated with the staff id of the nurse entered.
     *
     * @param response user input when prompted for ward number
     * @param staffID  of nurse
     * @return boolean on whether or not the ward entry is valid
     */
    private boolean isValidNurseAssignedWard( String response, int staffID ) {
        // Variable for isValid
        boolean isValid = false;
        // Variable for Connection
        Connection connect = null;
        // Variable for Statement
        Statement stment = null;
        // Variable for ResultSet
        ResultSet rs = null;

        // Validate user entry was an integer.  Connect to database and query to
        // see if the ward number entered is onr associated with the nurse.
        try {
            // Convert user input to integer.  If parse fails, throw exception
            int wardNumber = Integer.parseInt(response);

            // Connect to database
            connect = DriverManager.getConnection("jdbc:mariadb://classdb2.csc.ncsu.edu/gjohnso", "gjohnso", "proj540");
            // Create Statement object
            stment = connect.createStatement();
            // Crete string of query statement
            String staffIDQuery = String.format("SELECT ward_number FROM NurseAssignedWard WHERE staff_id = %d AND ward_number  = %d", staffID, wardNumber);
            // Execute query and get results
            rs = stment.executeQuery(staffIDQuery);
            // If values returned, set isValid flag ro true
            if ( rs.next() ) {
                isValid = true;
            }
        // Catch number format exception - i.e., the user did not enter an integer
        } catch (NumberFormatException e) {
            System.out.println("[WHMS] Invalid response. Response must be a number.");
        // Catch sql exception
        } catch (SQLException f) {
            System.out.println("[WHMS] Invalid entry.  Response must be a ward with a nurse currently assigned.");
        // Close ResultSet, Statement, and Connection no matter what
        } finally {
            // Close ResultSet
            if ( rs != null ) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    throw new RuntimeException();
                }
            }
            // Close statement if not null
            if (stment != null) {
                try {
                    stment.close();
                } catch (SQLException e) {
                    throw new RuntimeException();
                }
            }
            // Close connection if not null
            if (connect != null) {
                try {
                    connect.close();
                } catch (SQLException e) {
                    throw new RuntimeException();
                }
            }
        }
        // Return value of isValid
        return isValid;
    }

    /**
     * Delete a nurse-ward assignment
     *
     * @param staffID of nurse
     * @param wardNumber ward number of assignment to be removed
     */
    private void deleteNurseAssignment( int staffID, int wardNumber ) {
        // Variable for Connection
        Connection connect = null;
        // Variable for Statement
        Statement stment = null;

        // Connect to database and delete tuple with staffID and wardNumber passed as parameters
        try {
            // Connect to database
            connect = DriverManager.getConnection("jdbc:mariadb://classdb2.csc.ncsu.edu/gjohnso", "gjohnso", "proj540");
            // Create Statement object
            stment = connect.createStatement();
            // Create string for query
            String delete = String.format("DELETE FROM NurseAssignedWard WHERE staff_id = %d AND ward_number = %d", staffID, wardNumber);
            // Execute update
            stment.executeUpdate(delete);
            // Print success message
            System.out.printf("[WHMS] Assignment of nurse %d to ward %d has been removed.\n", staffID, wardNumber);
        // Catch sql exception
        } catch (SQLException ex) {
            System.out.println("Invalid action.  Returning to Ward menu.");
        // Close Statement and Connection no matter what
        } finally {

            // Close statement if not null
            if (stment != null) {
                try {
                    stment.close();
                } catch (SQLException e) {
                    throw new RuntimeException();
                }
            }
            // Close connection if not null
            if (connect != null) {
                try {
                    connect.close();
                } catch (SQLException e) {
                    throw new RuntimeException();
                }
            }
        }
    }
}
