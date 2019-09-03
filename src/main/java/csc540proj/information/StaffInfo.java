package csc540proj.information;

import csc540proj.utility.CommandLine;
import csc540proj.utility.Helper;

import java.sql.Connection;
import java.sql.ResultSetMetaData;

public class StaffInfo {

    private final CommandLine commandLine;
    private final Connection connection;

    public StaffInfo(final CommandLine commandLine, final Connection connection) {
        this.commandLine = commandLine;
        this.connection = connection;
    }

    public void printMenu() {
        commandLine.println("**************************************************");
        commandLine.println("Staff Information Menu:");
        commandLine.println("");
        commandLine.println("1 - Enter New Staff");
        commandLine.println("2 - Update Existing Staff");
        commandLine.println("3 - Remove Staff");
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
    public void action(String choice) throws Exception {
        switch (choice) {
            case "1":
                enterStaff();
                run();
                break;
            case "2":
                updateStaff();
                run();
                break;
            case "3":
                deleteStaff();
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
     * Enters a new staff memeber with specified inputs
     * Auto assigns staff Id id not provided
     */
    private void enterStaff() {
        String firstName, lastName, gender, dob, jobTitle, profTitle = "NULL", department, phoneNumber = "NULL", streetAddr, city, state, zip;
        String StaffID;

        //  receive information about staff to enter.

        StaffID = Helper.confirmInput(commandLine, "Enter Staff ID (leave blank to auto assign): ", "^(?:[0-9]{3})?$");
        firstName = Helper.confirmInput(commandLine, "Enter First Name: ", "^(?!\\s*$).+");
        lastName = Helper.confirmInput(commandLine, "Enter Last Name: ", "^(?!\\s*$).+");
        jobTitle = Helper.confirmInput(commandLine, "Enter job title: ", "^(?!\\s*$).+");
        gender = Helper.confirmInput(commandLine, "Enter gender ('M' for male. 'F' for female): ", "[MF]");
        dob = Helper.getDate(commandLine, "DOB");
        profTitle = Helper.confirmInput(commandLine, "Enter professional title (leave blank if not available): ", "^(?:(?!\\s*$).+)?$");
        department = Helper.confirmInput(commandLine, "Enter Department: ", "^(?!\\s*$).+");
        phoneNumber = Helper.confirmInput(commandLine, "Enter Phone Number (leave blank if not available): ", "^(?:(?!\\s*$).+)?$");
        streetAddr = Helper.confirmInput(commandLine, "Enter Street Address: ", "^(?!\\s*$).+");
        city = Helper.confirmInput(commandLine, "Enter City: ", "^(?!\\s*$).+");
        state = Helper.confirmInput(commandLine, "Enter State: ", "[A-Z]{2}");
        zip = Helper.confirmInput(commandLine, "Enter Zip Code: ", "[0-9]{5}");

        commandLine.println("Entering Staff...");

        String sql;
        if (StaffID.equals("")) {
            sql = "INSERT INTO Staff (first_name, last_name, DOB, gender, job_title, phone_number, professional_title, department, street_address, city, state, zip_code)" +
                    "VALUES ('" + firstName + "', '" + lastName + "', '" + dob + "', '" + gender + "', '" + jobTitle + "', '" + phoneNumber + "', '" + profTitle + "', '" + department + "', " +
                    "'" + streetAddr + "', '" + city + "', '" + state + "', '" + zip + "')";
        } else {
            sql = "INSERT INTO Staff (staff_id, first_name, last_name, DOB, gender, job_title, phone_number, professional_title, department, street_address, city, state, zip_code)" +
                    "VALUES (" + StaffID + ", '" + firstName + "', '" + lastName + "', '" + dob + "', '" + gender + "', '" + jobTitle + "', '" + phoneNumber + "', '" + profTitle + "', '" + department + "'," +
                    "'" + streetAddr + "', '" + city + "', '" + state + "', '" + zip + "')";
        }
        //  inserts information into database depending on whether or not an ID was given
        Helper.insertAndHandle(connection, sql);
        //  if the staff member is a doctor, insert a new entry into the "doctor" table.
        if (jobTitle.equals("Doctor") || jobTitle.equals("doctor")) {
            Helper.queryAndHandle(connection, "SELECT staff_id FROM Staff WHERE first_name = '" + firstName + "' AND last_name = '" + lastName + "' AND DOB = '" + dob + "'", resultSet -> {
                String specialty = "NULL";
                resultSet.next();
                specialty = Helper.confirmInput(commandLine, "Enter specialty (leave blank if not available): ", "^(?!\\s*$).+");
                String findID = "INSERT INTO Doctor (staff_id, specialty) VALUES (" + resultSet.getInt(1) + ", '" + specialty + "')";
                Helper.insertAndHandle(connection, findID);
            });

        }
        commandLine.println("Done.");
    }

    /**
     * Delete staff member from database
     * CASCADE deletes all associated entries where a staffID is supplied as a foreign key
     */
    private void deleteStaff() {
        int staffID = Helper.getStaff(connection, commandLine);
        String query = "SELECT * FROM Staff WHERE staff_id = " + staffID + "";
        //  pass query and handle result set
        Helper.queryAndHandle(connection, query, resultSet -> {
            if (resultSet.next()) {
                //  if staff member exists with supplied ID, delete.
                String sql = "DELETE FROM Staff WHERE staff_id = " + staffID + "";
                Helper.insertAndHandle(connection, sql);
                commandLine.println("Staff Deleted.");
            } else
                commandLine.println("ID does not match a current staff.");
        });
    }

    /**
     * Update staff member
     * staff member will be asked for by ID
     * supplied ID from getStaff function must be valid or return to menu
     */
    private void updateStaff() {
        int staffID = Helper.getStaff(connection, commandLine);
        String query = "";
        //  decide whether or not to join doctor with staff table
        String choice = Helper.confirmInput(commandLine, "Is this staff member a doctor? (y, n)", "[y|n]");
        if (choice.equals("y"))
            query = "SELECT * FROM Staff LEFT JOIN Doctor ON Staff.staff_id = Doctor.staff_id WHERE Staff.staff_id = " + staffID + "";
        else if (choice.equals("n"))
            query = "SELECT * FROM Staff Where staff_id = " + staffID + "";

        String repeat = "y";
        //  pass query to get all necessary staff information, handle connection and result set
        while (repeat.equals("y")) {
            Helper.queryAndHandle(connection, query, resultSet -> {
                if (resultSet.next()) {
                    ResultSetMetaData rsmd = resultSet.getMetaData();
                    int cNum = rsmd.getColumnCount();
                    int i;
                    //  print all attributes with associated column numbers
                    for (i = 1; i <= cNum; i++)
                        commandLine.println("[" + i + "] " + rsmd.getColumnName(i) + ": " + resultSet.getString(i));

                    int column = Integer.parseInt(Helper.confirmInput(commandLine, "Select the column number you wish to edit (0 to cancel): ", "^[0-9][0-9]{1,2}$|^\\d$"));
                    //  we do not allow the editing of a staff ID
                    if (column == 1) {
                        commandLine.println("You can not edit Staff ID");
                        return;
                    } else if (column == 0) {
                        commandLine.println("Canceled update.(Enter to return to previous menu");
                        return;
                    }

                    commandLine.println("Enter updated " + rsmd.getColumnName(column) + ": ");
                    String ud = Helper.getChoice();
                    String sql;
                    //  if the column number is a valid "staff" column, update staff table
                    if (1 < column && column <= 14) {
                        sql = "UPDATE Staff SET " + rsmd.getColumnName(column) + " = '" + ud + "' WHERE staff_id = '" + staffID + "'";
                        Helper.insertAndHandle(connection, sql);
                        commandLine.println("Staff Updated.");
                    }
                    //  if column number is "specialty" update Doctor table
                    else if (column == 15) {
                        sql = "UPDATE Doctor SET " + rsmd.getColumnName(column) + " = '" + ud + "' WHERE staff_id = '" + staffID + "'";
                        Helper.insertAndHandle(connection, sql);
                        commandLine.println("Staff Updated.");
                    }

                    commandLine.println("Continue updating staff? (y/n): ");
                } else {
                    commandLine.println("Id does not match a current staff member.(Enter to return to previous menu)");
                }
            });
            repeat = Helper.getChoice();
        }
    }
}