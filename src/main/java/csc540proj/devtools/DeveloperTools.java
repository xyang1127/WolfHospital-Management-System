package csc540proj.devtools;

import csc540proj.utility.CommandLine;
import csc540proj.utility.menu.AbstractMenu;

import java.sql.Connection;

/**
 * This menu provides developer utilities like resetting the database.
 */
public class DeveloperTools extends AbstractMenu {
    /**
     * This constructor initializes the command line, database connections, title,
     * and menu items via the superclass, AbstractMenu.
     */
    public DeveloperTools(final CommandLine commandLine, final Connection connection) {
        super(
                commandLine,
                connection,
                "Developer Tools Menu",
                new ResetDatabaseMenuItem()
        );
    }
}
