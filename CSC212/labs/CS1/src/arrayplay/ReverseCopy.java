
/*
 * Program featuring straight up arrays and file IO to read and reverse copy a lyric.
 */

package arrayplay;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class ReverseCopy {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        String inputFileName = "RoarOfTheJungleDragon.text";
        String outputFileName = "RoarOfTheJungleDragonReversed.text";
        String[] words = readWordsFromFile(inputFileName);
        writeWordsToFile(words,outputFileName);
    }

    private static final int LIMIT = 1000;

    private static String[] readWordsFromFile(String inputFileName) throws FileNotFoundException {
        // Equate a scanner with the input file
        Scanner scanner = establishScanner(inputFileName);
        // Read the words from the file into an oversized array
        String[] temp = new String[LIMIT];
        int index = 0;
        while (scanner.hasNext()) {
            String word = scanner.next();
            temp[index] = word;
            index = index + 1;
        }
        int wordCount = index;
        // Transfer the words to a perfectly sized array
        String[] words = new String[wordCount];
        for (int x = 0; x < wordCount; x = x + 1) {
            words[x] = temp[x];
        }
        // Return the words
        return words;
    }

    private static void writeWordsToFile(String[] words, String outputFileName) throws IOException {
        // Equate a printer with an output file
        PrintWriter printer = getPrintWriter(outputFileName);
        // Print the words to the file
        for (int x = words.length-1; x >= 0; x = x - 1) {
            printer.println(words[x]);
        }
        printer.close();
    }

    private static Scanner establishScanner(String inputFileName) throws FileNotFoundException {
        String fullFileName = createFullFileName(inputFileName);
        return new Scanner(new File(fullFileName));
    }

    private static PrintWriter getPrintWriter(String outputFileName) throws FileNotFoundException {
        String fullFileName = createFullFileName(outputFileName);
        PrintWriter printer = new PrintWriter(fullFileName);
        return printer;
    }

    // Create the full file name for a simple file name, assuming that it will be
    // found in the CS1Files/data subdirectory of the user’s home directory.
    private static String createFullFileName(String fileName) {
        String separator = System.getProperty("file.separator");
        String home = System.getProperty("user.home");
        String path = home + separator + "CS1Files" + separator + "data" + separator;
        String fullFileName = path + fileName;
        return fullFileName;
    }
}
