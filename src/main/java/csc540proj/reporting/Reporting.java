package csc540proj.reporting;


import csc540proj.utility.CommandLine;
import csc540proj.utility.menu.AbstractMenu;

import java.sql.Connection;

/**
 * This class is a menu that provides access to the individual
 * reporting operations.
 */
public class Reporting extends AbstractMenu {
    /**
     * This constructor initializes the command line, database connections, title,
     * and menu items via the superclass, AbstractMenu.
     */
    public Reporting(final CommandLine commandLine, final Connection connection) {
        super(
                commandLine,
                connection,
                "Reporting Menu",
                new MedicalHistoryMenuItem(),
                new WardBedUsageMenuItem(),
                new PatientsPerMonthMenuItem(),
                new WardUsagePercentageMenuItem(),
                new DoctorOfPatientsMenuItem(),
                new HospitalStaffMenuItem()
        );
    }
}
