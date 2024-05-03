import java.util.ArrayList;
import java.util.Random;
import java.util.Arrays;
import java.util.List;

public class K_Means
{
    public static int maxValue;
    public static int minValue;
    public static void GenerateInitialClusterValues(int k, ArrayList<Double> clusterStartValues, ArrayList<Double> UserData, ArrayList<ArrayList<Cluster>> cluster2dDataArray)
    {
        //Iterator
        int itter = 0;

        //Instantiate k amount of empty cluster arrays
        while(itter < k)
        {
            //Adds new integer array to 2d cluster data array
            cluster2dDataArray.add(new ArrayList<Cluster>());

            boolean goodIndexFound = false;
            Integer randomIndex = null;
            while(!goodIndexFound)
            {
                //****************Generating Initial Cluster Point associated with the cluster array above****************

                //Set start and end range
                int startRange = 0;
                int endRange = UserData.size() - 1;

                // Create a Random object
                Random random = new Random();

                // Generate a random integer within the range
                randomIndex = random.nextInt(endRange - startRange + 1) + startRange;

                //Checks if the index has already been used
                boolean indexIsUsed = clusterStartValues.contains(UserData.get(randomIndex));


                if(!indexIsUsed) {
                    goodIndexFound = true;
                }
            }

            // Value at the randomly generated index
            Double randomIndexValue = UserData.get(randomIndex);

            //Adds The Index to the ClusterValuesIndex array
            clusterStartValues.add(randomIndexValue);

            //Iterates the counter
            itter++;
        }
    }

    public static ArrayList<Double> CalculateTotalLoss(ArrayList<ArrayList<Double>> cluster2dDataArray, ArrayList<Double> currentStartValues)
    {
        ArrayList<Double> totalVariance = new ArrayList<Double>();
        for(int i = 0; i < cluster2dDataArray.size(); i++)
        {
            //Define sum
            double sum = 0;
            for(Double value : cluster2dDataArray.get(i))
            {
                //Calculates variance
                double variance = Math.pow((value - currentStartValues.get(i)), 2);
                sum += variance;
            }
            //Divide the sum by n
            double averageVariance = Math.sqrt(sum / cluster2dDataArray.size());
            totalVariance.add(averageVariance);
        }

        return totalVariance;
    }
    public static ArrayList<Double> AssignClusters(ArrayList<Double> data, ArrayList<Double> ClusterStartValues, ArrayList<ArrayList<Double>> Cluster2dDataArray)
    {
        //Clears all data from arrays
        for(ArrayList<Double> arr : Cluster2dDataArray){
            arr.clear();
        }

        //Adds data to their clusters
        for(Double value : data)
        {
            if(value > maxValue){
                //If its more assigns new max value
                maxValue = (int) value.doubleValue();
            }
            double smallestDistance = 99999999999.0;
            int closestClusterIndex = -1;
            for(int i = 0; i < ClusterStartValues.size(); i++)
            {
                double absDistance = Math.abs(ClusterStartValues.get(i) - value);
                if(absDistance < smallestDistance)
                {
                    closestClusterIndex = i;
                    smallestDistance = absDistance;
                }
            }

            Cluster2dDataArray.get(closestClusterIndex).add(value);
        }


        for(int j = 0; j < Cluster2dDataArray.size(); j++)
        {
            //Prints out new array
            System.out.println("ArrayList " + j + ": " + Cluster2dDataArray.get(j).toString());

            double sum = 0;
            for(Double value : Cluster2dDataArray.get(j))
            {
                sum += value;
            }
            double mean = sum / (double) Cluster2dDataArray.get(j).size();

            //Print Statements
            //System.out.println("Array " + j + " Sum = " + sum + " and " + sum + " / " + Cluster2dDataArray.get(j).size() + " = " + mean);

            //Sets new cluster start value
            ClusterStartValues.set(j, mean);
            System.out.println();
        }

        return CalculateTotalLoss(Cluster2dDataArray, ClusterStartValues);
    }


    public static void addRandomDoubles(ArrayList<Double> list, int k)
    {
        Random random = new Random();
        for (int i = 0; i < k; i++)
        {
            double randomDouble = 1 + random.nextDouble() * 10000;
            list.add(randomDouble); // Add the random double to the list
        }
    }


    public static void main(String[] args)
    {
        /*
        //Sets initial max and min values
        minValue = 0;
        maxValue = 1;

        //Double array to store Cluster Arrays
        ArrayList<ArrayList<Double>> Cluster2dDataArray = new ArrayList<ArrayList<Double>>();

        //Cluster Value arraylist, each value correlates to the actual Cluster point value of each cluster array
        ArrayList<Double> ClusterStartValues = new ArrayList<Double>();

        ArrayList<Double> data = new ArrayList<Double>();

        //Populates arraylist
        addRandomDoubles(data, 500);

//        data.add(26.0);
//        data.add(74.0);
//        data.add(96.0);
//        data.add(256.0);
//        data.add(261.0);


        //Number of Clusters
        int k = 6;

        //Generates Initial Clusters
        GenerateInitialClusterValues(k, ClusterStartValues, data, Cluster2dDataArray);

        //Arrays to save the previous data
        ArrayList<Double> TotalVarianceArray = new ArrayList<Double>();


        int numTimesRun = 5;
        for(int i = 0; i < numTimesRun; i++)
        {
            for(int m = 0; m < ClusterStartValues.size(); m++)
            {
                System.out.println("Cluster " + m + " Start Value: " + ClusterStartValues.get(m));
            }

            System.out.println();


            double PreviousTotalVariance = 0;

            for(Double value : TotalVarianceArray)
            {
                if(value != null)
                {
                    PreviousTotalVariance += value;
                }
                else
                {
                    PreviousTotalVariance = 99999;
                }
            }

            System.out.println();
            ArrayList<ArrayList<Double>> PreviousCluster2dDataArray = Cluster2dDataArray;

            //Assigns Clusters to the data and returns the new Total Variance
            TotalVarianceArray = AssignClusters(data, ClusterStartValues, Cluster2dDataArray);

            double NewTotalVariance = 0;

            //Calculates new Total Variance
            for(Double value : TotalVarianceArray)
            {
                if(value != null)
                {
                    NewTotalVariance += value;
                }
                else
                {
                    NewTotalVariance = 99999;
                }
            }

            System.out.println("Previous Variance: " + PreviousTotalVariance + " < " + NewTotalVariance);
            if(PreviousTotalVariance <= NewTotalVariance && PreviousTotalVariance != 0){
                System.out.println("Best Clusters Found");
                break;
            }




            for(int p = 0; p < TotalVarianceArray.size(); p++)
            {
                System.out.println("Array " + p + " - Total Variance: " + TotalVarianceArray.get(p));
            }

            System.out.println();
        }
        NumberLinePlotSwing.createNumberLine(Cluster2dDataArray, minValue, maxValue);
        */
    }
}
