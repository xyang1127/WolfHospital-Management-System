package csc540proj.utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This is a utility class that reads a SQL file Java resource and
 * returns the SQL statements contained in the file as an array.
 */
final public class SQLResourceReader {

    /**
     * Hide the constructor since this class is not meant to be
     * instantiated.
     */
    private SQLResourceReader() {
    }

    /**
     * This method reads a SQL file Java resource and returns the
     * SQL statements contained in the file as an array. If the resource
     * is unable to be found, then null is returned.
     * <p>
     * It assumes that the SQL statements are properly delimited
     * via a semicolon.
     */
    public static String[] getStatements(String resource) {
        // Get the resource with the provided name. If we can't find it, then
        // return unll.
        InputStream resourceStream = SQLResourceReader.class.getResourceAsStream(resource);
        if (resourceStream == null) {
            return null;
        }

        // Allocate a dynamic list of SQL statements.
        List<String> sqlStatements = new ArrayList<>();
        try {
            // Scanner the resource stream for semicolons.
            Scanner scanner = new Scanner(resourceStream);
            scanner.useDelimiter(";");

            // Everytime you see a semicolon, that means you have
            // read a SQL statement.
            while (scanner.hasNext()) {
                String sqlStatement = scanner.next();

                // As long as the statement is not all newlines, then add it to
                // the sql statements list.
                if (!containsAllNewlines(sqlStatement)) {
                    sqlStatements.add(sqlStatement);
                }
            }
        } finally {
            // Regardless of what happens, make sure to close the resource
            // stream. If you can't close it, then throw a RuntimeException.
            try {
                resourceStream.close();
            } catch (IOException ioe) {
                throw new RuntimeException(ioe);
            }
        }

        return sqlStatements.toArray(new String[0]);
    }

    private static boolean containsAllNewlines(String sqlStatement) {
        return sqlStatement.trim().length() == 0;
    }
}
