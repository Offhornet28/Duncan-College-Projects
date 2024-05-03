
/*
    CSC Spring 2023
    Programming Assignment #: 2
    Name: Duncan Zaug
    ID: 806107062
 */

package SmartHome;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;
import javax.json.*;
import static java.lang.Integer.parseInt;

public class SmartSystem {

    public static String[] fileData = new String[10];

    public static void main (String[] args) throws Exception {
        System.out.println("SmartHome System is running now");

        // Properties
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream("config.properties");     // config.properties MUST be placed in the TOP directory of this project
        prop.load(fis);

        // Loading sensor data in Json file which is placed in src/Data/sensors.json
        String sensorData = "sensors";
        String sensorFilePath = prop.getProperty("filepath") + File.separator + "Data" + File.separator + sensorData + ".json";
        File sensorFile = new File(sensorFilePath);
        System.out.println("Sensor data has been loaded.");
        InputStream inputStream = new FileInputStream(sensorFile);
        JsonReader jsonReader = Json.createReader(inputStream);
        JsonObject jsonObject = jsonReader.readObject();
        jsonReader.close();
        inputStream.close();


        //Loading sensory data in src/Data/senData.txt
        String sensoryData = "senData";
        String sensoryFilePath = prop.getProperty("filepath") + File.separator + "Data" + File.separator + sensoryData + ".txt";
        File inputFile = new File(sensoryFilePath);
        System.out.println("Sensory data has been loaded.");
        //Read Sensory data
        Scanner fileReader = new Scanner(inputFile);
        int i = 0;
        while(fileReader.hasNext()) {
            fileData[i] = fileReader.nextLine();
            ++i;
        }
        //Startup
        System.out.println("Owner: " + jsonObject.getString("Owner"));
        System.out.println("Location: " + jsonObject.getString("Location"));
        System.out.println("Phone: " + jsonObject.getString("Phone"));

        //User Menu
        Scanner userInput = new Scanner(System.in);
        int uInput;
        while (true) {
            System.out.print("\nChoose an option from menu [1:view 2:analysis 3:sensors 0:quit]: ");
            uInput = userInput.nextInt();
            if (uInput == 1) {
                System.out.println("View: ");
                view();
            } else if (uInput == 2) {
                System.out.println("Analysis: ");
                analysis();
            } else if (uInput == 3) {
                System.out.println("Sensors: ");
                sensors(jsonObject);
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

    //sensors
    private static void sensors(JsonObject jsonObject) {
        System.out.println("ID" + addSpaces(4) + "Type" + addSpaces(10) + "Object" +  addSpaces(3) + "Location" + addSpaces(5) + "Status" + addSpaces(3) + "Default");
        JsonArray sensors = jsonObject.getJsonArray("Sensors");
        for(JsonValue sensor : sensors) {
            String id = sensor.asJsonObject().getString("ID");
            String type = sensor.asJsonObject().getString("Type");
            String object = sensor.asJsonObject().getString("Object");
            String location = sensor.asJsonObject().getString("Location");
            double status = sensor.asJsonObject().getJsonNumber("Status").doubleValue();
            double defaultStatus = sensor.asJsonObject().getJsonNumber("Default Status").doubleValue();
            int statusLength;
            if (status >= 10) {statusLength = 4;} else {statusLength = 3;}
            System.out.println(id + addSpaces(3) + type + addSpaces(14 - type.length()) + object + addSpaces(9 - object.length()) + location + addSpaces(13 - location.length()) +
                    status +  addSpaces(9 - statusLength)+ defaultStatus);
        }
    }

    //addSpaces
    private static String addSpaces(int numOfSpaces) {
        StringBuilder spaces = new StringBuilder();
        for(int i = 0; i <= numOfSpaces; ++i) {
            spaces.append(" ");
        }
        return spaces.toString();
    }
}
