
/*
    CSC Spring 2023
    Programming Assignment #: 3
    Name: Duncan Zaug
    ID: 806107062
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
