
/*
    CSC Spring 2023
    Programming Assignment #: 4
    Name: Duncan Zaug
    ID: 806107
 */

package SmartHome;
import java.lang.Object;
import java.util.ArrayList;

abstract class Sensor {
    protected double defaultStatus;               // default status
    protected double currentStatus;               // current status
    protected String ID;
    protected String object;
    protected String location;

    Sensor (String ID, String object, String location, double defaultStatus, double currentStatus) {
        this.ID = ID;
        this.object = object;
        this.location = location;
        this.defaultStatus = defaultStatus;
        this.currentStatus = currentStatus;
    }
    abstract Object get(String request);
    abstract void changeStatus (double change);
}

//abstract class usage
class LightSensor extends Sensor {
    LightSensor(String ID, String object, String location, double defaultStatus, double currentStatus) {
        super(ID, object, location, defaultStatus, currentStatus);
    }
    Object get(String request) {
        if(request.equalsIgnoreCase("id")) {
            return this.ID;
        } else if(request.equalsIgnoreCase("type")) {
            return "light";
        } else if(request.equalsIgnoreCase("object")) {
            return this.object;
        } else if(request.equalsIgnoreCase("location")) {
            return this.location;
        } else if(request.equalsIgnoreCase("default")) {
            return this.defaultStatus;
        } else if (request.equalsIgnoreCase("current")) {
            return this.currentStatus;
        }
        return null;
    }
    void changeStatus (double Change) {
        this.currentStatus = Change;
    }
}

class PressureSensor extends Sensor {
    PressureSensor(String ID, String object, String location, double defaultStatus, double currentStatus) {
        super(ID, object, location, defaultStatus, currentStatus);
    }
    Object get(String request) {
        if(request.equalsIgnoreCase("id")) {
            return this.ID;
        } else if(request.equalsIgnoreCase("type")) {
            return "pressure";
        } else if(request.equalsIgnoreCase("object")) {
            return this.object;
        } else if(request.equalsIgnoreCase("location")) {
            return this.location;
        } else if(request.equalsIgnoreCase("default")) {
            return this.defaultStatus;
        } else if (request.equalsIgnoreCase("current")) {
            return this.currentStatus;
        }
        return null;
    }
    void changeStatus (double Change) {
        this.currentStatus = Change;
    }
}

class ContactSensor extends Sensor {
    ContactSensor(String ID, String object, String location, double defaultStatus, double currentStatus){
        super(ID, object, location, defaultStatus, currentStatus);
    }
    Object get(String request) {
        if(request.equalsIgnoreCase("id")) {
            return this.ID;
        } else if(request.equalsIgnoreCase("type")) {
            return "contact";
        } else if(request.equalsIgnoreCase("object")) {
            return this.object;
        } else if(request.equalsIgnoreCase("location")) {
            return this.location;
        } else if(request.equalsIgnoreCase("default")) {
            return this.defaultStatus;
        } else if (request.equalsIgnoreCase("current")) {
            return this.currentStatus;
        }
        return null;
    }
    void changeStatus (double Change) {
        this.currentStatus = Change;
    }
}

class MotionSensor extends Sensor {
    MotionSensor(String ID, String object, String location, double defaultStatus, double currentStatus) {
        super(ID, object, location, defaultStatus, currentStatus);
    }
    Object get(String request) {
        if(request.equalsIgnoreCase("id")) {
            return this.ID;
        } else if(request.equalsIgnoreCase("type")) {
            return "motion";
        } else if(request.equalsIgnoreCase("object")) {
            return this.object;
        } else if(request.equalsIgnoreCase("location")) {
            return this.location;
        } else if(request.equalsIgnoreCase("default")) {
            return this.defaultStatus;
        } else if (request.equalsIgnoreCase("current")) {
            return this.currentStatus;
        }
        return null;
    }
    void changeStatus (double Change) {
        this.currentStatus = Change;
    }
}

class TemperatureSensor extends Sensor {
    TemperatureSensor(String ID, String object, String location, double defaultStatus, double currentStatus) {
        super(ID, object, location, defaultStatus, currentStatus);
    }
    Object get(String request) {
        if(request.equalsIgnoreCase("id")) {
            return this.ID;
        } else if(request.equalsIgnoreCase("type")) {
            return "temperature";
        } else if(request.equalsIgnoreCase("object")) {
            return this.object;
        } else if(request.equalsIgnoreCase("location")) {
            return this.location;
        } else if(request.equalsIgnoreCase("default")) {
            return this.defaultStatus;
        } else if (request.equalsIgnoreCase("current")) {
            return this.currentStatus;
        }
        return null;
    }
    void changeStatus (double Change) {
        this.currentStatus = Change;
    }
}

//Interface usage
class Bedroom implements Room {
    private String name;
    private ArrayList<String> sensors;
    Bedroom(String name, ArrayList<String> sensors) {
        this.name = name;
        this.sensors = sensors;
    }
    public String getSensor(String sensorName) {
        return sensors.get(sensors.indexOf(sensorName));
    }
    public String getName() {
        return this.name;
    }
    public int getNumOfSensors() {
        return sensors.size();
    }
    public void deploySensor(Sensor sensor) {
        String sensorToAdd = sensor.get("ID").toString();
        sensors.add(sensorToAdd);
    }
    public void removeSensor(Sensor sensor) {
        String sensorToRemove = sensor.get("ID").toString();
        sensors.remove(sensorToRemove);
    }
}

class Bathroom implements Room {
    private String name;
    private ArrayList<String> sensors;
    Bathroom(String name, ArrayList<String> sensors) {
        this.name = name;
        this.sensors = sensors;
    }
    public String getSensor(String sensorName) {
        return sensors.get(sensors.indexOf(sensorName));
    }
    public String getName() {
        return this.name;
    }
    public int getNumOfSensors() {
        return sensors.size();
    }
    public void deploySensor(Sensor sensor) {
        String sensorToAdd = sensor.get("ID").toString();
        sensors.add(sensorToAdd);
    }
    public void removeSensor(Sensor sensor) {
        String sensorToRemove = sensor.get("ID").toString();
        sensors.remove(sensorToRemove);
    }
}

class LivingRoom implements Room {
    private String name;
    private ArrayList<String> sensors;
    LivingRoom(String name, ArrayList<String> sensors) {
        this.name = name;
        this.sensors = sensors;
    }
    public String getSensor(String sensorName) {
        return sensors.get(sensors.indexOf(sensorName));
    }
    public String getName() {
        return this.name;
    }
    public int getNumOfSensors() {
        return sensors.size();
    }
    public void deploySensor(Sensor sensor) {
        String sensorToAdd = sensor.get("ID").toString();
        sensors.add(sensorToAdd);
    }
    public void removeSensor(Sensor sensor) {
        String sensorToRemove = sensor.get("ID").toString();
        sensors.remove(sensorToRemove);
    }
}

class Kitchen implements Room {
    private String name;
    private ArrayList<String> sensors;
    Kitchen(String name, ArrayList<String> sensors) {
        this.name = name;
        this.sensors = sensors;
    }
    public String getSensor(String sensorName) {
        return sensors.get(sensors.indexOf(sensorName));
    }
    public String getName() {
        return this.name;
    }
    public int getNumOfSensors() {
        return sensors.size();
    }
    public void deploySensor(Sensor sensor) {
        String sensorToAdd = sensor.get("ID").toString();
        sensors.add(sensorToAdd);
    }
    public void removeSensor(Sensor sensor) {
        String sensorToRemove = sensor.get("ID").toString();
        sensors.remove(sensorToRemove);
    }
}

class DiningRoom implements Room {
    private String name;
    private ArrayList<String> sensors;
    DiningRoom(String name, ArrayList<String> sensors) {
        this.name = name;
        this.sensors = sensors;
    }
    public String getSensor(String sensorName) {
        return sensors.get(sensors.indexOf(sensorName));
    }
    public String getName() {
        return this.name;
    }
    public int getNumOfSensors() {
        return sensors.size();
    }
    public void deploySensor(Sensor sensor) {
        String sensorToAdd = sensor.get("ID").toString();
        sensors.add(sensorToAdd);
    }
    public void removeSensor(Sensor sensor) {
        String sensorToRemove = sensor.get("ID").toString();
        sensors.remove(sensorToRemove);
    }
}


