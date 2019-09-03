package csc540proj;

import csc540proj.information.Reservations;
import csc540proj.utility.CommandLine;
import csc540proj.utility.Helper;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.Scanner;

public class Checkout {

    private final CommandLine commandLine;
    private final Connection connection;

    public Checkout(CommandLine commandLine, Connection connection) {
        this.commandLine = commandLine;
        this.connection = connection;
    }

    public void run() {
        int patientId = Helper.getPatient(connection, commandLine);
        if (patientId == -1) {
            return;
        }

        final String checkOutDate = Helper.getDate(commandLine, "check out date");
        finishCheckinMedicalRecords(patientId, checkOutDate);

        // TODO: do we want the ability to set that the bed was released at a different date than right now?
        Reservations Res = new Reservations(commandLine, connection);
        Helper.queryAndHandle(connection, "SELECT ward_number, bed_number FROM PatientAssignedBed WHERE patient_id = " + patientId + " AND end_date IS NULL", resultSet -> {
            while (resultSet.next()) {
                commandLine.println("Releasing Bed: " + resultSet.getInt(2) + " in Ward: " + resultSet.getInt(1) + "...");
                Res.releaseBed(patientId, LocalDate.now(), resultSet.getInt(1), resultSet.getInt(2));
            }
            commandLine.println("No more beds are reserved or assigned to patient: " + patientId);
        });

        // TODO: we should be setting the bed to unassigned.


        BillingAccounts ba = new BillingAccounts();
        int billingAccountNumber = ba.getLatestAccountNumber(patientId);
        if (billingAccountNumber == -1) {
            System.out.println("[WHMS] No Billing Account is associated with this patient " + patientId);
            return;
        }
        // generate billing record for accommodation fee
        float fee = 0;
        System.out.print("[WHMS] Total days patient " + patientId + " have stayed in the ward: ");
        String days = getChoice();
        System.out.print("[WHMS] Charge per day in that ward: ");
        String cpd = getChoice();
        fee = Float.parseFloat(days) * Float.parseFloat(cpd);
        ba.accommodationBR(fee, billingAccountNumber);

        // print out all the billing records for this visit
        ba.ListBR(billingAccountNumber);
    }

    private void finishCheckinMedicalRecords(int patientId, final String checkOutDate) {
        String query = String.format(
                "UPDATE MedicalRecord SET end_date='%s' WHERE patient_id=%d AND end_date IS NULL",
                checkOutDate, patientId);
        Helper.insertAndHandle(connection, query);
    }

    private static String getChoice() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
