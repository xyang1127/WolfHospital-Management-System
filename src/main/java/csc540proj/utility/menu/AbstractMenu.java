package csc540proj.utility.menu;

import csc540proj.utility.CommandLine;

import java.sql.Connection;

/**
 * This class represents a menu that can call out to menu items.
 */
public class AbstractMenu {
    // This field is a command line abstraction that helps with testing.
    private final CommandLine commandLine;

    // This field is a connection to the database.
    private final Connection connection;

    // This is the title of the menu.
    private final String title;

    // These are the items associated with this menu.
    private final AbstractMenuItem[] menuItems;

    /**
     * This constructor initializes the command line and database connections.
     */
    public AbstractMenu(
            final CommandLine commandLine,
            final Connection connection,
            final String title,
            final AbstractMenuItem... menuItems
    ) {
        this.commandLine = commandLine;
        this.connection = connection;
        this.title = title;
        this.menuItems = menuItems;
    }

    public void run() {
        // Print out a nicely formatted list of the menu items for
        // the reporting task.
        commandLine.printBreak();
        commandLine.printf("%s:", title);
        commandLine.println("");
        for (int i = 1; i <= menuItems.length; i++) {
            commandLine.printf("%d - %s", i, menuItems[i - 1].getTitle());
        }
        commandLine.println("");
        commandLine.println("Enter - Return to previous menu");
        commandLine.println("");

        // Get the user's menu choice. If they press enter, return
        // to the previous menu.
        String choice = commandLine.getInput("Choice: ");
        if (choice.length() == 0) {
            return;
        }

        // Parse an integer out of the provided user input.
        // If the input is invalid, tell the user.
        int choiceInt;
        try {
            choiceInt = Integer.parseInt(choice) - 1;
            menuItems[choiceInt].run(commandLine, connection);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            commandLine.println("Unknown menu item for choice: " + choice);
        }
    }
}
