package csc540proj.utility;

import javafx.util.Pair;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DateValidatorTest {

    @Test
    public void testIsYearValid() {
        List<Pair<String, Boolean>> data = Arrays.asList(
                new Pair<String, Boolean>("2010", true),
                new Pair<String, Boolean>("0010", true),
                new Pair<String, Boolean>("0000", true),

                new Pair<String, Boolean>("201", false),
                new Pair<String, Boolean>("201a", false),
                new Pair<String, Boolean>("a010", false),
                new Pair<String, Boolean>("-111", false),
                new Pair<String, Boolean>("", false)
        );

        for (Pair<String, Boolean> datum : data) {
            boolean actual = DateValidator.isYearValid(datum.getKey());
            boolean expected = datum.getValue();
            assertEquals(expected, actual);
        }
    }

    @Test
    public void testIsMonthValid() {
        List<Pair<String, String>> data = Arrays.asList(
                new Pair<String, String>("01", "31"),
                new Pair<String, String>("02", "28"),
                new Pair<String, String>("03", "31"),
                new Pair<String, String>("04", "30"),
                new Pair<String, String>("05", "31"),
                new Pair<String, String>("06", "30"),
                new Pair<String, String>("07", "31"),
                new Pair<String, String>("08", "31"),
                new Pair<String, String>("09", "30"),
                new Pair<String, String>("10", "31"),
                new Pair<String, String>("11", "30"),
                new Pair<String, String>("12", "31"),

                new Pair<String, String>("00", null),
                new Pair<String, String>("", null),
                new Pair<String, String>("13", null),
                new Pair<String, String>("-1", null),
                new Pair<String, String>("aa", null),
                new Pair<String, String>("1a", null)
        );

        for (Pair<String, String> datum : data) {
            String actual = DateValidator.isMonthValid(datum.getKey());
            String expected = datum.getValue();
            assertEquals(expected, actual);
        }
    }

}