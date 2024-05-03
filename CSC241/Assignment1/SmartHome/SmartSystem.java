
/*
    CSC Spring 2023
    Programming Assignment #: 1
    Name: Duncan Zaug
    ID: 806107062
 */

package SmartHome;
import java.io.*;
import java.nio.file.FileSystems;
import java.util.Scanner;
import static java.lang.Integer.parseInt;

public class SmartSystem {
    public static String[] fileData = new String[10];

    public static void main (String[] args) throws IOException {
        //Opening sensor data in src/Data/senData.txt
        String sensorData = "senData";
        String filePath = FileSystems.getDefault().getPath(System.getProperty("user.dir"),"src", "Data", sensorData +".txt").toAbsolutePath().toString();
        File inputFile = new File(filePath);

        //Read the data
        Scanner fileReader = new Scanner(inputFile);
        int i = 0;
        while(fileReader.hasNext()) {
            fileData[i] = fileReader.nextLine();
            ++i;
        }
        fileReader.close();
        System.out.println("Sensor Data Read:");

        //Startup
        System.out.println("Time                   ID    Value");
        String[] splitData;
        int x = 0;
        while (x < fileData.length) {
            splitData = fileData[x].split(", ");
            System.out.println(splitData[0] + "   " + splitData[1] + "   " + splitData[2]);
            ++x;
        }

        //User Menu
        Scanner userInput = new Scanner(System.in);
        int uInput;
        while (true) {
            System.out.print("\nChoose an option from menu [1:view 2:analysis 0:quit]: ");
            uInput = userInput.nextInt();
            if (uInput == 1) {
                System.out.println("View: ");
                view();
            } else if (uInput == 2) {
                System.out.println("Analysis: ");
                analysis();
            } else if (uInput == 0) {
                System.out.println("Quit: ");
                break;
            } else {
                System.out.println("invalid input, try again");
            }
        }
        userInput.close();
    }


    //view
    private static void view() {
        System.out.println("Time                   ID    Value");
        String[] splitData;
        int x = 0;
        while (x < fileData.length) {
            splitData = fileData[x].split(", ");
            System.out.println(splitData[0] + "   " + splitData[1] + "   " + splitData[2]);
            ++x;
        }
    }

    //analysis part 1
    private static void analysis() {
        //sort the data
        String[] sortedData = new String[10];
        System.arraycopy(fileData, 0, sortedData, 0, fileData.length);
        for(int i = sortedData.length - 1; i >= 0; --i) {
            int maxIdx = maxValueIndex(sortedData, i);
            String temp = sortedData[maxIdx];
            sortedData[maxIdx] = sortedData[i];
            sortedData[i] = temp;
        }
        //display the data
        String[] splitData;
        int x = 0;
        while (x < sortedData.length) {
            String displayGroup = sortedData[x].substring(sortedData[x].lastIndexOf("s"), sortedData[x].lastIndexOf(",") );
            if (x != 0) {
                int previous = parseInt(sortedData[x - 1].substring(sortedData[x - 1].lastIndexOf("s") + 1, sortedData[x - 1].lastIndexOf(",") ) );
                int current = parseInt(sortedData[x].substring(sortedData[x].lastIndexOf("s") + 1, sortedData[x].lastIndexOf(",") ) );
                if(current > previous) {
                    System.out.println("     [" + displayGroup + "]  " + getEventCount(sortedData, displayGroup) + " event(s)");
                    System.out.println("Time                   ID    Value");
                }
            } else {
                System.out.println("     [" + displayGroup + "]  " + getEventCount(sortedData, displayGroup) + " event(s)");
                System.out.println("Time                   ID    Value");
            }
            splitData = sortedData[x].split(", ");
            System.out.println(splitData[0] + "   " + splitData[1] + "   " + splitData[2]);
            ++x;
        }
    }

    //analysis part 2
    private static int maxValueIndex(String[] sortedData, int limit) {
        int max = parseInt(sortedData[0].substring( sortedData[0].lastIndexOf("s") + 1, sortedData[0].lastIndexOf(",") ) );
        int maxIdx = 0;
        for(int idx = 0; idx <= limit; ++idx) {
            int min = parseInt(sortedData[idx].substring( sortedData[idx].lastIndexOf("s") + 1, sortedData[idx].lastIndexOf(",") ) );
            if (min > max) {
                max = min;
                maxIdx = idx;
            }
        }
        return maxIdx;
    }

    //analysis part 3
    private static int getEventCount(String[] sortedData, String uEvent) {
        int count = 0;
        for (String sortedDatum : sortedData) {
            String currentGroup = sortedDatum.substring(sortedDatum.lastIndexOf("s"), sortedDatum.lastIndexOf(",") );
            if (currentGroup.equals(uEvent)) {
                ++count;
            }
        }
        return count;
    }
}
