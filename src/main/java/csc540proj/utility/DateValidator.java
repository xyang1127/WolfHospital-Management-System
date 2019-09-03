package csc540proj.utility;

/**
 * This is a simple class with static methods to validate the format of dates.
 */
final public class DateValidator {

    /**
     * Hide the constructor since we don't want instances of this class to be
     * instantiated (since it is a utility class for static methods).
     */
    private DateValidator() {
    }

    /**
     * This method validates that a year is of the form 'YYYY'.
     * It returns true if the year is valid.
     */
    public static boolean isYearValid(String year) {
        // If the year does not have 4 characters, we know it is wrong.
        if (year.length() != 4) {
            return false;
        }

        // If the year has a non-numeric character, we know it is wrong.
        for (int i = 0; i < year.length(); i++) {
            char c = year.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }

        return true;
    }

    /**
     * This method validates that a month is of the form 'MM'.
     * It returns null if the month is invalid. Otherwise, it returns the
     * last day in the month.
     */
    public static String isMonthValid(String month) {
        String lastDay = null;
        switch (month) {
            case "01":
            case "03":
            case "05":
            case "07":
            case "08":
            case "10":
            case "12":
                lastDay = "31";
                break;

            case "04":
            case "06":
            case "09":
            case "11":
                lastDay = "30";
                break;

            case "02":
                lastDay = "28";
                break;

            default:
                return null;
        }

        return lastDay;
    }
}
