package csc540proj.utility;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * This class represents interactions with the command line.
 * It is primarily used to make testing stuff written to stdout easier.
 */
public class CommandLine {

    private final String prefix;
    private final Scanner input;
    private final PrintStream output;

    /**
     * This method is the constructor for this class.
     * <p>
     * The prefix will always be printed before every line.
     * The input is where user provided data will be read from.
     * The output is where stuff will be written.
     */
    public CommandLine(final String prefix, final InputStream intput, final OutputStream output) {
        this.prefix = prefix;
        this.input = new Scanner(intput);
        this.output = new PrintStream(output);
    }

    /**
     * This method prints a break between menus.
     */
    public void printBreak() {
        output.println("**************************************************");
    }

    /**
     * This method prints a string to the specified output.
     * It includes a newline on the end.
     */
    public void println(String string) {
        output.println(prefix + string);
    }

    /**
     * This method prints a string to the specified output.
     * It does NOT include a newline on the end.
     */
    public void print(String string) {
        output.print(prefix + string);
    }

    /**
     * This method prints a printf-style formatted string to the specified output.
     * It includes a newline on the end.
     */
    public void printf(String format, Object... args) {
        String string = String.format(format, args);
        println(string);
    }

    /**
     * This method polls the user for an input string.
     * It first prompts the user with the provided prompt.
     */
    public String getInput(final String prompt) {
        print(prompt);
        return input.nextLine();
    }

    /**
     * This method polls the user for an input string.
     * It does not prompt the user.
     */
    public String getInput() {
        return input.nextLine();
    }
}
