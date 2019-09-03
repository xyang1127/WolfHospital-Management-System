package csc540proj.reporting;

import csc540proj.utility.CommandLine;
import csc540proj.utility.Helper;
import csc540proj.utility.menu.AbstractMenuItem;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * This class provides a menu item that prints out the hospital staff
 * organized by their job title.
 */
public class HospitalStaffMenuItem extends AbstractMenuItem {

    /**
     * This constructor initializes this menu item with a title.
     */
    public HospitalStaffMenuItem() {
        super("Hospital Staff (Grouped By Role)");
    }

    /**
     * This method runs the functionality associated with this menu item.
     */
    @Override
    public void run(CommandLine commandLine, Connection connection) {
        // Submit a query that returns all of the staff ordered by
        // job title.
        Map<String, List<Staff>> roleGroupings = new LinkedHashMap<>();
        String query = "SELECT staff_id, first_name, last_name, job_title, DOB, TIMESTAMPDIFF(YEAR, DOB, CURDATE()) AS 'age', gender, phone_number, professional_title, department, street_address, city, state, zip_code FROM Staff ORDER BY job_title";
        Helper.queryAndHandle(connection, query, resultSet -> {
            // For each response, put them in a map from job title to
            // lastName, firstName strings.
            while (resultSet.next()) {
                // Get the id, first name, last name, and job title of the returned
                // staff member.
                int staffId = resultSet.getInt("staff_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String role = resultSet.getString("job_title");
                String dob = resultSet.getString("DOB");
                String age = resultSet.getString("age");
                String gender = resultSet.getString("gender");
                String phoneNumber = resultSet.getString("phone_number");
                String professionalTitle = resultSet.getString("professional_title");
                String department = resultSet.getString("department");
                String streetAddress = resultSet.getString("street_address");
                String city = resultSet.getString("city");
                String state = resultSet.getString("state");
                String zip_code = resultSet.getString("zip_code");

                // If we don't currently have an entry in the map for this job title,
                // then add one.
                List<Staff> staffInRole = roleGroupings.get(role);
                if (staffInRole == null) {
                    roleGroupings.put(role, (staffInRole = new ArrayList<>()));
                }

                // Create a Staff object to represent this staff member. It will
                // later be printed out.
                Staff staff = new Staff();
                staff.staffId = staffId;
                staff.firstName = firstName;
                staff.lastName = lastName;
                staff.dob = dob;
                staff.age = age;
                staff.gender = gender;
                staff.phoneNumber = phoneNumber;
                staff.professionalTitle = professionalTitle;
                staff.department = department;
                staff.streetAddress = streetAddress;
                staff.city = city;
                staff.state = state;
                staff.zipCode = zip_code;

                staffInRole.add(staff);
            }

            // For each job title, print out the staff members with that job title.
            for (String role : roleGroupings.keySet()) {
                commandLine.println("Role: " + role);
                List<Staff> staffInRole = roleGroupings.get(role);
                for (Staff staffMember : staffInRole) {
                    commandLine.println("  - ID: " + staffMember.staffId);
                    commandLine.println("    First name: " + staffMember.firstName);
                    commandLine.println("    Last name: " + staffMember.lastName);
                    commandLine.println("    DOB: " + staffMember.dob);
                    commandLine.println("    age: " + staffMember.age);
                    commandLine.println("    Gender: " + staffMember.gender);
                    commandLine.println("    Phone number: " + staffMember.phoneNumber);
                    commandLine.println("    Professional title: " + staffMember.professionalTitle);
                    commandLine.println("    Department: " + staffMember.department);
                    commandLine.println("    Street Address: " + staffMember.streetAddress);
                    commandLine.println("    City: " + staffMember.city);
                    commandLine.println("    State: " + staffMember.state);
                    commandLine.println("    Zip Code: " + staffMember.zipCode);
                }
            }
        });
    }

    /**
     * This is a helper data class that makes the printing of staff members easier.
     */
    private static class Staff {
        public int staffId;
        public String firstName, lastName, dob, age, gender, phoneNumber, professionalTitle, department, streetAddress, city, state, zipCode;
    }
}
