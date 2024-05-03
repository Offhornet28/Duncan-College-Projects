
/*
 * Program featuring an array to store and interactively manipulate a list of numbers.
 */

package arrayplay;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class NumberList {
    //Local variables of the class to be used globally within it
    private static final int LIMIT = 1000;
    private static int[] numbers = new int[LIMIT];
    private static int numberOfNumbers = 0;
    private static Scanner commandReader = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            //Establish array of numbers
            readNumbers();
            //Check the Data
            //System.out.println("nThe original list of numbers ...");
            //displayNumbers();
            //Enter the Interpreter
            interpreter();
        } catch (FileNotFoundException ex) {
            System.out.println("The file was not found. Please think again.");
            System.exit(-1);
        }
    }

    //Assuming that the data file will be found in the public_html/data
    //subdirectory of the user’s home directory.
    private static Scanner establishScanner(String fn) throws FileNotFoundException {
        String separator = File.separator;
        String homeDirectory = System.getProperty("user.home");
        String path = homeDirectory + separator + "public_html" + separator + "data" + separator;
        String fullFileName = path + fn;
        return new Scanner(new File(fullFileName));
    }

    private static void readNumbers() throws FileNotFoundException {
        Scanner scanner = establishScanner("NumberSet.txt");
        while (scanner.hasNext()) {
            numbers[numberOfNumbers] = scanner.nextInt();
            numberOfNumbers = numberOfNumbers + 1;
        }
    }

    private static void displayNumbers() {
        for (int x = 0; x < numberOfNumbers; x = x + 1) {
            System.out.println(numbers[x]);
        }
    }

    private static void interpreter() {
        System.out.print(">>> ");
        String command = commandReader.next();
        if (command.equalsIgnoreCase("DISPLAY")) {
            interpreterDisplayCommand();
        } else if (command.equalsIgnoreCase("PRINT")) {
            interpretPrintCommand();
        } else if (command.equalsIgnoreCase("SWAP")) {
            interpretSwapCommand();
        } else if (command.equalsIgnoreCase("ADD")) {
            interpretAddCommand();
        } else if (command.equalsIgnoreCase("HELP")) {
            interpretHelpCommand();
        } else if (command.equalsIgnoreCase("EXIT")) {
            System.exit(0);
        } else {
            System.out.println("### Unrecognizable command: " + command);
        }
        interpreter();
    }

    private static void interpreterDisplayCommand() {
        displayNumbers();
    }

    private static void interpretPrintCommand() {
        String operand = commandReader.next();
        if (operand.equalsIgnoreCase("FIRST")) {
            System.out.println(numbers[0]);
        } else if (operand.equalsIgnoreCase("LAST")) {
            System.out.println(numbers[numberOfNumbers - 1]);
        } else {
            int index = Integer.valueOf(operand);
            System.out.println(numbers[index - 1]);
        }
    }

    private static void interpretHelpCommand() {
        System.out.println("HELP - display a menu of commands");
        System.out.println("DISPLAY - display the list of numbers");
        System.out.println("PRINT - print a number (FIRST;LAST;nth)");
        System.out.println("SWAP - exchange two elements (nth;mth)");
        System.out.println("ADD - add a number to the list (FIRST;LAST)");
        System.out.println("EXIT - terminate execution of the program");
    }

    private static void interpretSwapCommand() {
        int position1 = commandReader.nextInt();
        int position2 = commandReader.nextInt();
        int temp = numbers[position1 - 1];
        numbers[position1 - 1] = numbers[position2 - 1];
        numbers[position2 - 1] = temp;
    }

    private static void interpretAddCommand() {
        String position = commandReader.next();
        if (position.equalsIgnoreCase("LAST")) {
            addLast();
        } else if (position.equalsIgnoreCase("FIRST")) {
            addFirst();
        } else {
            System.out.println("### invalid operand for add command");
        }
        numberOfNumbers = numberOfNumbers + 1;
    }

    private static void addLast() {
        numbers[numberOfNumbers] = commandReader.nextInt();
    }

    private static void addFirst() {
        for (int x = numberOfNumbers; x > 0; x = x - 1) {
            numbers[x] = numbers[x - 1];
        }
        numbers[0] = commandReader.nextInt();
    }
}
