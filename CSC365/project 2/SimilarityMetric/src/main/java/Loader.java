import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.io.File;


public class Loader
{
    public static boolean DebugMode = false;
    private static double SimilarityScore = 0.0;

    private static double SimilarityScore2 = 0.0;

    public static void lineBreak()
    {
        System.out.println("\n--------------------------------------------------\n");
    }

    public static JSONArray readJSON(List<Double> starsList, List<String> categoriesList, String filePath)
    {
        try {
            // Read the entire file content as a single string
            String content = new String(Files.readAllBytes(Paths.get(filePath)));

            // Parse the string content into a JSONArray
            JSONArray jsonArray = new JSONArray(content);

            //You need this line because the length of the array changes in the for loop
            int i = 0;

            // Iterate over each element in the JSONArray
            while(i < jsonArray.length()) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                if (!jsonObject.isNull("stars") && !jsonObject.isNull("categories") && !jsonObject.isNull("name")) {
                    //Adds stars to star List
                    starsList.add(jsonObject.getDouble("stars"));

                    //**********************************Category's List Preprocessing**********************************

                    //Takes out spaces from each category Term
                    String tempCategoryString = "";
                    tempCategoryString = (jsonObject.getString("categories"));

                    //Makes String array of each term in the categoryLine
                    String[] categoryLine = tempCategoryString.split(",\\s*");

                    //Creates string builder
                    StringBuilder finalCategoryLine = new StringBuilder();

                    for (String term : categoryLine) {
                        //Strips trailing white spaces
                        term = term.strip();

                        //Replaces any spaces with _
                        term = term.replace(" ", "_").toUpperCase();
                        finalCategoryLine.append(term).append(",");
                    }
                    categoriesList.add(finalCategoryLine.toString());
                    i++;
                } else {
                    //System.out.println("Remove Object: " + jsonObject.getString("name"));
                    jsonArray.remove(i);
                }

            }

            return jsonArray;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static MyHashMap2<String, Integer> populateFrequencyHashTable(List<String> categoriesList)
    {
        MyHashMap2<String, Integer> frequencyTable = new MyHashMap2<>(categoriesList.size());

        int numwords = 0;
        //This method is to calculate the number of times a target word comes up
        for (String eachObject : categoriesList)
        {
            String[] categoryWords = eachObject.toUpperCase().split(",\\s*");

            //Iterates Through Words in the doc
            for (String word : categoryWords)
            {
                if (frequencyTable.get(word) != null)
                {
                    //Adds one to term frequency
                    Integer termCount = frequencyTable.get(word);

                    frequencyTable.put(word, termCount + 1);

                    numwords++;

                } else {
                    //First Time Seeing Term
                    frequencyTable.put(word, 1);
                    numwords++;
                }
            }
        }

        //Sets the number of words in the entire corpus
        frequencyTable.setNumWords(numwords);
        return frequencyTable;
    }

    public static double getSimilarityScore()
    {
        return SimilarityScore;
    }

    public static double getSimilarityScore2()
    {
        return SimilarityScore2;
    }

    public static void deleteFilesInFolder(String folderPath) {
        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files)
            {
                if (file.isFile())
                {
                    file.delete();
                }
            }
        }
        else
        {
            System.out.println("File Delete Error");
        }
    }
    public static void GenerateSerializedBusinesses(JSONArray business_JSON_Array, List<String> categoriesList) throws IOException {
        //------------------------------Read JSON File----------------------------------


        MyHashMap2<String, Integer> frequencyHashTable = populateFrequencyHashTable(categoriesList);

        frequencyHashTable.serializeTable("Frequency_Table", "src/main/SerializedHashMaps/");

        int totalDocuments = frequencyHashTable.getNumWords();

        if(business_JSON_Array != null)
        {
            MyHashMap2<String, String> businessHashTable = new MyHashMap2<>(business_JSON_Array.length());
            for (int i = 0; i < business_JSON_Array.length(); i++)
            {
                JSONObject obj = business_JSON_Array.getJSONObject(i);
                String name = "";
                String categories = "";

                categories = categoriesList.get(i);
                name = obj.getString("name");

                name = name.strip();
                //Re-formats Name
                name = name.replace(",", "");

                //Serialize the term frequency
                String[] categoryLine = categories.split(",\\s*");

                StringBuilder TermFrequencies = new StringBuilder();
                StringBuilder IDFValues = new StringBuilder();

                for (String term : categoryLine)
                {
                    int TF = -1;
                    double IDF_Value = -1.0;
                    if(frequencyHashTable.get(term) != null) {
                        TF = frequencyHashTable.get(term);

                        IDF_Value = Math.log(totalDocuments / (double) TF);
                    }
                    else
                    {
                        System.out.println("Could not parse: " + term.strip());
                    }

                    if(TF != -1)
                    {
                        TermFrequencies.append(TF);
                        TermFrequencies.append(",");
                    }

                    if(IDF_Value != -1)
                    {
                        IDFValues.append(IDF_Value);
                        IDFValues.append(",");
                    }
                    else
                    {
                        System.out.println("IDF Error");
                    }
                }

                //Adds business name and ID to Hashmap
                String ID = obj.getString("business_id");
                //System.out.println("ID: " + ID);
                businessHashTable.put(name, ID);

                //Serializes this business file
                Serializer.SerializeBusiness(ID, name, categories, TermFrequencies.toString(), IDFValues.toString());
            }
            //Serializes the Table of business ID and Names
            businessHashTable.serializeTable("Business_Table", "src/main/SerializedHashMaps/");
        }
    }

    public static MyHashMap2<String, Integer> LoadTFHashMap(String rawFilePath)
    {
        String filePath = rawFilePath + ".csv";

        //Counts Lines
        int lineCount = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while (br.readLine() != null) {
                lineCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        //Creates hash map
        MyHashMap2<String, Integer> tempHashMap = new MyHashMap2<>(lineCount);

        //Loads Data into hashmap
        try (BufferedReader br = new BufferedReader(new FileReader(filePath)))
        {
            String line;
            while((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String Key = parts[0].trim();
                Integer Value = Integer.valueOf(parts[1].trim());
                tempHashMap.put(Key, Value);
            }
        }
        catch (IOException e)
        {
            System.out.println("Error: ");
            e.printStackTrace();
            return null;
        }

        return tempHashMap;
    }
    public static MyHashMap2<String, String> LoadBusinessHashMap(String rawFilePath)
    {
        String filePath = rawFilePath + ".csv";

        //Counts Lines
        int lineCount = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while (br.readLine() != null) {
                lineCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        //Creates hash map
        MyHashMap2<String, String> tempHashMap = new MyHashMap2<>(lineCount);

        //Loads Data into hashmap
        try (BufferedReader br = new BufferedReader(new FileReader(filePath)))
        {
            String line;
            while((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String Key = parts[0].trim();
                String Value = parts[1].trim();

                tempHashMap.put(Key, Value);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }

        return tempHashMap;
    }

    public static ArrayList<ArrayList<String>> ParseCategoryArray(String clusterID)
    {
        String filePath = "src/main/SerializedBusinesses/" + clusterID + ".csv";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath)))
        {
            //Reads first Lines
            String line = reader.readLine();

            //Converts line to char array
            char[] lineCharArr = line.toCharArray();

            int correctArrayIndex = 1;
            int startIndex = -1;
            int endIndex = -1;

            ArrayList<ArrayList<String>> tempArrayHolder = new ArrayList<ArrayList<String>>();

            //Finds start and end index
            for (int i = 0; i < lineCharArr.length; i++)
            {
                if (lineCharArr[i] == '{') {
                    startIndex = i;
                }
                else if (lineCharArr[i] == '}')
                {
                    String arrayLine = line.substring(startIndex + 1, i);

                    String[] splitArray = arrayLine.split(",");

                    ArrayList<String> terms = new ArrayList<String>(Arrays.asList(splitArray));
                    tempArrayHolder.add(terms);
                }
            }

            return tempArrayHolder;

        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
            //If it got this far something messed up
            return null;
        }
    }

    public static ArrayList<BusinessCluster> GenerateNewClusters(List<String> categoriesList, JSONArray jsonArray, int numClusters, MyHashMap2<String, String> businessHashMap)
    {
        ArrayList<BusinessCluster> parentClusterHolder = new ArrayList<BusinessCluster>();

        int itter = 0;
        ArrayList<Integer> BadIndexes = new ArrayList<Integer>();

        //Instantiate k amount of empty cluster arrays
        while(itter < numClusters)
        {
            boolean goodIndexFound = false;
            Integer randomIndex = null;
            while(!goodIndexFound)
            {
                //****************Generating Initial Cluster Point associated with the cluster array above****************

                // Create a Random object
                Random random = new Random();

                // Generate a random integer within the range
                randomIndex = random.nextInt(jsonArray.length());

                //Checks if the index has already been used
                boolean indexIsUsed = BadIndexes.contains(randomIndex);

                if(!indexIsUsed)
                {
                    goodIndexFound = true;
                }

                String checkNumTerms = jsonArray.getJSONObject(randomIndex).getString("categories");

                String[] terms = checkNumTerms.split(",");

                if(terms.length < 7){
                    goodIndexFound = false;
                    BadIndexes.add(randomIndex);
                }
            }

            //Adds The Index to the ClusterValuesIndex array
            BadIndexes.add(randomIndex);

            //Gets Cluster Name
            String clusterName = jsonArray.getJSONObject(randomIndex).getString("name");

            //Re-formats Name
            clusterName = clusterName.strip();
            clusterName = clusterName.replace(",", "");
            //Creates a temporary cluster
            BusinessCluster tempCluster = new BusinessCluster(clusterName);

            //Adds its own name to the names in the cluster
            tempCluster.namesInCluster.add(clusterName);

            //Gets Cluster ID
            String clusterID = businessHashMap.get(clusterName);

            //Parses the business's .csv file
            ArrayList<ArrayList<String>> csvArrayHolder = ParseCategoryArray(clusterID);

            ArrayList<String> Terms = csvArrayHolder.get(0);

            //Gets the TF from the Cluster
            ArrayList<String> TF_Values = csvArrayHolder.get(1);

            //Gets the IDF Values from the Cluster
            ArrayList<String> IDF_Values = csvArrayHolder.get(2);

            //Sets Cluster Index
            tempCluster.clusterIndex = itter;

            //Sets The base category terms for the entire cluster
            tempCluster.categoryTerms = Terms;

            //Adds its own terms to the terms array in the cluster
            tempCluster.businessTermsInCluster.add(String.join(",", Terms));

            //Adds its own terms to the terms array in the cluster
            tempCluster.businessTotalSimilarity.add(0.0);

            //Sets TF Values
            tempCluster.Term_TF_Values = TF_Values;

            //Sets IDF Values
            tempCluster.Term_IDF_Values = IDF_Values;


            //Adds the newly made cluster to the parent array
            parentClusterHolder.add(tempCluster);


            //Iterates the counter
            itter++;
        }

        return parentClusterHolder;
    }

    public static double CalculateCluster_TF_IDF(ArrayList<String> currentBusinessTerms, BusinessCluster currentCluster)
    {
        ArrayList<String> currentClusterTerms = currentCluster.categoryTerms;
        double totalSimilarityValue = 0.0;
        //Iterates through businessTerm
        for(String businessTerm : currentBusinessTerms)
        {
            //Iterates through each clutser term
            for(int i = 0; i < currentClusterTerms.size(); i++)
            {
                String clusterTerm = currentClusterTerms.get(i);
                //Compares businessTerm to clusterTerm and adds terms TF-IDF value
                if(businessTerm.equals(clusterTerm))
                {
                    totalSimilarityValue += Double.parseDouble(currentCluster.Term_IDF_Values.get(i));
                }
            }
        }
        return totalSimilarityValue / (double) currentBusinessTerms.size();
    }

    public static void AssignCluster(ArrayList<BusinessCluster> clusterArrayList, MyHashMap2<String, String> businessHashMap) {
        ArrayList<String> listOfNames = businessHashMap.getAllKeysAsString();

        //Iterate through Each business
        for(String name : listOfNames)
        {
            // Flag to track if the current business is already a cluster point
            boolean foundMatch = false;

            // Iterate through each cluster
            for (BusinessCluster currentCluster : clusterArrayList)
            {
                if (currentCluster.clusterName.equals(name))
                {
                    foundMatch = true; // Set flag to true if names match
                    break;
                }

            }

            // continue to the next iteration of the outer loop if a match was found
            if (foundMatch) {
                continue;
            }

            //Gets Cluster ID
            String clusterID = businessHashMap.get(name);

            //Gets the currentBusinessTerms
            ArrayList<String> currentBusinessTerms = ParseCategoryArray(clusterID).get(0);

            double mostSimilarClusterValue = 0.0;
            int mostSimilarClusterIndex = 0;

            //Calculates the TF_IDF_Value for each cluster
            for(int j = 0; j < clusterArrayList.size(); j++)
            {
                BusinessCluster tempCluster = clusterArrayList.get(j);
                double TF_IDF_Value = CalculateCluster_TF_IDF(currentBusinessTerms, tempCluster);

                //Checks if this is the most similar cluster
                if(TF_IDF_Value > mostSimilarClusterValue)
                {
                    //Sets new HighestSimilaryCluster
                    mostSimilarClusterValue = TF_IDF_Value;
                    mostSimilarClusterIndex = j;
                }
            }

            BusinessCluster mostSimilarCluster = clusterArrayList.get(mostSimilarClusterIndex);
            mostSimilarCluster.namesInCluster.add(name);
            mostSimilarCluster.businessTermsInCluster.add(String.join(",", currentBusinessTerms));
            mostSimilarCluster.businessTotalSimilarity.add(mostSimilarClusterValue);
        }
    }


    public static void main(String[] args)
    {
        //******************************************Start Variables******************************************

        boolean generateNewSerialization = false;

        List<Double> starsList = new ArrayList<>();
        List<String> categoriesList = new ArrayList<>();

        String filePath = "src/main/resources/yelp_academic_dataset_business_formatted.json";


        //******************************************Reading File******************************************

        JSONArray business_JSON_Array = readJSON(starsList, categoriesList, filePath);



        //******************************************Creating New Serialized Files******************************************

        try
        {
            if(generateNewSerialization)
            {
                //******************************************Deleting Old Files******************************************

                deleteFilesInFolder("src/main/SerializedHashMaps");
                deleteFilesInFolder("src/main/SerializedBusinesses");

                //Serializes
                GenerateSerializedBusinesses(business_JSON_Array, categoriesList);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        //Loads Both Hashmaps
        MyHashMap2<String, Integer> frequencyTableHashMap = LoadTFHashMap("src/main/SerializedHashMaps/Frequency_Table");
        MyHashMap2<String, String> businessHashMap = LoadBusinessHashMap("src/main/SerializedHashMaps/Business_Table");

        //******************************************Clustering******************************************

        ArrayList<BusinessCluster> ClusterArrayList = GenerateNewClusters(categoriesList, business_JSON_Array, 15, businessHashMap);

        AssignCluster(ClusterArrayList, businessHashMap);

        Serializer.SerializeCluster(ClusterArrayList.get(0));
    }


}

