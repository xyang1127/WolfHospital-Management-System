package csc540proj.utility;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SQLResourceReaderTest {

    @Test
    public void itWorks() {
        String[] statements = SQLResourceReader.getStatements("/some-sql-file.sql");
        assertEquals(5, statements.length);
        assertTrue(statements[0].trim().startsWith("CREATE TABLE"));
        assertTrue(statements[0].trim().endsWith(")"));

        assertTrue(statements[1].trim().startsWith("CREATE TABLE"));
        assertTrue(statements[1].trim().endsWith(")"));

        assertTrue(statements[2].trim().startsWith("DROP TABLE"));
        assertTrue(statements[2].trim().endsWith("Staff"));

        assertTrue(statements[3].trim().startsWith("DROP TABLE"));
        assertTrue(statements[3].trim().endsWith("Doctor"));

        assertTrue(statements[4].trim().startsWith("INSERT INTO"));
        assertTrue(statements[4].trim().endsWith(")"));
    }

}