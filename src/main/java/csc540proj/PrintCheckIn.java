package csc540proj;

import java.sql.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PrintCheckIn {

    private static final String jdbcURL = "jdbc:mariadb://classdb2.csc.ncsu.edu/gjohnso";
    private static final String username = "gjohnso";
    private static final String password = "proj540";

    /**
     * This method prints out all the check-in information
     */
    public void printInfor() {
        try {
            Connection con = DriverManager.getConnection(jdbcURL, username, password);
            Statement statement = con.createStatement();

            String query = "SELECT patient_id, bed_number, ward_number, start_date, end_date FROM PatientAssignedBed";

            ResultSet resultSet = statement.executeQuery(query);

            int i = 0;

            System.out.println("[WHMS]");
            System.out.println("[WHMS] Here is all the Check-in Information: ");
            while (resultSet.next()) {
                i++;
                int PID = resultSet.getInt("patient_id");
                int BN = resultSet.getInt("bed_number");
                int WN = resultSet.getInt("ward_number");
                String SD = resultSet.getString("start_date");
                String ED = resultSet.getString("end_date");

                System.out.println("[WHMS]");
                System.out.println("[WHMS] Patient ID: " + PID);
                System.out.println("[WHMS] Ward Number: " + WN);
                System.out.println("[WHMS] Bed Number: " + BN);
                System.out.println("[WHMS] Start Date: " + SD);
                System.out.println("[WHMS] End Date: " + ED);
            }
            System.out.println("[WHMS]");
            System.out.println("[WHMS] Summary:");
            System.out.println("[WHMS] total " + i + " Check-in Information are listed");

            resultSet.close();
            statement.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("[WHMS] Something went wrong when printing check-in information");
            throw new RuntimeException(e);
        }
    }

    /**
     * This method prints out all the check-in information for a specific patient
     */
    public void AprintInfor() {
        try {
            Connection con = DriverManager.getConnection(jdbcURL, username, password);
            Statement statement = con.createStatement();
            ResultSet resultSet = null;

            String regex;
            Pattern pattern;
            Matcher matcher;
            // check whether the patientID is valid
            System.out.print("[WHMS] Enter Patient ID: ");
            String PID = getChoice();
            regex = ".*[0-9].*";
            pattern = Pattern.compile(regex);
            matcher = pattern.matcher(PID);
            while (!matcher.matches()) {
                System.out.print("[WHMS] NOT valid Patient ID input, please enter again: ");
                PID = getChoice();
                matcher = pattern.matcher(PID);
            }

            resultSet = statement.executeQuery("SELECT * FROM Patient WHERE patient_id=" + PID);
            if (!resultSet.next()) {
                System.out.println("[WHMS] The Patient ID specified above doesn't exist in the database");
                return;
            }

            String query = "SELECT bed_number, ward_number, start_date, end_date FROM PatientAssignedBed" +
                    " WHERE patient_id=" + PID;

            resultSet = statement.executeQuery(query);

            int i = 0;

            System.out.println("[WHMS]");
            System.out.println("[WHMS] Here is all the Check-in Information for Patient : " + PID);
            while (resultSet.next()) {
                i++;
                int BN = resultSet.getInt("bed_number");
                int WN = resultSet.getInt("ward_number");
                String SD = resultSet.getString("start_date");
                String ED = resultSet.getString("end_date");

                System.out.println("[WHMS]");
                System.out.println("[WHMS] Ward Number: " + WN);
                System.out.println("[WHMS] Bed Number: " + BN);
                System.out.println("[WHMS] Start Date: " + SD);
                System.out.println("[WHMS] End Date: " + ED);
            }
            System.out.println("[WHMS]");
            System.out.println("[WHMS] Summary:");
            System.out.println("[WHMS] total " + i + " Check-in Information are listed");

            resultSet.close();
            statement.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("[WHMS] Something went wrong when printing check-in information");
            throw new RuntimeException(e);
        }
    }

    /**
     * This method prints out the menu for maintaining billing account and billing record
     */
    public void printMenu() {
        System.out.println("**************************************************");
        System.out.println("[WHMS] Check-in Information Menu:");
        System.out.println("[WHMS]");
        System.out.println("[WHMS] 1 - Check-in Information For A Patient");
        System.out.println("[WHMS] 2 - All Check-in Information");
        System.out.println("[WHMS]");
        System.out.println("[WHMS] Enter - Return to previous menu");
        System.out.println("[WHMS]");

        System.out.print("[WHMS] Choice: ");
    }

    /**
     * This method associate each option with their operations
     */
    public void action(String choice) {
        switch (choice) {
            case "1":
                AprintInfor();
                printMenu();
                action(getChoice());
                break;
            case "2":
                printInfor();
                printMenu();
                action(getChoice());
                break;
            default:
                System.out.println("[WHMS] Return to main menu.");
        }
    }

    /**
     * This method gets inputs from the users
     */
    private static String getChoice() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

}
