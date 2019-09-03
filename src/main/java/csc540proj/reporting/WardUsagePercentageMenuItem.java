package csc540proj.reporting;

import csc540proj.utility.CommandLine;
import csc540proj.utility.Helper;
import csc540proj.utility.menu.AbstractMenuItem;

import java.sql.Connection;

/**
 * This class provides a menu item that prints out the current
 * usage of a ward as a percentage of beds used.
 */
public class WardUsagePercentageMenuItem extends AbstractMenuItem {

    /**
     * This constructor initializes this menu item with a title.
     */
    public WardUsagePercentageMenuItem() {
        super("Ward-Usage Percentage");
    }

    /**
     * This method runs the functionality associated with this menu item.
     */
    @Override
    public void run(CommandLine commandLine, Connection connection) {
        // Submit a query that returns the percentage of beds used in
        // a ward, currently.
        String query = "SELECT ward_number, (capacity - availability) / capacity AS \"usage\" FROM Ward";
        Helper.queryAndHandle(connection, query, resultSet -> {
            // Print out each returned percentage, along with the associated
            // ward number.
            while (resultSet.next()) {
                // Get the ward number and usage percentage.
                int wardNumber = resultSet.getInt("ward_number");
                float usage = resultSet.getFloat("usage");
                // Print out the information, formatting the usage as a percentage.
                commandLine.printf("Ward %2d, Usage: %02.0f%%", wardNumber, usage * 100);
            }
        });
    }
}
