package csc540proj;

import java.sql.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * this class deals with the third operation:
 * Maintaining billing accounts. Generate/maintain billing accounts for every visit of every patient.
 * Before generating an account, make sure there is space in the hospital for the patient
 * (in case the patient needs to stay in the hospital).
 */

public class BillingAccounts {

    /**
     * This method prints out the menu for maintaining billing account and billing record
     */
    public void printMenu() {
        System.out.println("**************************************************");
        System.out.println("[WHMS] Billing Account Menu:");
        System.out.println("[WHMS]");
        System.out.println("[WHMS] 1 - Generate Billing Account");
        System.out.println("[WHMS] 2 - Add Billing Record");
        System.out.println("[WHMS] 3 - Update Billing Account");
        System.out.println("[WHMS] 4 - Update Billing Record");
        System.out.println("[WHMS] 5 - Delete Billing Account");
        System.out.println("[WHMS] 6 - Delete Billing Record");
        System.out.println("[WHMS] 7 - List Billing Accounts");
        System.out.println("[WHMS] 8 - List Billing Records");
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
                generateBA();
                printMenu();
                action(getChoice());
                break;
            case "3":
                updateBA();
                printMenu();
                action(getChoice());
                break;
            case "2":
                addBR();
                printMenu();
                action(getChoice());
                break;
            case "4":
                updateBR();
                printMenu();
                action(getChoice());
                break;
            case "6":
                deleteBR();
                printMenu();
                action(getChoice());
                break;
            case "8":
                ListBR();
                printMenu();
                action(getChoice());
                break;
            case "7":
                ListBA();
                printMenu();
                action(getChoice());
                break;
            case "5":
                deleteBA();
                printMenu();
                action(getChoice());
                break;
            default:
                System.out.println("[WHMS] Return to main menu.");
        }
    }

    private static final String jdbcURL = "jdbc:mariadb://classdb2.csc.ncsu.edu/gjohnso";
    private static final String username = "gjohnso";
    private static final String password = "proj540";

    /**
     * This method generates a Billing Account
     */
    private void generateBA() {
        String regex;
        Pattern pattern;
        Matcher matcher;
        // check whether the patientID is valid
        System.out.print("[WHMS] Enter Patient ID: ");
        String patientID = getChoice();
        regex = ".*[0-9].*";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(patientID);
        while (!matcher.matches()) {
            System.out.print("[WHMS] NOT valid Patient ID input, please enter again: ");
            patientID = getChoice();
            matcher = pattern.matcher(patientID);
        }
        // check date validity
        System.out.print("[WHMS] Enter Check-in Date(yyyy-mm-dd): ");
        String checkIn = getChoice();
        regex = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(checkIn);
        while (!matcher.matches()) {
            System.out.print("[WHMS] NOT valid Check-in Date input, please enter Check-in Date again: ");
            checkIn = getChoice();
            matcher = pattern.matcher(checkIn);
        }
        generateBA(Integer.parseInt(patientID), checkIn);
    }

    /**
     * This method generates a billing record
     */
    public void generateBA(int patientID, String checkIn) {
        try {
            // check whether there is room in hospital
            Connection con = DriverManager.getConnection(jdbcURL, username, password);
            Statement statement = con.createStatement();
            // select those wards that are available
            ResultSet res = statement.executeQuery("SELECT ward_number FROM Ward WHERE availability > 0 ");
            if (!res.next()) {
                // if you reach here, it means there is no Ward available
                System.out.println("[WHMS] No wards available, cannot generate billing account now");
                System.out.println("[WHMS] Return to Billing Account Menu");
            } else {
                // There are wards available, so go ahead generate billing account
                // these variables are used for regular expression checks
                String regex;
                Pattern pattern;
                Matcher matcher;
                // check whether this patient ID exists
                res = statement.executeQuery("SELECT * FROM Patient WHERE patient_id=" + patientID);
                if (!res.next()) {
                    System.out.println("[WHMS] The Patient ID specified above doesn't exist in the database");
                    return;
                }
                // check SSN validity
                System.out.print("[WHMS] Enter SSN responsible party (xxx-xx-xxxx): ");
                String ssn = getChoice();
                regex = "[0-9]{3}-[0-9]{2}-[0-9]{4}";
                pattern = Pattern.compile(regex);
                matcher = pattern.matcher(ssn);
                while (!matcher.matches()) {
                    System.out.print("[WHMS] NOT valid SSN input, please enter SSN Responsible Party again (xxx-xx-xxxx): ");
                    ssn = getChoice();
                    matcher = pattern.matcher(ssn);
                }
                // check payment method validity
                System.out.print("[WHMS] Enter Payment Method: ");
                String paymentMethod = getChoice();
                regex = "^(?!\\s*$).+";
                pattern = Pattern.compile(regex);
                matcher = pattern.matcher(paymentMethod);
                while (!matcher.matches()) {
                    System.out.print("[WHMS] Payment Method CANNOT be empty, please enter Payment Method again: ");
                    paymentMethod = getChoice();
                    matcher = pattern.matcher(paymentMethod);
                }
                // check card number validity
                System.out.print("[WHMS] Enter Card Number (leave blank if not available): ");
                String cardNumber = getChoice();
                regex = "[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{4}|^$";
                pattern = Pattern.compile(regex);
                matcher = pattern.matcher(cardNumber);
                while (!matcher.matches()) {
                    System.out.print("[WHMS] NOT valid Card Number input, please enter Card Number again: ");
                    cardNumber = getChoice();
                    matcher = pattern.matcher(cardNumber);
                }
                System.out.print("[WHMS] Enter Insurance Provider (leave blank if not available): ");
                String Insurance = getChoice();
                // billing address cannot be empty
                System.out.print("[WHMS] Enter Billing Street Address: ");
                String streetAddr = getChoice();
                regex = "^(?!\\s*$).+";
                pattern = Pattern.compile(regex);
                matcher = pattern.matcher(streetAddr);
                while (!matcher.matches()) {
                    System.out.print("[WHMS] Billing Street Address CANNOT be empty, please enter again: ");
                    streetAddr = getChoice();
                    matcher = pattern.matcher(streetAddr);
                }
                System.out.print("[WHMS] Enter Billing City: ");
                String city = getChoice();
                regex = "^(?!\\s*$).+";
                pattern = Pattern.compile(regex);
                matcher = pattern.matcher(city);
                while (!matcher.matches()) {
                    System.out.print("[WHMS] Billing City CANNOT be empty, please enter again: ");
                    city = getChoice();
                    matcher = pattern.matcher(city);
                }
                System.out.print("[WHMS] Enter Billing State: ");
                String state = getChoice();
                regex = "^(?!\\s*$).+";
                pattern = Pattern.compile(regex);
                matcher = pattern.matcher(state);
                while (!matcher.matches()) {
                    System.out.print("[WHMS] Billing State CANNOT be empty, please enter again: ");
                    state = getChoice();
                    matcher = pattern.matcher(state);
                }
                // check zip code validity
                System.out.print("[WHMS] Enter Billing Zip Code (xxxxx): ");
                String zip = getChoice();
                regex = "[0-9]{5}";
                pattern = Pattern.compile(regex);
                matcher = pattern.matcher(zip);
                while (!matcher.matches()) {
                    System.out.print("[WHMS] NOT valid Zip Code input, please enter Zip Code again (xxxxx): ");
                    zip = getChoice();
                    matcher = pattern.matcher(zip);
                }

                System.out.println("[WHMS] Entering Patient...");

                // insert the BillingAccount into database
                statement.executeUpdate("INSERT INTO BillingAccount (" +
                        "patient_id, " +
                        "SSN_responsible_party, " +
                        "payment_method, " +
                        "card_number, " +
                        "insurance_provider, " +
                        "billing_street, " +
                        "billing_city, " +
                        "billing_state, " +
                        "billing_zip_code, " +
                        "check_in_date) VALUES (" +
                        patientID + ", '" +
                        ssn + "', '" +
                        paymentMethod + "', '" +
                        cardNumber + "', '" +
                        Insurance + "', '" +
                        streetAddr + "', '" +
                        city + "', '" +
                        state + "', '" +
                        zip + "', '" +
                        checkIn + "'" +
                        ")");

                System.out.println("[WHMS] Billing Account for Patient " + patientID + " is successfully generated");
            }

            res.close();
            statement.close();
            con.close();

        } catch (SQLIntegrityConstraintViolationException ie) {
            System.out.println("[WHMS] the Patient ID specified above doesn't exist, please try to enter again");
        } catch (SQLException e) {
            System.out.println("[WHMS] Fails to update Billing Account, please try to enter again!");
        }

    }

    /**
     * This method updates a Billing Account.
     * If update fails, it will roll back to the original version
     */
    private void updateBA() {

        Connection con = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {

            con = DriverManager.getConnection(jdbcURL, username, password);
            statement = con.createStatement();
            con.setAutoCommit(false);

            String regex;
            Pattern pattern;
            Matcher matcher;

            // first specify which billing record to update
            System.out.print("[WHMS] Enter Account Number to Update: ");
            String accountNum = getChoice();
            regex = ".*[0-9].*";
            pattern = Pattern.compile(regex);
            matcher = pattern.matcher(accountNum);
            while (!matcher.matches()) {
                System.out.print("[WHMS] NOT valid Account Number input, please enter again: ");
                accountNum = getChoice();
                matcher = pattern.matcher(accountNum);
            }

            // check whether this account exists
            resultSet = statement.executeQuery("SELECT * FROM BillingAccount WHERE account_number=" + accountNum);
            if (!resultSet.next()) {
                System.out.println("[WHMS] The Account Number specified above doesn't exist in the database");
                return;
            }

            System.out.println("[WHMS] Following are the information that you can update.");
            System.out.println("[WHMS] ------------------------");
            System.out.println("[WHMS] patient_id");
            System.out.println("[WHMS] SSN_responsible_party");
            System.out.println("[WHMS] payment_method");
            System.out.println("[WHMS] card_number");
            System.out.println("[WHMS] insurance_provider");
            System.out.println("[WHMS] billing_street");
            System.out.println("[WHMS] billing_city");
            System.out.println("[WHMS] billing_state");
            System.out.println("[WHMS] billing_zip_code");
            System.out.println("[WHMS] check_in_date");
            System.out.println("[WHMS] ------------------------");

            boolean iter = true;

            while (iter) {
                System.out.print("[WHMS] Enter the EXACT NAME you want to update (Enter 'q' to quit): ");
                String attribute = getChoice();

                /**
                 * logic for updating information in BillingAccount:
                 * first check the validity of the input data using regular expression
                 * if the data is valid, then try to update it in the database
                 * if anything went wrong during the update, all the data update before will be rolled back
                 *
                 */

                switch (attribute) {
                    case "patient_id":
                        System.out.print("[WHMS] Enter new information: ");
                        String id = getChoice();
                        // check whether patient_id is valid
                        regex = ".*[0-9].*";
                        pattern = Pattern.compile(regex);
                        matcher = pattern.matcher(id);
                        while (!matcher.matches()) {
                            System.out.print("[WHMS] NOT valid Patient ID input, please enter again: ");
                            id = getChoice();
                            matcher = pattern.matcher(id);
                        }
                        // update patient_id
                        statement.executeUpdate("UPDATE BillingAccount " +
                                "SET patient_id=" + id + " WHERE account_number=" + accountNum);
                        System.out.println("[WHMS] patient_id has been updated successfully");
                        break;
                    case "SSN_responsible_party":
                        System.out.print("[WHMS] Enter new SSN Responsible Party (xxx-xx-xxxx): ");
                        String infor = getChoice();
                        // check whether input is valid
                        regex = "[0-9]{3}-[0-9]{2}-[0-9]{4}";
                        pattern = Pattern.compile(regex);
                        matcher = pattern.matcher(infor);
                        while (!matcher.matches()) {
                            System.out.print("[WHMS] NOT valid SSN input, please enter SSN Responsible Party again (xxx-xx-xxxx): ");
                            infor = getChoice();
                            matcher = pattern.matcher(infor);
                        }
                        // update ssn
                        statement.executeUpdate("UPDATE BillingAccount " +
                                "SET " + attribute + "='" + infor + "' WHERE account_number=" + accountNum);
                        System.out.println("[WHMS] " + attribute + " has been updated successfully");
                        break;
                    case "payment_method":
                        System.out.print("[WHMS] Enter new Payment Method: ");
                        infor = getChoice();
                        statement.executeUpdate("UPDATE BillingAccount " +
                                "SET " + attribute + "='" + infor + "' WHERE account_number=" + accountNum);
                        System.out.println("[WHMS] " + attribute + " has been updated successfully");
                        break;
                    case "card_number":
                        System.out.print("[WHMS] Enter new Card Number (xxxx-xxxx-xxxx-xxxx OR <empty>): ");
                        infor = getChoice();
                        regex = "[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{4}|^$";
                        pattern = Pattern.compile(regex);
                        matcher = pattern.matcher(infor);
                        while (!matcher.matches()) {
                            System.out.print("[WHMS] NOT valid Card Number, please enter again (xxx-xx-xxxx): ");
                            infor = getChoice();
                            matcher = pattern.matcher(infor);
                        }
                        statement.executeUpdate("UPDATE BillingAccount " +
                                "SET " + attribute + "='" + infor + "' WHERE account_number=" + accountNum);
                        System.out.println("[WHMS] " + attribute + " has been updated successfully");
                        break;
                    case "insurance_provider":
                        System.out.print("[WHMS] Enter new Insurance Provider: ");
                        infor = getChoice();
                        statement.executeUpdate("UPDATE BillingAccount " +
                                "SET " + attribute + "='" + infor + "' WHERE account_number=" + accountNum);
                        System.out.println("[WHMS] " + attribute + " has been updated successfully");
                        break;
                    case "billing_street":
                    case "billing_city":
                    case "billing_state":
                        System.out.print("[WHMS] Enter new Billing Address: ");
                        infor = getChoice();
                        regex = "^(?!\\s*$).+";
                        pattern = Pattern.compile(regex);
                        matcher = pattern.matcher(infor);
                        while (!matcher.matches()) {
                            System.out.print("[WHMS] Billing Address CANNOT be empty, please enter again: ");
                            infor = getChoice();
                            matcher = pattern.matcher(infor);
                        }
                        statement.executeUpdate("UPDATE BillingAccount " +
                                "SET " + attribute + "='" + infor + "' WHERE account_number=" + accountNum);
                        System.out.println("[WHMS] " + attribute + " has been updated successfully");
                        break;
                    case "billing_zip_code":
                        System.out.print("[WHMS] Enter new Zip Code: ");
                        infor = getChoice();
                        regex = "[0-9]{5}";
                        pattern = Pattern.compile(regex);
                        matcher = pattern.matcher(infor);
                        while (!matcher.matches()) {
                            System.out.print("[WHMS] NOT valid Zip Code, please enter again: ");
                            infor = getChoice();
                            matcher = pattern.matcher(infor);
                        }
                        statement.executeUpdate("UPDATE BillingAccount " +
                                "SET " + attribute + "='" + infor + "' WHERE account_number=" + accountNum);
                        System.out.println("[WHMS] " + attribute + " has been updated successfully");
                        break;
                    case "check_in_date":
                        System.out.print("[WHMS] Enter new Date (yyyy-mm-dd): ");
                        infor = getChoice();
                        regex = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
                        pattern = Pattern.compile(regex);
                        matcher = pattern.matcher(infor);
                        while (!matcher.matches()) {
                            System.out.print("[WHMS] NOT valid Date, please enter again: ");
                            infor = getChoice();
                            matcher = pattern.matcher(infor);
                        }
                        statement.executeUpdate("UPDATE BillingAccount " +
                                "SET " + attribute + "='" + infor + "' WHERE account_number=" + accountNum);
                        System.out.println("[WHMS] " + attribute + " has been updated successfully");
                        break;
                    case "q":
                        iter = false;
                        break;
                    default:
                        System.out.println("[WHMS] Invalid input! Please enter again (Enter 'q' to quit update)");
                }
            }

            con.commit();
            con.setAutoCommit(true);

        } catch (SQLException e) {
            System.out.println("[WHMS] Fails to update Billing Account, please try to enter again!");
            System.out.println("[WHMS] All the data enter previously has been rolled back");
            if (con != null) {
                try {
                    con.rollback();
                    con.setAutoCommit(true);
                } catch (SQLException e1) {
                    e.printStackTrace();
                }
            }
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * this method is used by Checkout Class when a patient what to check out.
     * it generates a specific billing record which type is "accommodation fee".
     * So this function is made "public" instead of "private".
     */
    public void accommodationBR(float fee, int accountNum) {
        try {
            Connection con = DriverManager.getConnection(jdbcURL, username, password);
            Statement statement = con.createStatement();

            int billingRecordNum = getNewRecordtNumber(accountNum);

            System.out.println("[WHMS] Enter Billing Record ... ");
            statement.executeUpdate("INSERT INTO BillingRecord (" +
                    "account_number, " +
                    "billing_record_number, " +
                    "fee_description, " +
                    "fee) VALUES (" +
                    accountNum + ", " +
                    billingRecordNum + ", '" +
                    "accommodation fee" + "', " +
                    fee +
                    ")");
            System.out.println("[WHMS] Accommodation billing Record has been successfully generated: ");

            statement.close();
            con.close();

        } catch (SQLIntegrityConstraintViolationException ie) {
            System.out.println("[WHMS] the Billing Account specified above doesn't exist, please try to enter again");
        } catch (SQLException e) {
            System.out.println("[WHMS] Fails to update Billing Account, please try to enter again!");
        }
    }

    /**
     * This method add a Billing Record to a Billing Account
     */
    private void addBR() {
        try {
            Connection con = DriverManager.getConnection(jdbcURL, username, password);
            Statement statement = con.createStatement();
            ResultSet res;

            String regex;
            Pattern pattern;
            Matcher matcher;

            // check the validity of billing account
            System.out.print("[WHMS] Enter Account Number: ");
            String accountNum = getChoice();
            regex = ".*[0-9].*";
            pattern = Pattern.compile(regex);
            matcher = pattern.matcher(accountNum);
            while (!matcher.matches()) {
                System.out.print("[WHMS] NOT valid Account Number input, please enter again: ");
                accountNum = getChoice();
                matcher = pattern.matcher(accountNum);
            }
            // check whether this account number exists
            res = statement.executeQuery("SELECT * FROM BillingAccount WHERE account_number=" + accountNum);
            if (!res.next()) {
                System.out.println("[WHMS] The Billing Account specified above doesn't exist in the database");
                return;
            }
            // check billing record validity
            System.out.print("[WHMS] Enter Billing Record Number: ");
            String billingRecordNum = getChoice();
            regex = ".*[0-9].*";
            pattern = Pattern.compile(regex);
            matcher = pattern.matcher(billingRecordNum);
            while (!matcher.matches()) {
                System.out.print("[WHMS] NOT valid Billing Record Number input, please enter again: ");
                billingRecordNum = getChoice();
                matcher = pattern.matcher(billingRecordNum);
            }
            // check if this billing record already exist
            res = statement.executeQuery("SELECT * FROM BillingRecord " +
                    "WHERE account_number=" + accountNum + " AND billing_record_number=" + billingRecordNum);
            if (res.next()) {
                System.out.println("[WHMS] The Billing Record specified above has already existed in the database");
                return;
            }
            // fee description cannot be null
            System.out.print("[WHMS] Enter Fee Description: ");
            String feeDes = getChoice();
            regex = "^(?!\\s*$).+";
            pattern = Pattern.compile(regex);
            matcher = pattern.matcher(feeDes);
            while (!matcher.matches()) {
                System.out.print("[WHMS] Fee Description CANNOT be empty, please enter again: ");
                feeDes = getChoice();
                matcher = pattern.matcher(feeDes);
            }
            // check fee input validity
            System.out.print("[WHMS] Enter Fee: ");
            String fee = getChoice();
            regex = "([0-9]*[.])?[0-9]+";
            pattern = Pattern.compile(regex);
            matcher = pattern.matcher(fee);
            while (!matcher.matches()) {
                System.out.print("[WHMS] NOT valid Fee input, please enter again: ");
                fee = getChoice();
                matcher = pattern.matcher(fee);
            }

            System.out.println("[WHMS] Enter Billing Record ... ");
            statement.executeUpdate("INSERT INTO BillingRecord (" +
                    "account_number, " +
                    "billing_record_number, " +
                    "fee_description, " +
                    "fee) VALUES (" +
                    accountNum + ", " +
                    billingRecordNum + ", '" +
                    feeDes + "', " +
                    fee +
                    ")");
            System.out.println("[WHMS] Billing Record is successfully generated: ");

            res.close();
            statement.close();
            con.close();

        } catch (SQLIntegrityConstraintViolationException ie) {
            System.out.println("[WHMS] the Billing Account specified above doesn't exist, please try to enter again");
        } catch (SQLException e) {
            System.out.println("[WHMS] Fails to update Billing Account, please try to enter again!");
        }
    }

    /**
     * This method updates a Billing Record
     */
    private void updateBR() {

        /**
         * logic for updating information in BillingRecord:
         * first check the validity of the input data using regular expression
         * if the data is valid, then try to update it in the database
         * if anything went wrong during the update, all the data update before will be rolled back
         *
         */

        try {
            Connection con = DriverManager.getConnection(jdbcURL, username, password);
            Statement statement = con.createStatement();
            ResultSet res;

            String regex;
            Pattern pattern;
            Matcher matcher;

            // first specify which billing record to update
            System.out.print("[WHMS] Enter Account Number to Update: ");
            String accountNum = getChoice();
            regex = ".*[0-9].*";
            pattern = Pattern.compile(regex);
            matcher = pattern.matcher(accountNum);
            while (!matcher.matches()) {
                System.out.print("[WHMS] NOT valid Account Number input, please enter again: ");
                accountNum = getChoice();
                matcher = pattern.matcher(accountNum);
            }
            // check whether this account number exists
            res = statement.executeQuery("SELECT * FROM BillingAccount WHERE account_number=" + accountNum);
            if (!res.next()) {
                System.out.println("[WHMS] The Billing Account specified above doesn't exist in the database");
                return;
            }
            // check billing record validity
            System.out.print("[WHMS] Enter Billing Record Number to Update: ");
            String billingRecordNum = getChoice();
            regex = ".*[0-9].*";
            pattern = Pattern.compile(regex);
            matcher = pattern.matcher(billingRecordNum);
            while (!matcher.matches()) {
                System.out.print("[WHMS] NOT valid Billing Record Number input, please enter again: ");
                billingRecordNum = getChoice();
                matcher = pattern.matcher(billingRecordNum);
            }
            // check if this billing record already exist
            res = statement.executeQuery("SELECT * FROM BillingRecord " +
                    "WHERE account_number=" + accountNum + " AND billing_record_number=" + billingRecordNum);
            if (!res.next()) {
                System.out.println("[WHMS] The Billing Record specified above doesn't exist in the database");
                return;
            }


            System.out.println("[WHMS] Following are the information that you can update.");
            System.out.println("[WHMS] ------------------------");
            System.out.println("[WHMS] fee_description");
            System.out.println("[WHMS] fee");
            System.out.println("[WHMS] ------------------------");

            boolean iter = true;

            while (iter) {
                System.out.print("[WHMS] Enter the EXACT NAME you want to update (Enter 'q' to quit): ");
                String attribute = getChoice();

                switch (attribute) {
                    case "fee_description":
                        System.out.print("[WHMS] Enter fee description: ");
                        String des = getChoice();
                        regex = "^(?!\\s*$).+";
                        pattern = Pattern.compile(regex);
                        matcher = pattern.matcher(des);
                        while (!matcher.matches()) {
                            System.out.print("[WHMS] Fee Description CANNOT be empty, please enter again: ");
                            des = getChoice();
                            matcher = pattern.matcher(des);
                        }
                        statement.executeUpdate("UPDATE BillingRecord " +
                                "SET fee_description='" + des + "' WHERE account_number=" + accountNum + " AND billing_record_number=" + billingRecordNum);
                        System.out.println("[WHMS] Fee Description has been updated successfully");
                        break;
                    case "fee":
                        System.out.print("[WHMS] Enter fee: ");
                        String f = getChoice();
                        regex = "([0-9]*[.])?[0-9]+";
                        pattern = Pattern.compile(regex);
                        matcher = pattern.matcher(f);
                        while (!matcher.matches()) {
                            System.out.print("[WHMS] NOT valid Fee input, please enter again: ");
                            f = getChoice();
                            matcher = pattern.matcher(f);
                        }
                        statement.executeUpdate("UPDATE BillingRecord " +
                                "SET fee=" + f + " WHERE account_number=" + accountNum + " AND billing_record_number=" + billingRecordNum);
                        System.out.println("[WHMS] Fee has been updated successfully");
                        break;
                    case "q":
                        iter = false;
                        break;
                    default:
                        System.out.println("[WHMS] Invalid input! Please enter again (enter 'q' to quit update)");
                }
            }

            res.close();
            statement.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("[WHMS] Fails to update Billing Record, please try to enter again!");
        }
    }

    /**
     * This method delete a Billing Record
     */
    private void deleteBR() {

        /**
         * logic:
         * first check whether the billing account and patientID specified exists in the database
         * if this billing record exists, then try to delete it
         */

        try {
            Connection con = DriverManager.getConnection(jdbcURL, username, password);
            Statement statement = con.createStatement();
            ResultSet res;

            String regex;
            Pattern pattern;
            Matcher matcher;

            // check account number validity
            System.out.print("[WHMS] Enter Account Number: ");
            String accountNum = getChoice();
            regex = ".*[0-9].*";
            pattern = Pattern.compile(regex);
            matcher = pattern.matcher(accountNum);
            while (!matcher.matches()) {
                System.out.print("[WHMS] NOT valid Account Number input, please enter again: ");
                accountNum = getChoice();
                matcher = pattern.matcher(accountNum);
            }
            // check whether this account number exists
            res = statement.executeQuery("SELECT * FROM BillingAccount WHERE account_number=" + accountNum);
            if (!res.next()) {
                System.out.println("[WHMS] The Billing Account specified above doesn't exist in the database");
                return;
            }
            // check billing account validity
            System.out.print("[WHMS] Enter Billing Record Number: ");
            String billingRecordNum = getChoice();
            regex = ".*[0-9].*";
            pattern = Pattern.compile(regex);
            matcher = pattern.matcher(billingRecordNum);
            while (!matcher.matches()) {
                System.out.print("[WHMS] NOT valid Billing Record input, please enter again: ");
                billingRecordNum = getChoice();
                matcher = pattern.matcher(billingRecordNum);
            }
            // check whether this billing record exists
            res = statement.executeQuery("SELECT * FROM BillingRecord " +
                    "WHERE account_number=" + accountNum + " AND billing_record_number=" + billingRecordNum);
            if (!res.next()) {
                System.out.println("[WHMS] The Billing Record specified above doesn't exist in the database");
                return;
            }

            System.out.println("[WHMS] The Billing Record specified above is being deleted ");
            System.out.println("[WHMS] . . . . . . . ");

            statement.executeUpdate("DELETE FROM BillingRecord " +
                    "WHERE account_number=" + accountNum + " AND billing_record_number=" + billingRecordNum);

            System.out.println("[WHMS] Billing Record specified has been successfully deleted: ");

            res.close();
            statement.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("[WHMS] Fails to delete Billing Record, please try to enter again!");
        }
    }

    /**
     * This method deletes a Billing Account
     */
    private void deleteBA() {
        /**
         * logic:
         * first check whether the billing account specified exists in the database
         * if this billing account exists, then try to delete it
         */
        try {
            Connection con = DriverManager.getConnection(jdbcURL, username, password);
            Statement statement = con.createStatement();
            ResultSet res;

            String regex;
            Pattern pattern;
            Matcher matcher;

            System.out.print("[WHMS] Enter Account Number: ");
            String accountNum = getChoice();
            regex = ".*[0-9].*";
            pattern = Pattern.compile(regex);
            matcher = pattern.matcher(accountNum);
            while (!matcher.matches()) {
                System.out.print("[WHMS] NOT valid Account Number input, please enter again: ");
                accountNum = getChoice();
                matcher = pattern.matcher(accountNum);
            }
            // check whether this account number exists
            res = statement.executeQuery("SELECT * FROM BillingAccount WHERE account_number=" + accountNum);
            if (!res.next()) {
                System.out.println("[WHMS] The Billing Account specified above doesn't exist in the database");
                return;
            }
            System.out.println("[WHMS] The Billing Account specified above is about to be deleted ");
            System.out.println("[WHMS] . . . . . . . ");

            statement.executeUpdate("DELETE FROM BillingAccount " +
                    "WHERE account_number=" + accountNum);

            System.out.println("[WHMS] Billing Account specified has been successfully deleted: ");

            res.close();
            statement.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("[WHMS] Fails to update Billing Account, please try to enter again!");
        }
    }

    /**
     * This method List all the Billing Account given a specific patient.
     * This method is called in the Checkout class to generate the bills.
     * This method will be called so it's "public" instead of "private"
     */
    public void ListBR(int accountNum) {
        try {
            Connection con = DriverManager.getConnection(jdbcURL, username, password);
            Statement statement = con.createStatement();

            String query = ("SELECT billing_record_number, check_in_date, fee_description,fee" +
                    " FROM BillingRecord NATURAL JOIN BillingAccount WHERE account_number=" + accountNum);

            ResultSet resultSet = statement.executeQuery(query);

            int i = 0;

            System.out.println("[WHMS]");
            System.out.println("[WHMS] Here is all the Billing Record for Billing Account " + accountNum + ": ");
            while (resultSet.next()) {
                i++;
                int recordNum = resultSet.getInt("billing_record_number");
                String checkIn = resultSet.getString("check_in_date");
                String feeDes = resultSet.getString("fee_description");
                float fee = resultSet.getFloat("fee");
                System.out.println("[WHMS]");
                System.out.println("[WHMS] Billing Record Number: " + recordNum);
                System.out.println("[WHMS] Check-in Date: " + checkIn);
                System.out.println("[WHMS] Fee Description: " + feeDes);
                System.out.println("[WHMS] Fee: " + fee);
            }
            System.out.println("[WHMS]");
            System.out.println("[WHMS] Summary:");
            System.out.println("[WHMS] total " + i + " Billing Record(s) are listed");

            resultSet.close();
            statement.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("[WHMS] Something went wrong when listing Billing Record");
            throw new RuntimeException(e);
        }
    }

    /**
     * This method List all the Billing Records for a Billing Account which is entered by users
     */
    private void ListBR() {
        try {
            String regex;
            Pattern pattern;
            Matcher matcher;
            Connection con = DriverManager.getConnection(jdbcURL, username, password);
            Statement statement = con.createStatement();
            ResultSet res;

            System.out.print("[WHMS] Enter the Account Number you want to check: ");
            String accountNum = getChoice();
            regex = ".*[0-9].*";
            pattern = Pattern.compile(regex);
            matcher = pattern.matcher(accountNum);
            while (!matcher.matches()) {
                System.out.print("[WHMS] NOT valid Patient ID input, please enter again: ");
                accountNum = getChoice();
                matcher = pattern.matcher(accountNum);
            }
            res = statement.executeQuery("SELECT * FROM BillingAccount WHERE account_number=" + accountNum);
            if (!res.next()) {
                System.out.println("[WHMS] The Account Number specified above doesn't exist in the database");
                return;
            }

            res.close();
            statement.close();
            con.close();

            int ac = Integer.parseInt(accountNum);
            ListBR(ac);

        } catch (SQLException e) {
            System.out.println("[WHMS] Something went wrong when trying to list Billing Records");
            throw new RuntimeException(e);
        }
    }

    /**
     * This method List all the Billing Account given a specific patient
     */
    private void ListBA() {
        try {
            String regex;
            Pattern pattern;
            Matcher matcher;
            Connection con = DriverManager.getConnection(jdbcURL, username, password);
            Statement statement = con.createStatement();
            ResultSet res;

            System.out.print("[WHMS] Enter the Patient ID you want to check: ");
            String patientID = getChoice();
            regex = ".*[0-9].*";
            pattern = Pattern.compile(regex);
            matcher = pattern.matcher(patientID);
            while (!matcher.matches()) {
                System.out.print("[WHMS] NOT valid Patient ID input, please enter again: ");
                patientID = getChoice();
                matcher = pattern.matcher(patientID);
            }
            res = statement.executeQuery("SELECT * FROM Patient WHERE patient_id=" + patientID);
            if (!res.next()) {
                System.out.println("[WHMS] The Patient ID specified above doesn't exist in the database");
                return;
            }

            String query = ("SELECT account_number, SSN_responsible_party, payment_method, card_number, insurance_provider, " +
                    "billing_street, billing_city, billing_state, billing_zip_code, check_in_date" +
                    " FROM BillingAccount WHERE patient_id=" + patientID);

            ResultSet resultSet = statement.executeQuery(query);

            int i = 0;

            System.out.println("[WHMS]");
            System.out.println("[WHMS] Here is all the Billing Account for Patient ID " + patientID + ": ");
            while (resultSet.next()) {
                i++;
                int accountNum = resultSet.getInt("account_number");
                String ssn = resultSet.getString("SSN_responsible_party");
                String payMethod = resultSet.getString("payment_method");
                String cardNum = resultSet.getString("card_number");
                String insur = resultSet.getString("insurance_provider");
                String street = resultSet.getString("billing_street");
                String city = resultSet.getString("billing_city");
                String state = resultSet.getString("billing_state");
                String zip = resultSet.getString("billing_zip_code");
                String in = resultSet.getString("check_in_date");
                System.out.println("[WHMS]");
                System.out.println("[WHMS] Billing Account Number: " + accountNum);
                System.out.println("[WHMS] SSN Responsible Party: " + ssn);
                System.out.println("[WHMS] Payment Method: " + payMethod);
                System.out.println("[WHMS] Card Number: " + cardNum);
                System.out.println("[WHMS] Insurance Provider: " + insur);
                System.out.println("[WHMS] Billing Street: " + street);
                System.out.println("[WHMS] Billing City: " + city);
                System.out.println("[WHMS] Billing State: " + state);
                System.out.println("[WHMS] Billing Zip Code: " + zip);
                System.out.println("[WHMS] Check-in Date: " + in);
            }
            System.out.println("[WHMS]");
            System.out.println("[WHMS] Summary:");
            System.out.println("[WHMS] total " + i + " Billing Account(s) are listed");

            resultSet.close();
            statement.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("[WHMS] Something went wrong when listing Billing Account");
            throw new RuntimeException(e);
        }
    }

    /**
     * This method gets inputs from the users
     */
    private static String getChoice() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public int getLatestAccountNumber(int patientID) {
        int result = -1;

        try {
            Connection con = DriverManager.getConnection(jdbcURL, username, password);
            Statement statement = con.createStatement();

            String query = ("SELECT account_number FROM BillingAccount WHERE patient_id=" + patientID);

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next())
                result = resultSet.getInt("account_number");

            resultSet.close();
            statement.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("[WHMS] Something when calculating the latest billing account");
        }

        if (result == -1)
            System.out.println("[WHMS] There is no Billing Account associated with this patient ID");

        return result;
    }

    //accountNum should always be valid
    private int getNewRecordtNumber(int accountNum) {
        int result = 0;

        try {
            Connection con = DriverManager.getConnection(jdbcURL, username, password);
            Statement statement = con.createStatement();

            String query = ("SELECT billing_record_number FROM BillingRecord WHERE account_number=" + accountNum);

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next())
                result = resultSet.getInt("billing_record_number");

            resultSet.close();
            statement.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("[WHMS] Something when calculating the new billing record number");
        }

        result++;

        return result;
    }

}