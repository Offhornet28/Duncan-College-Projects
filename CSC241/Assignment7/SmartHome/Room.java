
/*
    CSC Spring 2023
    Programming Assignment #: 4
    Name: Duncan Zaug
    ID: 806107
 */

package SmartHome;

interface Room {
    // they are all public abstract methods
    void deploySensor(Sensor sensor);
    void removeSensor(Sensor sensor);
    String getName();
    String getSensor(String sensorName);
    int getNumOfSensors();
}

// TODO: Create classes implementing interface Room
//  There must be at least five classes: Bedroom, Bathroom, Livingroom, Kitchen, and Diningroom
