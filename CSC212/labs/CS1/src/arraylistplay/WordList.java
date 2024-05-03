
/*
 * Program featuring an arrayList to store and interactively manipulate a list of words
 */

package arraylistplay;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class WordList {

    private static ArrayList<String> words = new ArrayList<>();
    private static Scanner commandReader = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            //Establish array of words
            readWords();
            //Check the Data
            //System.out.println("nThe original list of words ...");
            //displayWords();
            //Enter the Interpreter
            interpreter();
        } catch (FileNotFoundException ex) {
            System.out.println("The file was not found. Please think again.");
            System.exit(-1);
        }
    }

    private static Scanner establishScanner(String fn) throws FileNotFoundException {
        String separator = File.separator;
        String homeDirectory = System.getProperty("user.home");
        String path = homeDirectory + separator + "public_html" + separator + "data" + separator;
        String fullFileName = path + fn;
        return new Scanner(new File(fullFileName));
    }

    private static void readWords() throws FileNotFoundException {
        Scanner scanner = establishScanner("WordSet.txt");
        while (scanner.hasNext()) {
            words.add(scanner.next());
        }
    }

    private static void displayWords() {
        for (String word : words) {
            System.out.println(word);
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
        displayWords();
    }

    private static void interpretPrintCommand() {
        String operand = commandReader.next();
        if (operand.equalsIgnoreCase("FIRST")) {
            System.out.println(words.get(0));
        } else if (operand.equalsIgnoreCase("LAST")) {
            System.out.println(words.get(words.size() - 1));
        } else {
            int index = Integer.valueOf(operand);
            System.out.println(words.get(index - 1));
        }
    }

    private static void interpretHelpCommand() {
        System.out.println("HELP - display a menu of commands");
        System.out.println("DISPLAY - display the list of numbers");
        System.out.println("PRINT - print a word (FIRST;LAST;nth)");
        System.out.println("SWAP - exchange two elements (nth;mth)");
        System.out.println("ADD - add a word to the list (FIRST;LAST)");
        System.out.println("EXIT - terminate execution of the program");
    }

    private static void interpretSwapCommand() {
        int position1 = commandReader.nextInt() - 1;
        int position2 = commandReader.nextInt() - 1;
        String temp = words.get(position1);
        words.set( position1, words.get(position2) );
        words.set(position2, temp);
    }

    private static void interpretAddCommand() {
        String position = commandReader.next();
        if (position.equalsIgnoreCase("LAST")) {
            words.add(commandReader.next());
        } else if (position.equalsIgnoreCase("FIRST")) {
            words.add(0, commandReader.next());
        } else {
            System.out.println("### invalid operand for add command");
        }
    }
}
