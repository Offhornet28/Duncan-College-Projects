
/*
    CSC Spring 2023
    Programming Assignment #: 6
    Name: Duncan Zaug
    ID: 806107062
 */

package SmartHome;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.Properties;
import java.util.Scanner;
import javax.json.*;
import static java.lang.Integer.parseInt;
import java.lang.Object;
import Utility.Benchmark;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Arrays;

public class SmartSystem {
    public static ArrayList<String> fileData = new ArrayList<>();
    public static LinkedList<String> activity01Data = new LinkedList<>();
    public static LinkedList<String> activity02Data = new LinkedList<>();
    public static LinkedList<String> activity03Data = new LinkedList<>();

    public static void main (String[] args) throws Exception {
        System.out.println("SmartHome System is running now");

        // Properties
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream("config.properties");
        prop.load(fis);

        // Loading sensor data in Json file which is placed in src/Data/sensors.json
        String sensorData = "sensors";
        String sensorFilePath = prop.getProperty("filepath") + File.separator + "Data" + File.separator + sensorData + ".json";
        File sensorFile = new File(sensorFilePath);
        System.out.println("Sensor data has been loaded.");
        InputStream inputStream = new FileInputStream(sensorFile);
        JsonReader sensorReader = Json.createReader(inputStream);
        JsonObject sensorObject = sensorReader.readObject();
        sensorReader.close();
        inputStream.close();

        //Loading room data in Json file which is placed in src/Data/spaces.json
        String roomData = "spaces";
        String roomFilePath = prop.getProperty("filepath") + File.separator + "Data" + File.separator + roomData + ".json";
        File roomFile = new File(roomFilePath);
        System.out.println("Room data has been loaded");
        InputStream inputStream1 = new FileInputStream(roomFile);
        JsonReader roomReader = Json.createReader(inputStream1);
        JsonObject roomObject = roomReader.readObject();
        roomReader.close();
        inputStream1.close();

        //Loading sensory data in src/Data/senData.txt
        String sensoryData = "senData";
        String sensoryFilePath = prop.getProperty("filepath") + File.separator + "Data" + File.separator + sensoryData + ".txt";
        File inputFile1 = new File(sensoryFilePath);
        System.out.println("Sensory data has been loaded.");
        //Read Sensory data
        Scanner fileReader1 = new Scanner(inputFile1);
        while(fileReader1.hasNext()) {
            fileData.add(fileReader1.nextLine());
        }

        //Loading activity data in src/Data/activity_a01.txt
        String activity01 = "activity_a01";
        String activity01FilePath = prop.getProperty("filepath") + File.separator + "Data" + File.separator + activity01 + ".txt";
        File inputFile2 = new File((activity01FilePath));
        System.out.println("activity_a01 data has been loaded.");
        //Read activity_a01 data
        Scanner fileReader2 = new Scanner(inputFile2);
        while(fileReader2.hasNext()) {
            activity01Data.add(fileReader2.nextLine());
        }

        //Loading activity data in src/Data/activity_a02.txt
        String activity02 = "activity_a02";
        String activity02FilePath = prop.getProperty("filepath") + File.separator + "Data" + File.separator + activity02 + ".txt";
        File inputFile3 = new File((activity02FilePath));
        System.out.println("activity_a02 data has been loaded.");
        //Read activity_a02 data
        Scanner fileReader3 = new Scanner(inputFile3);
        while(fileReader3.hasNext()) {
            activity02Data.add(fileReader3.nextLine());
        }

        //Loading activity data in src/Data/activity_a03.txt
        String activity03 = "activity_a03";
        String activity03FilePath = prop.getProperty("filepath") + File.separator + "Data" + File.separator + activity03 + ".txt";
        File inputFile4 = new File((activity03FilePath));
        System.out.println("activity_a03 data has been loaded.");
        //Read activity_a03 data
        Scanner fileReader4 = new Scanner(inputFile4);
        while(fileReader4.hasNext()) {
            activity03Data.add(fileReader4.nextLine());
        }

        //Startup
        System.out.println("Owner: " + sensorObject.getString("Owner"));
        System.out.println("Location: " + sensorObject.getString("Location"));
        System.out.println("Phone: " + sensorObject.getString("Phone"));

        //User Menu
        Scanner userInput = new Scanner(System.in);
        int uInput;
        while (true) {
            try {
                System.out.print("\nChoose an option from menu [1:view 2:analysis 3:sensors 4:Rooms 5:Search 6:Evaluate 0:quit]: ");
                uInput = userInput.nextInt();
                if (uInput == 1) {
                    System.out.println("View: ");
                    view();
                } else if (uInput == 2) {
                    System.out.println("Analysis: ");
                    analysis();
                } else if (uInput == 3) {
                    System.out.println("Sensors: ");
                    sensors(sensorObject);
                } else if (uInput == 4){
                    System.out.println("Rooms: ");
                    rooms(sensorObject, roomObject);
                } else if (uInput == 5) {
                    System.out.println("Search: ");
                    search(sensorObject);
                } else if (uInput == 6){
                    System.out.println("Evaluate: ");
                    evaluate();
                }
                else if (uInput == 0) {
                    System.out.println("Quit: ");
                    break;
                } else {
                    System.out.println("invalid input, try again");
                }
            } catch (InputMismatchException invalidInputException) {
                //to catch if the input is not a number
                userInput.nextLine();
                System.out.println("invalid input, try again");
            }
        }
        userInput.close();
    }

    //view
    private static void view() {
        String[] fileDataArray = new String[fileData.size()];
        for(int i = 0; i < fileData.size(); ++i) {
            fileDataArray[i] = fileData.get(i);
        }
        System.out.println("Time                   ID    Value");
        String[] splitData;
        int x = 0;
        while (x < fileDataArray.length) {
            splitData = fileDataArray[x].split(", ");
            System.out.println(splitData[0] + "   " + splitData[1] + "   " + splitData[2]);
            ++x;
        }
    }

    //analysis part 1
    private static void analysis() {
        //sort the data
        String[] sortedData = new String[fileData.size()];
        for(int i = 0; i < fileData.size(); ++i) {
            sortedData[i] = fileData.get(i);
        }
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
    private static void sensors(JsonObject sensorObject) {
        System.out.println("ID" + addSpaces(4) + "Type" + addSpaces(10) + "Object" +  addSpaces(10) + "Location" + addSpaces(5) + "Status" + addSpaces(3) + "Default");
        JsonArray sensors = sensorObject.getJsonArray("Sensors");
        for(JsonValue sensor : sensors) {
            String id = sensor.asJsonObject().getString("ID");
            String type = sensor.asJsonObject().getString("Type");
            String object = sensor.asJsonObject().getString("Object");
            String location = sensor.asJsonObject().getString("Location");
            double status = sensor.asJsonObject().getJsonNumber("Status").doubleValue();
            double defaultStatus = sensor.asJsonObject().getJsonNumber("Default Status").doubleValue();
            int statusLength;
            if (status >= 10) {statusLength = 4;} else {statusLength = 3;}
            System.out.println(id + addSpaces(3) + type + addSpaces(14 - type.length()) + object + addSpaces(16 - object.length()) + location + addSpaces(13 - location.length()) +
                    status +  addSpaces(9 - statusLength)+ defaultStatus);
        }
    }

    //Rooms
    private static void rooms(JsonObject sensorObject, JsonObject roomObject) {
        JsonArray sensors = sensorObject.getJsonArray("Sensors");
        JsonArray rooms = roomObject.getJsonArray("Rooms");
        for(JsonValue room : rooms) {
            String roomName = room.asJsonObject().getString("Name");
            int numOfSensors = room.asJsonObject().getInt("Number of Sensors");
            if(numOfSensors == 0) {
                System.out.println(" [" + roomName + "]  "  + numOfSensors + " Sensors");
            } else {
                System.out.println(" [" + roomName + "]  "  + numOfSensors + " Sensors");
                System.out.println("ID" + addSpaces(4) + "Type" + addSpaces(10) + "Object" +  addSpaces(10) + "Location" + addSpaces(5) + "Status" + addSpaces(3) + "Default");
                JsonArray roomSensorsJson = room.asJsonObject().getJsonArray("Sensors");
                Object[] roomSensors = roomSensorsJson.toArray();
                for (Object sensorID : roomSensors) {
                    String roomSensor = sensorID.toString();
                    int roomSensorValue = parseInt( roomSensor.substring( roomSensor.lastIndexOf('s') + 1, roomSensor.lastIndexOf('"') ) );
                    for(JsonValue sensor : sensors) {
                        String sensorValueText =  sensor.asJsonObject().getString("ID");
                        int sensorValue  = parseInt( sensorValueText.substring( sensorValueText.lastIndexOf('s') + 1 ) );
                        if(roomSensorValue == sensorValue) {
                            String id = sensor.asJsonObject().getString("ID");
                            String type = sensor.asJsonObject().getString("Type");
                            String object = sensor.asJsonObject().getString("Object");
                            String location = sensor.asJsonObject().getString("Location");
                            double status = sensor.asJsonObject().getJsonNumber("Status").doubleValue();
                            double defaultStatus = sensor.asJsonObject().getJsonNumber("Default Status").doubleValue();
                            int statusLength;
                            if (status >= 10) {statusLength = 4;} else {statusLength = 3;}
                            System.out.println(id + addSpaces(3) + type + addSpaces(14 - type.length()) + object + addSpaces(16 - object.length()) + location + addSpaces(13 - location.length()) +
                                    status +  addSpaces(9 - statusLength)+ defaultStatus);
                        }
                    }
                }

            }
        }
    }

    //search
    private static void search(JsonObject sensorObject) {
        JsonArray sensors = sensorObject.getJsonArray("Sensors");
        //get sensor input and convert
        Scanner sensorInput = new Scanner(System.in);
        System.out.print("\nSensor ID to search for: ");
        String stringSensorInput = sensorInput.next();
        int sInput;
        try {
            sInput = parseInt( stringSensorInput.substring(stringSensorInput.lastIndexOf('s') + 1, 3) );
        } catch (NumberFormatException sCaseException) {
            try {
                sInput = parseInt( stringSensorInput.substring(stringSensorInput.lastIndexOf('S') + 1, 3) );
            } catch(NumberFormatException improperInputException) {
                //No event found exception 1
                System.out.println("No Sensor Found");
                return;
            }
        } catch (StringIndexOutOfBoundsException shortInputException) {
            //No event found exception 2
            System.out.println("No Sensor Found");
            return;
        }
        //linear search
        Benchmark.setCounterLS(0);
        String linearSearchResult = "";
        for(JsonValue sensor : sensors) {
            String id = sensor.asJsonObject().getString("ID");
            String type = sensor.asJsonObject().getString("Type");
            String object = sensor.asJsonObject().getString("Object");
            String location = sensor.asJsonObject().getString("Location");
            double status = sensor.asJsonObject().getJsonNumber("Status").doubleValue();
            double defaultStatus = sensor.asJsonObject().getJsonNumber("Default Status").doubleValue();
            int IDValue = parseInt( id.substring(id.lastIndexOf('s') + 1) );
            int statusLength;
            if (status >= 10) {statusLength = 4;} else {statusLength = 3;}
            Benchmark.increaseCounterLS();
            if(IDValue == sInput) {
                linearSearchResult = id + addSpaces(3) + type + addSpaces(14 - type.length()) + object + addSpaces(16 - object.length()) + location + addSpaces(13 - location.length()) +
                        status +  addSpaces(9 - statusLength) + defaultStatus;
                break;
            }
        }
        //binary search
        int first = 1;
        int last = sensorObject.getInt("Number of Sensors");
        int mid;
        Benchmark.setCounterBS(0);
        while(first < last) {
            Benchmark.increaseCounterBS();
            mid = (first + last) / 2;
            if (sInput > mid) {
                first = mid + 1;
            } else {
                last = mid;
            }
        }
        JsonObject foundSensor = sensors.getJsonObject(first - 1);
        String id = foundSensor.asJsonObject().getString("ID");
        String type = foundSensor.asJsonObject().getString("Type");
        String object = foundSensor.asJsonObject().getString("Object");
        String location = foundSensor.asJsonObject().getString("Location");
        double status = foundSensor.asJsonObject().getJsonNumber("Status").doubleValue();
        double defaultStatus = foundSensor.asJsonObject().getJsonNumber("Default Status").doubleValue();
        int statusLength;
        if (status >= 10) {statusLength = 4;} else {statusLength = 3;}
        String binarySearchResult = id + addSpaces(3) + type + addSpaces(14 - type.length()) + object + addSpaces(16 - object.length()) + location + addSpaces(13 - location.length()) +
                status +  addSpaces(9 - statusLength) + defaultStatus;
        //no event found exception 3
        if(!linearSearchResult.equals(binarySearchResult)) {
            System.out.println("No Sensor Found");
            return;
        }
        //print results
        System.out.println("ID" + addSpaces(4) + "Type" + addSpaces(10) + "Object" +  addSpaces(10) + "Location" + addSpaces(5) + "Status" + addSpaces(3) + "Default");
        System.out.println(linearSearchResult);
        System.out.println(addSpaces(6) + "[BENCHMARK RESULTS]");
        System.out.println("By Linear Search: " + Benchmark.getCounterLS() + " Comparisons");
        System.out.println("By Binary Search: " + Benchmark.getCounterBS() + " Comparisons");
    }

    //evaluate
    private static void evaluate() {
        //Converting Data to arrays to use the split function
        String[] activity01Array = activity01Data.toArray(new String[0]);
        String[] activity02Array = activity02Data.toArray(new String[0]);
        String[] activity03Array = activity03Data.toArray(new String[0]);
        String[] splitData;
        //activity_a01
        System.out.println("\n [Activity 01 - Sleeping]");
        for (String s : activity01Array) {
            splitData = s.split(", ");
            System.out.println(splitData[0] + "  " + splitData[1]);
        }
        //activity_a02
        System.out.println(" [Activity 02 - Taking Shower]");
        for (String s : activity02Array) {
            splitData = s.split(", ");
            System.out.println(splitData[0] + "  " + splitData[1]);
        }
        //activity_a03
        System.out.println(" [Activity 03 - Making Breakfast]");
        for (String s : activity03Array) {
            splitData = s.split(", ");
            System.out.println(splitData[0] + "  " + splitData[1]);
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
