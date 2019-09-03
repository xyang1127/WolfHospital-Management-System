package csc540proj;

import csc540proj.information.MedicalRecord;
import csc540proj.information.Reservations;
import csc540proj.utility.CommandLine;
import csc540proj.utility.Helper;

import java.sql.Connection;

public class Checkin {

    private final CommandLine commandLine;
    private final Connection connection;

    public Checkin(final CommandLine commandLine, final Connection connection) {
        this.commandLine = commandLine;
        this.connection = connection;
    }

    public void run() {

        int patientID = Helper.getPatient(connection, commandLine);
        Helper.queryAndHandle(connection, "SELECT * FROM Patient WHERE patient_id = " + patientID, resultSet1 -> {
            try {
                if (resultSet1.next()) {
                    String startDate = Helper.getStartDate(commandLine);

                    commandLine.println("date used: " + startDate);
                    Reservations Res = new Reservations(commandLine, connection);
                    Helper.queryAndHandle(connection, "SELECT ward_number, bed_number FROM Bed WHERE availability = 1", resultSet -> {
                        if (resultSet.next()) {
                            commandLine.println("Reserving Bed: " + resultSet.getInt(2) + " in Ward: " + resultSet.getInt(1) + "...");
                            Res.reserveBed(patientID, startDate, resultSet.getInt(1), resultSet.getInt(2));
                        } else commandLine.println("No beds available. Continuing without bed reservation.");
                    });

                    MedicalRecord MR = new MedicalRecord();
                    MR.enterCheckInMedicalRecord(patientID, startDate);

                    BillingAccounts BA = new BillingAccounts();
                    BA.generateBA(patientID, startDate);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
