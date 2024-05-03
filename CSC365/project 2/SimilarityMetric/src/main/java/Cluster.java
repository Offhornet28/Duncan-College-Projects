import java.util.ArrayList;

public class Cluster
{
    private double currentValue;
    private ArrayList<String> businessNamesInCluster;
    public Cluster (double currentMiddleValue, ArrayList<String> businessNamesInCluster)
    {
        this.currentValue = currentMiddleValue;
        this.businessNamesInCluster = businessNamesInCluster;
    }

    public double getCurrentValue(){
        return currentValue;
    }

    public void setCurrentValue(double currentValue){
        this.currentValue = currentValue;
    }

    public ArrayList<String> getClusterNameArray(){
        return businessNamesInCluster;
    }
    public void addBusinessName(String name){
        businessNamesInCluster.add(name);
    }

    public void removeBusinessName(String name){
        businessNamesInCluster.remove(name);
    }

    public void overideCurrentArray(ArrayList<String> newBusinessNameArray){
        businessNamesInCluster = newBusinessNameArray;
    }

    public void clusterToString(){
        for(String value : businessNamesInCluster){
            System.out.println("BusinessName: " + value);
        }
    }
}
