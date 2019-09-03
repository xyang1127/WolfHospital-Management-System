package csc540proj.reporting;

import csc540proj.utility.CommandLine;
import csc540proj.utility.DateValidator;
import csc540proj.utility.Helper;
import csc540proj.utility.menu.AbstractMenuItem;
import javafx.util.Pair;

import java.sql.Connection;

/**
 * This class provides a menu item that prints out the number of
 * patients per month.
 */
public class PatientsPerMonthMenuItem extends AbstractMenuItem {

    /**
     * This constructor initializes this menu item with a title.
     */
    public PatientsPerMonthMenuItem() {
        super("Number of Patients per Month");
    }

    /**
     * This method runs the functionality associated with this menu item.
     */
    @Override
    public void run(CommandLine commandLine, Connection connection) {
        // Get the year from the user.
        // If the year is invalid, then print that the year is invalid.
        String year = getYearChoice(commandLine);
        if (year == null) {
            commandLine.println("Year format is invalid");
            return;
        }

        // Get the month from the user. As a part of this process, get
        // the last day of the month as well so we know how to format our
        // date range.
        // If the month is invalid, then print that the month is invalid.
        Pair<String, String> monthAndLastDay = getMonthChoice(commandLine);
        String month = monthAndLastDay.getKey();
        String lastDay = monthAndLastDay.getValue();
        if (month == null) {
            commandLine.println("Month format is invalid");
            return;
        }

        // Format the date range for the SQL query.
        String fromDate = String.format("%s/%s/01", year, month);
        String toDate = String.format("%s/%s/%s", year, month, lastDay);

        // Submit a SQL query that checks how many patients have billing accounts
        // in the provided date range.
        String query = String.format(
                "SELECT COUNT(patient_id) as patient_count " +
                        "FROM MedicalRecord " +
                        "WHERE record_type='checkin'" +
                        "      AND ((start_date < '%s' AND (end_date IS NULL OR end_date >= '%s')) " +
                        "           OR (start_date >= '%s' AND start_date <= '%s'))",
                fromDate, fromDate, fromDate, toDate);
        Helper.queryAndHandle(connection, query, resultSet -> {
            // Print out the patient count that was returned from the query.
            while (resultSet.next()) {
                int patientCount = resultSet.getInt("patient_count");
                commandLine.println("Patient count: " + patientCount);
            }
        });
    }

    /**
     * This method prompts the user for a year and then validates that
     * it is formatted correct.
     */
    private String getYearChoice(final CommandLine commandLine) {
        // Get the year from the command line.
        String year = commandLine.getInput("Enter year (YYYY): ");
        // Call a helper method to validate that this year is formatted
        // correctly. If the year is not formatted correctly, the return null.
        return (DateValidator.isYearValid(year) ? year : null);
    }

    /**
     * This method prompts the user for a month and then validates that
     * it is formatted correct.
     * In addition, it returns the last day of the month. This will help
     * callers who want to format a date range of the month.
     */
    private Pair<String, String> getMonthChoice(final CommandLine commandLine) {
        // Get the month from the command line.
        String month = commandLine.getInput("Enter month (MM): ");
        // Call a helper method to validate that this month is formatted
        // correctly. Also, store the returned last day of the month to
        // return to the caller of this method.
        String lastDay = DateValidator.isMonthValid(month);
        // If the month is not formatted correctly, then return null.
        return (lastDay != null ? new Pair<>(month, lastDay) : new Pair<>(null, null));
    }


}
