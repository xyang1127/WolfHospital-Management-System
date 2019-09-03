package csc540proj.reporting;

import csc540proj.devtools.ResetDatabaseMenuItem;
import csc540proj.utility.CommandLine;
import csc540proj.utility.menu.AbstractMenuItem;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestUtil {
    private static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection("jdbc:mariadb://classdb2.csc.ncsu.edu/agkeesle", "agkeesle", "proj540");
        } catch (SQLException e) {
            System.err.println("Failed to create connection: " + e);
            System.exit(99);
        }
    }

    public static Connection getConnection() throws Exception {
        return connection;
    }

    public static void resetDatabase() throws Exception {
        new ResetDatabaseMenuItem().run0(
                new CommandLine(
                        "",
                        new ByteArrayInputStream(new byte[0]),
                        System.out
                ),
                getConnection()
        );
    }

    public static <T extends AbstractMenuItem> void assertMenuItemBehavior(
            final String inputString,
            final String expectedOutput,
            final Class<T> clazz
    ) throws Exception {
        assertMenuItemBehavior(inputString, expectedOutput, clazz, false);
    }

    public static <T extends AbstractMenuItem> void assertMenuItemBehavior(
            final String inputString,
            final String expectedOutput,
            final Class<T> clazz,
            final boolean endsWith
    ) throws Exception {
        ByteArrayInputStream input = new ByteArrayInputStream(inputString.getBytes());
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        CommandLine commandLine = new CommandLine("", input, output);
        Connection connection = TestUtil.getConnection();

        T clazzInstance = clazz.newInstance();
        clazzInstance.run(commandLine, connection);

        String actualOutput = output.toString();
        if (endsWith) {
            assertTrue(actualOutput, actualOutput.toString().endsWith(expectedOutput));
        } else {
            assertEquals(expectedOutput, actualOutput);
        }
    }

    public static class MultiOutputStream extends OutputStream {
        private final List<OutputStream> outputStreams = new ArrayList<>();

        public MultiOutputStream addOutputStream(final OutputStream outputStream) {
            outputStreams.add(outputStream);
            return this;
        }

        @Override
        public void write(int b) throws IOException {
            for (final OutputStream outputStream : outputStreams) {
                outputStream.write(b);
            }
        }

        @Override
        public void close() throws IOException {
            for (final OutputStream outputStream : outputStreams) {
                outputStream.close();
            }
        }

        @Override
        public void flush() throws IOException {
            for (final OutputStream outputStream : outputStreams) {
                outputStream.flush();
            }
        }

        @Override
        public String toString() {
            return outputStreams.isEmpty() ? "" : outputStreams.get(0).toString();
        }
    }
}
