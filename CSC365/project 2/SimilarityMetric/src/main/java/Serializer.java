import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Serializer
{
    public static void SerializeBusiness(String ID, String BusinessName, String CategoryList, String FrequencyList, String IDF_Value_List)
    {
        CategoryList = "{" + CategoryList + "}";
        FrequencyList = "{" + FrequencyList + "}";
        IDF_Value_List = "{" + IDF_Value_List + "}";
        String[] textLines = {ID, BusinessName, CategoryList, FrequencyList, IDF_Value_List};

        String filePath = "src/main/SerializedBusinesses/" + ID + ".csv";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath)))
        {
            // Writing data rows
            for (String attribute : textLines) {
                bw.write(attribute + ",");
            }
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void SerializeCluster(BusinessCluster cluster)
    {
        String filePath = "src/main/SerializedClusters/Cluster" + cluster.clusterIndex  + ".csv";
        ArrayList<String> names = cluster.namesInCluster;
        ArrayList<String> terms = cluster.businessTermsInCluster;
        ArrayList<Double> businessTotalSimilarity = cluster.businessTotalSimilarity;

        System.out.println("Name size: " + names.size() + " bussy size: " + businessTotalSimilarity.size());

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath)))
        {
            System.out.println("NamesSize: " + names.size());
            //Iterates Through All the lines
            for(int i = 0; i < names.size(); i++)
            {
                String currentName = names.get(i);
                String currentTerm = "{" + terms.get(i) + "}";
                String totalSimilarity = businessTotalSimilarity.get(i).toString();
                String[] textLine = {currentName, currentTerm, totalSimilarity};

                // Writing One line of Data
                for (String attribute : textLine) {
                    bw.write(attribute + ",");
                }
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Business readCSV(String rawFilePath)
    {
        String filePath = rawFilePath + ".csv";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String name = br.readLine().trim();
            String id = br.readLine().trim();
            String TermFequency = br.readLine().trim();
            String CategoryList = br.readLine().trim();

            return new Business(name, id, TermFequency, CategoryList);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
