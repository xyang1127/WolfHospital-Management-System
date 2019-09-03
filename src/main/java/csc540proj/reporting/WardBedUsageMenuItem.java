package csc540proj.reporting;

import csc540proj.utility.CommandLine;
import csc540proj.utility.Helper;
import csc540proj.utility.menu.AbstractMenuItem;

import java.sql.Connection;

/**
 * This class provides a menu item that prints out the current
 * usage of beds in each ward.
 */
public class WardBedUsageMenuItem extends AbstractMenuItem {

    /**
     * This constructor initializes this menu item with a title.
     */
    public WardBedUsageMenuItem() {
        super("Current Ward/Bed Usage");
    }

    /**
     * This method runs the functionality associated with this menu item.
     */
    @Override
    public void run(CommandLine commandLine, Connection connection) {
        // Submit a query that finds the number of beds available in
        // each ward.
        String query = "SELECT ward_number, bed_number, availability FROM Bed ORDER BY ward_number";
        Helper.queryAndHandle(connection, query, resultSet -> {
            // For each result, print out whether each bed is used.
            while (resultSet.next()) {
                // Get the ward, bed, and availability associated with this result.
                int wardNumber = resultSet.getInt("ward_number");
                int bedNumber = resultSet.getInt("bed_number");
                boolean available = resultSet.getBoolean("availability");
                // Print out the information.
                commandLine.printf("Ward %2d, Bed %2d, Available: %s", wardNumber, bedNumber, (available ? "YES" : "NO"));
            }

        });
    }
}
