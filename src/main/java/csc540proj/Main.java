package csc540proj;

import csc540proj.devtools.DeveloperTools;
import csc540proj.information.InfoProcessing;
import csc540proj.information.MedicalRecord;
import csc540proj.reporting.Reporting;
import csc540proj.utility.CommandLine;

import java.sql.Connection;
import java.util.Scanner;

/**
 * This class provides the main method for our application.
 */
public class Main {
    /**
     * This is the main method for our application. It is the entry point for our
     * application.
     */
    public static void main(String[] args) {
        // Prompt the user with welcome text so they know they are starting up the
        // WolfHospital Management System.
        System.out.println("**************************************************");
        System.out.println("  Welcome to the WolfHospital Management System");
        System.out.println("**************************************************");
        System.out.println();

        // Run the application loop. If we receive an exception during the application
        // loop, print it out and keep running.
        while (true) {
            try {
                // Print the menu for the user to see.
                printMenu();
                // Get input from the user as to what they want to do.
                String choice = getChoice();
                // Call the corresponding menu item.
                action(choice);
            } catch (Exception e) {
                System.out.println("[WHMS] Note: " + e.getMessage());
            }
        }
    }

    /**
     * This method prints out the main menu for our application.
     * It also prompts the user to input a choice.
     */
    public static void printMenu() {
        System.out.println("**************************************************");
        System.out.println("[WHMS] Menu:");
        System.out.println("[WHMS]");
        System.out.println("[WHMS] 1 - Information Processing");
        System.out.println("[WHMS] 2 - Maintain Medical Records");
        System.out.println("[WHMS] 3 - Generate reporting");
        System.out.println("[WHMS] 4 - Developer tools");
        System.out.println("[WHMS] 5 - Process Check-in");
        System.out.println("[WHMS] 6 - Process Check-out");
        System.out.println("[WHMS] 7 - Check-in Information");
        System.out.println("[WHMS] 8 - Maintaining Billing Account/Record");
        System.out.println("[WHMS]");
        System.out.print("[WHMS] Choice: ");
    }

    /**
     * This method receives input from the user. It will block on stdin until
     * the user issues a newline ('\n').
     */
    public static String getChoice() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    /**
     * This method will call the corresponding action from the provided choice.
     */
    public static void action(String choice) throws Exception {
        // Create a command line abstraction to pass to the actions.
        // This makes testing much easier.
        CommandLine commandLine = new CommandLine("[WHMS] ", System.in, System.out);

        // Create a connection using the ConnectionSingleton. There is only one
        // of these, and the connection gets reused every time throughout the application.
        Connection connection = ConnectionSingleton.getInstance().getConnection();
        if (choice.equals("1")) {
            InfoProcessing IP = new InfoProcessing(commandLine, connection);
            IP.run();
        } else if (choice.equals("3")) {
            Reporting reporting = new Reporting(commandLine, connection);
            reporting.run();
        } else if (choice.equals("8")) {
            BillingAccounts ba = new BillingAccounts();
            ba.printMenu();
            ba.action(getChoice());
        } else if (choice.equals("2")) {
            MedicalRecord MR = new MedicalRecord();

            //MR.printMenu();
//            String MR_choice = getChoice();
//            try {
//                MR.action(MR_choice);
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
            try {
                MR.printMenu();
            } catch (Exception e) {
                throw new RuntimeException();
            }
        } else if (choice.equals("4")) {
            DeveloperTools developerTools = new DeveloperTools(commandLine, connection);
            developerTools.run();
        } else if (choice.equals("5")) {
            Checkin CI = new Checkin(commandLine, connection);
            CI.run();

        } else if (choice.equals("6")) {
            Checkout co = new Checkout(commandLine, connection);
            co.run();
        } else if (choice.equals("7")) {
            PrintCheckIn pc = new PrintCheckIn();
            pc.printMenu();
            pc.action(getChoice());
        }
    }
}
