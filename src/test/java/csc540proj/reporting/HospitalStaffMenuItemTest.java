package csc540proj.reporting;

import org.junit.BeforeClass;
import org.junit.Test;

public class HospitalStaffMenuItemTest {

    @BeforeClass
    public static void resetDatabase() throws Exception {
        TestUtil.resetDatabase();
    }

    @Test
    public void printsStaffByRole() throws Exception {
        String expected = "Role: Billing staff\n" +
                "  - ID: 101\n" +
                "    First name: John\n" +
                "    Last name: Doe\n" +
                "    DOB: 1974-02-05\n" +
                "    age: 45\n" +
                "    Gender: M\n" +
                "    Phone number: 564\n" +
                "    Professional title: null\n" +
                "    Department: Office\n" +
                "    Street Address: 798 XYZ St\n" +
                "    City: Rochester\n" +
                "    State: NY\n" +
                "    Zip Code: 54\n" +
                "Role: Doctor\n" +
                "  - ID: 100\n" +
                "    First name: Mary\n" +
                "    Last name: Smith\n" +
                "    DOB: 1979-01-01\n" +
                "    age: 40\n" +
                "    Gender: F\n" +
                "    Phone number: 654\n" +
                "    Professional title: senior\n" +
                "    Department: Neurology\n" +
                "    Street Address: 90 ABC St\n" +
                "    City: Raleigh\n" +
                "    State: NC\n" +
                "    Zip Code: 27\n" +
                "  - ID: 103\n" +
                "    First name: Emma\n" +
                "    Last name: Sams\n" +
                "    DOB: 1964-03-25\n" +
                "    age: 55\n" +
                "    Gender: F\n" +
                "    Phone number: 546\n" +
                "    Professional title: Senior surgeon\n" +
                "    Department: Oncological Surgery\n" +
                "    Street Address: 49 ABC St\n" +
                "    City: Raleigh\n" +
                "    State: NC\n" +
                "    Zip Code: 27\n" +
                "  - ID: 105\n" +
                "    First name: Peter\n" +
                "    Last name: Brown\n" +
                "    DOB: 1967-03-05\n" +
                "    age: 52\n" +
                "    Gender: M\n" +
                "    Phone number: 724\n" +
                "    Professional title: Anesthetist\n" +
                "    Department: Oncological Surgery\n" +
                "    Street Address: 475 RG St\n" +
                "    City: Raleigh\n" +
                "    State: NC\n" +
                "    Zip Code: 27\n" +
                "Role: Front Desk Staff\n" +
                "  - ID: 104\n" +
                "    First name: Ava\n" +
                "    Last name: Hale\n" +
                "    DOB: 1964-01-15\n" +
                "    age: 55\n" +
                "    Gender: F\n" +
                "    Phone number: 777\n" +
                "    Professional title: null\n" +
                "    Department: Office\n" +
                "    Street Address: 425 RG St\n" +
                "    City: Raleigh\n" +
                "    State: NC\n" +
                "    Zip Code: 27\n" +
                "Role: Nurse\n" +
                "  - ID: 102\n" +
                "    First name: Carol\n" +
                "    Last name: Jones\n" +
                "    DOB: 1964-01-05\n" +
                "    age: 55\n" +
                "    Gender: F\n" +
                "    Phone number: 911\n" +
                "    Professional title: null\n" +
                "    Department: ER\n" +
                "    Street Address: 351 MH St\n" +
                "    City: Greensboro\n" +
                "    State: NC\n" +
                "    Zip Code: 27\n" +
                "  - ID: 106\n" +
                "    First name: Olivia\n" +
                "    Last name: Strange\n" +
                "    DOB: 1992-04-01\n" +
                "    age: 27\n" +
                "    Gender: F\n" +
                "    Phone number: 799\n" +
                "    Professional title: null\n" +
                "    Department: Neurology\n" +
                "    Street Address: 325 PD St\n" +
                "    City: Raleigh\n" +
                "    State: NC\n" +
                "    Zip Code: 27\n";
        TestUtil.assertMenuItemBehavior("", expected, HospitalStaffMenuItem.class);
    }

}