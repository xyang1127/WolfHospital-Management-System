package csc540proj.devtools;

import csc540proj.utility.CommandLine;
import csc540proj.utility.SQLResourceReader;
import csc540proj.utility.menu.AbstractMenuItem;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class provideds a menu item that resets the data in the database
 * to an initial state.
 */
public class ResetDatabaseMenuItem extends AbstractMenuItem {

    /**
     * This constructor initializes this menu item with a title.
     */
    public ResetDatabaseMenuItem() {
        super("Reset Database");
    }

    /**
     * This method runs the functionality associated with this menu item.
     */
    @Override
    public void run(CommandLine commandLine, Connection connection) {
        // Warn the user that they are going to completely erase everything
        // in the database if they do this.
        commandLine.println("Database name: " + getDatabaseURL(connection));
        commandLine.println("This will completely erase everything in the database");
        commandLine.println("and reset it back to its initial state.");

        // Get the user's confirmation that they want to do this action.
        String choice = commandLine.getInput("Are you sure you want to do this (y/n): ");

        // If the user does not want to perform this action, then quit.
        if (!choice.equals("y")) {
            commandLine.println("Aborting reset");
            return;
        }

        try {
            // Call a helper method to actually do the resetting.
            run0(commandLine, connection);

            // If we get to here, we have succeeded!
            commandLine.println("Success!");
        } catch (Exception e) {
            // If we catch any exception, just print out why we failed and
            // continue running the program.
            commandLine.println("Failure: " + e.getMessage());
        }
    }

    /**
     * This is a helper method that actually runs the SQL scripts needed
     * to reset the database.
     */
    public void run0(final CommandLine commandLine, final Connection connection) throws Exception {
        // Declare a statement so that it can be closed outside of the scope of
        // the try block.
        Statement statement = null;
        try {
            // Create a statement to be used in all of these following operations.
            statement = connection.createStatement();

            // First, drop existing tables.
            commandLine.println("Dropping tables...");
            executeOperations(commandLine, statement, "/drop-all.sql");

            // Next, create tables.
            commandLine.println("Creating tables...");
            executeOperations(commandLine, statement, "/create-all.sql");

            // Finally, populate the tables with sample data.
            commandLine.println("Populating tables...");
            executeOperations(commandLine, statement, "/insert-all-1.sql");
        } finally {
            // Regardless of what happens, always close the statement.
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e0) {
                    throw new RuntimeException(e0);
                }
            }
        }
    }

    /**
     * This method executes the operations from the provided Java resource.
     */
    private void executeOperations(
            final CommandLine commandLine,
            final Statement statement,
            final String resourceName
    ) throws Exception {
        // Get the SQL statements from the Java resource with the provided name.
        // If we cannot get the resource, then throw an Exception.
        String[] sqlStatements = SQLResourceReader.getStatements(resourceName);
        if (sqlStatements == null) {
            throw new Exception("Unknown resource: " + resourceName);
        }

        // For each SQL statement in the Java resource, print it out and then
        // execute it.
        for (final String sqlStatement : sqlStatements) {
            commandLine.println("  " + makeSQLStatementPretty(sqlStatement));
            statement.execute(sqlStatement);
        }
    }

    /**
     * This method takes a SQL statement as a string and returns it in a
     * very readable manner.
     */
    private String makeSQLStatementPretty(final String sqlStatement) {
        String prettySQLStatement = sqlStatement;

        // For starters, trim the whitespace off of the statement.
        prettySQLStatement = prettySQLStatement.trim();

        // If the string has a parenthesis (such as "CREATE TABLE Staff (" or
        // "INSERT INTO Staff (" then only return up to the parenthesis.
        // Otherwise, just return the string (in cases like "DROP TABLE Staff").
        int index = prettySQLStatement.indexOf("(");
        if (index == -1) {
            return prettySQLStatement;
        } else {
            return prettySQLStatement.substring(0, index);
        }
    }

    /**
     * This method tries to get the database URL from the connection.
     * If it fails, it simply returns "???".
     */
    private String getDatabaseURL(final Connection connection) {
        try {
            return connection.getMetaData().getURL();
        } catch (SQLException e) {
            return "???";
        }
    }
}
