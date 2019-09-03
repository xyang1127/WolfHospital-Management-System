package csc540proj.utility;

import java.sql.*;
import java.util.Scanner;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is a class with utility helper methods. It is not meant
 * to be instantiated.
 */
final public class Helper {

    /**
     * Hide the constructor so no one can make instances of this static class.
     */
    private Helper() {
    }

    /**
     * This method simply gets input from the user and returns it as a String.
     */
    public static String getChoice() {
        // Instantiate a scanner object that reads from stdin.
        Scanner scanner = new Scanner(System.in);
        // Get the input that the user types in before they press enter (newline).
        return scanner.nextLine();
    }

    /**
     * This method is an overloaded version of #getPatient(Connection, Supplier).
     * It uses the default method of getting command line input.
     * Please see #getPatient(Connection, Supplier) for more details.
     */
    public static int getPatient(Connection connection, CommandLine commandLine) {
        return getPatient(connection, commandLine, Helper::getChoice);
    }

    /**
     * This method finds a patient by first and last name.
     * <p>
     * It prompts the user for a first and last name of a patient and then
     * displays a potential list of patient IDs to chose from.
     */
    public static int getPatient(Connection connection, CommandLine commandLine, Supplier<String> getChoice) {

        // Prompt the user for the first and last name of the patient.
        commandLine.print("Enter Patient First Name: ");
        String firstName = getChoice.get();
        commandLine.print("Enter Patient Last Name: ");
        String lastName = getChoice.get();

        commandLine.println("Finding Patients...");
        commandLine.println("");

        String query = "SELECT * FROM Patient WHERE first_name = '" + firstName + "' AND last_name = '" + lastName + "'";

        queryAndHandle(connection, query, resultSet -> {
            // Get the number of columns in the returned data so that we can print
            // out each column name with the associated data.
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int cNum = rsmd.getColumnCount();
            while (resultSet.next()) {
                // For each column name in each returned patient, print out
                // each column name with the associated data.
                for (int i = 1; i <= cNum; i++) {
                    String cVal = resultSet.getString(i);
                    commandLine.println(rsmd.getColumnName(i) + ": " + cVal);
                }
                commandLine.println("");
            }
            commandLine.print("Confirm ID of Patient: ");
        });

        // After presenting the patients and their IDs to the user, prompt
        // the user for a patient ID. If the user gives a bad patient ID, then
        // print out the error and return -1.
        String patientId = getChoice.get();
        try {
            return (Integer.parseInt(patientId));
        } catch (NumberFormatException e) {
            commandLine.println("Invalid patient ID: " + patientId);
            return -1;
        }
    }

    /**
     * This method is an overloaded version of #getStaff(Connection, Supplier).
     * It uses the default method of getting command line input.
     * Please see #getStaff(Connection, Supplier) for more details.
     */
    public static int getStaff(Connection connection, CommandLine commandLine) {
        return getStaff(connection, commandLine, null, Helper::getChoice);
    }

    /**
     * This method finds a staff member by first and last name.
     * <p>
     * It prompts the user for a first and last name of a staff member and then
     * displays a potential list of staff IDs to chose from.
     * <p>
     * An optional jobTitle parameter can be passed in to filter the staff
     * considered staff members to only those with the provided jobTitle. The
     * jobTitle can be null if all staff members are to be considered.
     */
    public static int getStaff(Connection connection, CommandLine commandLine, String jobTitle, Supplier<String> getChoice) {
        // Prompt the user for the first and last name of the staff member.
        commandLine.print("Enter Staff First Name: ");
        String firstName = getChoice.get();
        commandLine.print("Enter Staff Last Name: ");
        String lastName = getChoice.get();

        commandLine.println("Finding Staff...");
        commandLine.println("");


        // Create the query to find staff members with the provided first
        // and last name. If a jobTitle argument has been provided, then also
        // limit the staff members considered to only those with the provide
        // jobTitle.
        String query = "SELECT * FROM Staff WHERE first_name = '" + firstName + "' AND last_name = '" + lastName + "'";
        if (jobTitle != null) {
            query = query.concat(" AND job_title = '" + jobTitle + "'");
        }

        // Execute the query.
        queryAndHandle(connection, query, resultSet -> {
            // Get the number of columns in the returned data so that we can print
            // out each column name with the associated data.
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int cNum = rsmd.getColumnCount();
            while (resultSet.next()) {
                // For each column name in each returned staff member, print out
                // each column name with the associated data.
                for (int i = 1; i <= cNum; i++) {
                    String cVal = resultSet.getString(i);
                    commandLine.println(rsmd.getColumnName(i) + ": " + cVal);
                }
                commandLine.println("");
            }
            commandLine.print("Select ID of Staff: ");
        });

        // After presenting the staff members and their IDs to the user, prompt
        // the user for a staff ID. If the user gives a bad staff ID, then
        // print out the error and return -1.
        String staffId = getChoice.get();
        try {
            return (Integer.parseInt(staffId));
        } catch (NumberFormatException e) {
            commandLine.println("Invalid staff ID: " + staffId);
            return -1;
        }

    }

    /**
     * This method will prompt the user for a start date.
     */
    public static String getStartDate(CommandLine commandLine) {
        // Declare variables that will be used in this method.
        String startDate, choice;
        String regex;
        Pattern pattern;
        Matcher matcher;

        // Prompt the user for the start date, telling them the expected format of the date.
        commandLine.print("Enter start date [yyyy-mm-dd] or press ENTER to use today's date: ");
        choice = getChoice();

        // Create a regex that should match the format of the date.
        regex = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(choice);

        // If the user entered nothing, use today's date.
        // Otherwise, see if the entered start date matches.
        // If the provided start date does NOT match the correct format, then run the function again.
        // If the provided start date DOES match the correct format, then return the start date.
        if (choice.equals(""))
            startDate = java.time.LocalDate.now().toString();
        else if (!matcher.matches()) {
            commandLine.println("NOT valid Check-in Date input.");
            startDate = getStartDate(commandLine);
        } else startDate = choice;
        return startDate;
    }

    /**
     * This interface is used by the below #queryAndHandle method to
     * handle the results of SQL query.
     */
    public static interface ResultSetHandler {
        public void handle(ResultSet resultSet) throws SQLException;
    }

    /**
     * This method will run a query and pass the results to a ResultSetHandler.
     * The provided connection will be used to run the provided query.
     */
    public static void queryAndHandle(final Connection connection, String query, ResultSetHandler handler) {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            // Try to create a statement from the provided connection and
            // execute the provided query. Then, pass the returned SQL
            // ResultSet to the provided handler.
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            handler.handle(resultSet);
        } catch (SQLException exception) {
            // If we fail to create a statement, execute a query, or handle
            // the returned ResultSet, then throw a RuntimeException, which
            // essentially stops the program.
            throw new RuntimeException(exception);
        } finally {
            // Whatever happens, we always try to close the created ResultSet.
            // If we fail to close the ResultSet, we throw a RuntimeException,
            // which essentially stops the program.
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException exception) {
                    throw new RuntimeException(exception);
                }
            }

            // Whatever happens, we always try to close the created Statement.
            // If we fail to close the Statement, we throw a RuntimeException,
            // which essentially stops the program.
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException exception) {
                    throw new RuntimeException(exception);
                }
            }
        }
    }

    /**
     * This is a helper method to make it easy for callers to run INSERT/UPDATE/DELETE queries
     * and not have to provide the overhead for handling SQLException's.
     */
    public static void insertAndHandle(final Connection connection, String sql) {
        Statement statement = null;
        try {
            // Try to create a statement from the provided connection and
            // execute the provided query. Then, pass the returned SQL
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException exception) {
            // If we fail to create a statement or execute an update,
            // then throw a RuntimeException, which essentially stops the program.
            System.out.println("There was an error when trying to alter the database. Returning to menu.");
        }

        // Whatever happens, we always try to close the created Statement.
        // If we fail to close the Statement, we throw a RuntimeException,
        // which essentially stops the program.
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * This method gets a date string provided by the user and ensures that it
     * follows a correct date format.
     */
    public static String getDate(final CommandLine commandLine, final String dateType) {
        // Create a regex for the desired date format.
        final String regex = "[0-9]{4}/[0-9]{2}/[0-9]{2}";
        final Pattern pattern = Pattern.compile(regex);

        // Continue to ask the user for date strings, until they enter a string
        // that follows the correct format.
        String input;
        do {
            input = commandLine.getInput(String.format("Enter %s (YYYY/MM/DD): ", dateType));
            if (!pattern.matcher(input).matches()) {
                commandLine.println("Invalid date format");
            } else {
                break;
            }
        } while (true);

        return input;
    }

    /**
     * This method prompts the user for a user input and compares the user input against
     * an expected regex. This function will loop until the user enters input that matches
     * the expected regex.
     */
    public static String confirmInput(final CommandLine commandLine, final String message, final String regex) {
        // Compile the provided regex.
        final Pattern pattern = Pattern.compile(regex);

        // Loop until the user enters input that matches the expected regex.
        String input;
        do {
            input = commandLine.getInput(message);
            if (!pattern.matcher(input).matches()) {
                commandLine.println("Invalid format");
            } else {
                break;
            }
        } while (true);

        return input;
    }
}
