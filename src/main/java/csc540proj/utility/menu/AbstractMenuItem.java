package csc540proj.utility.menu;

import csc540proj.utility.CommandLine;

import java.sql.Connection;

/**
 * This is an abstract base class that all reporting menu items must derive from.
 * <p>
 * There are two pieces of functionality provided by this class:
 * - A title for the menu item (#getTitle())
 * - The functionality that runs when the menu item is selected
 * (#run(CommandLine, Connection)).
 */
public abstract class AbstractMenuItem {

    // This private field is the name of the menu item.
    private final String title;

    /**
     * This constructor initializes a menu item with a title.
     */
    public AbstractMenuItem(final String title) {
        this.title = title;
    }

    /**
     * This method returns the title for this menu item.
     */
    public String getTitle() {
        return title;
    }

    /**
     * This abstract method will be called to invoke the functionality
     * of this menu item.
     */
    abstract public void run(final CommandLine commandLine, final Connection connection);
}
