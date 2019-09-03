package csc540proj.information;

import csc540proj.utility.CommandLine;
import csc540proj.utility.Helper;

import java.sql.Connection;

/**
 * This class is a menu that provides access to the individual
 * information processing operations.
 */
public class InfoProcessing {

    private final CommandLine commandLine;
    private final Connection connection;

    /**
     * This constructor initializes the connection and command line
     */
    public InfoProcessing(final CommandLine commandLine, final Connection connection) {
        this.commandLine = commandLine;
        this.connection = connection;
    }

    /**
     * This method prints the Information Processing menu
     */
    private void printMenu() {
        commandLine.println("**************************************************");
        commandLine.println("Information Processing Menu:");
        commandLine.println("");
        commandLine.println("1 - Patient Information");
        commandLine.println("2 - Staff Information");
        commandLine.println("3 - Ward Information");
        commandLine.println("4 - Reservations");
        commandLine.println("");
        commandLine.println("Enter - Return to previous menu");
        commandLine.println("");

        commandLine.print("Choice: ");
    }

    /**
     * This method gets the choice of the user and calls the corresponding methods
     */
    private void action(String choice) throws Exception {
        switch (choice) {
            case "1":
                PatientInfo PI = new PatientInfo(commandLine, connection);
                PI.run();
                break;
            case "2":
                StaffInfo SI = new StaffInfo(commandLine, connection);
                SI.run();
                break;
            case "3":
                WardInfo WI = new WardInfo(commandLine, connection);
                WI.run();
                break;
            case "4":
                Reservations Res = new Reservations(commandLine, connection);
                Res.run();
                break;
            default:
                commandLine.println("Returning to main menu.");

        }
    }

    /**
     * This method is called to run the functionality of the Information Processing menu system
     */
    public void run() throws Exception {
        printMenu();
        action(Helper.getChoice());
    }
}
