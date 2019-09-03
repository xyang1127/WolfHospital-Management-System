package csc540proj;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This is a helper class for accessing a connection used by this application.
 * This class follows the singleton pattern so that the connection is only created once.
 * We do not have to worry about thread safety here because our application is
 * single-threaded.
 */
public class ConnectionSingleton {

    // This is the static instance of this class.
    private static ConnectionSingleton instance;

    // This instance field is the connection that will be used by the caller.
    private final Connection connection;

    /**
     * This is the constructor for this class. It will create a connection, or
     * fail and throw a RuntimeException.
     */
    public ConnectionSingleton() {
        try {
            final String user = "gjohnso";
            final String password = "proj540";
            final String url = String.format("jdbc:mariadb://classdb2.csc.ncsu.edu/%s", user);
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * This method returns the singleton connection instance.
     */
    public static ConnectionSingleton getInstance() {
        // If the instance has not been initialized yet, then initialize it
        // and return it.
        // Note that this is not thread-safe! But it shouldn't matter because
        // our application is single-threaded.
        if (instance == null) {
            instance = new ConnectionSingleton();
        }
        return instance;
    }

    /**
     * This method returns the connection associated with this singleton.
     */
    public Connection getConnection() {
        return connection;
    }
}
