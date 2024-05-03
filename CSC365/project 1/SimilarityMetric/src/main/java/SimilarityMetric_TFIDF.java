import org.json.JSONArray;
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;



public class SimilarityMetric_TFIDF {


    public static boolean DebugMode = true;
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


            // Iterate over each element in the JSONArray
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                if (!jsonObject.isNull("stars")) {
                    starsList.add(jsonObject.getDouble("stars"));
                }

                if (!jsonObject.isNull("categories")) {
                    categoriesList.add(jsonObject.getString("categories"));
                }
            }


            for(String elem : categoriesList)
            {
                String[] categoryLine = elem.split(",\\s*");
                for(String singleString : categoryLine) {
                    singleString = singleString.replaceAll("\\s+", "").toLowerCase();
                    //System.out.println("\n SingleString: " + singleString);
                }
            }
            return jsonArray;

        } catch (Exception e) {
            System.out.println("***JSON Read Error***");
            return null;
        }
    }


    public static double[] calculateIDF(List<String> categoriesList, String[] termList)
    {
        //HashMap<String, Integer> hash = new HashMap<>();

        MyHashMap<String, Integer> myHashMap = new MyHashMap<String, Integer>();

        //This method is to calculate the number of times a target word comes up
        for (String eachDocument : categoriesList)
        {
            String[] categoryWords = eachDocument.toLowerCase().split(",\\s*");

            //Iterates Through termList
            for (String term : termList) {
                //Iterates Through Words in the doc
                for (String word : categoryWords)
                {

                    //Word is the current word in the doc
                    //Term is the target word


                    // Check if this word matches the target word
                    if (word.equals(term.toLowerCase())) {
                        if (myHashMap.get(term) != null) {
                            //Adds one to term
                            Integer termCount = myHashMap.get(term);
                            myHashMap.put(term, termCount + 1);
                        } else {
                            //First Time Seeing Term
                            myHashMap.put(term, 1);
                        }
                    }
                }
            }
        }


        double[] IDF_List = new double[termList.length];
        double NumDocs = categoriesList.size();

        for(int i = 0; i < termList.length; i++)
        {
            Integer finalTermCount = myHashMap.get(termList[i]);
            System.out.println("Blah: " + termList[i] + "   Word Count: " + finalTermCount + "   NumDocs: " + NumDocs);
            double IDF =  Math.log(NumDocs / finalTermCount);
            IDF_List[i] = IDF;
        }

        return IDF_List;
    }

    public static int findUserBusiness(JSONArray rewJsonArray, String businessID)
    {
        int localUserBusinessIndex = 0;
        boolean userBusFound = false;
        // Iterate over each element in the JSONArray
        for (int i = 0; i < rewJsonArray.length(); i++) {
            
            JSONObject jsonObject = rewJsonArray.getJSONObject(i);

            if (!jsonObject.isNull("business_id")) {
                String iterativeBusinessID = jsonObject.getString("business_id");
                if(businessID.equals(iterativeBusinessID))
                {
                    System.out.println("\nIndex: " + localUserBusinessIndex);
                    userBusFound = true;
                    break;
                }
            }
            localUserBusinessIndex++;
        }

        if(userBusFound)
        {
            //Found User Business
            return localUserBusinessIndex;
        }
        else
        {
            //Could not find user business
            return -1;
        }
    }

    public static double getSimilarityScore()
    {
        return SimilarityScore;
    }

    public static double getSimilarityScore2()
    {
        return SimilarityScore2;
    }


    public static String[] fixCategoriesError(JSONObject currentObject)
    {
        String categoriesString = currentObject.optString("categories", "");

        if (!categoriesString.isEmpty()) {
            String[] reviewCategories = categoriesString.split(",\\s*");
            return reviewCategories;
        } else {
            System.out.println("Categories not available for this business.");
        }
        return null;
    }

    public static double CalculateNormalizedStarDifference(List<Double> starsList, int userBusinessIndex, int i)
    {
        System.out.println("Business Index: " + userBusinessIndex + "  Similar Bus Index: " + i);
        double userBusRating = starsList.get(userBusinessIndex);
        double similarBusRating = starsList.get(i);

        double normalizedDifference = 1.0 - ( Math.abs(userBusRating - similarBusRating) / 4.0 );
        System.out.println("Start Difference between " + userBusRating + " and " + similarBusRating + " is: " + normalizedDifference);
        return normalizedDifference;
    }

    public static JSONArray runSimilarityMetric(String BusinessID) {
        
        //------------------------------Read JSON File----------------------------------


        String filePath = "src/main/resources/yelp_academic_dataset_business_formatted.json";
        List<Double> starsList = new ArrayList<>();
        List<String> categoriesList = new ArrayList<>();

        JSONArray review_JSON_Array = readJSON(starsList, categoriesList, filePath);

        //------------------------------Find User Buissiness----------------------------------

        int userBusinessIndex = findUserBusiness(review_JSON_Array, BusinessID);

        System.out.println("UserBus Index: " + userBusinessIndex);
        if(userBusinessIndex == -1)
        {
            //Couldn't find user business
            return null;
        }


        JSONObject userBusinessObject = review_JSON_Array.getJSONObject(userBusinessIndex);
        String name = userBusinessObject.getString("name");
        System.out.println("\nBusiness Name: " + name + "\n");

        //------------------------------Calculated the IDF Values for each of the target words----------------------------------

        String[] TargetWords = userBusinessObject.getString("categories").split(",\\s*");
        for(String singleString : TargetWords) {
            singleString = singleString.replaceAll("\\s+", "").toLowerCase();
        }

        double targetIDFValues[] = calculateIDF(categoriesList ,TargetWords);
        for(int i = 0; i < targetIDFValues.length; i++)
        {
            System.out.println("Word: " + TargetWords[i] + "             IDF_Value: " + targetIDFValues[i]);
        }
        
        //------------------------------Generate Similarity Values All the buissinesses----------------------------------

        System.out.println("\n\n\n_______________________Start_______________________\n");


        //Most Similar Business Object
        JSONObject mostSimilarBuissness = new JSONObject();
        double hieghestBusinessSimilarityScore = 0;

        //Second Most Similar Business Object
        JSONObject secondMostSimilarBuissness = new JSONObject();
        double secondHieghestBusinessSimilarityScore = 0;


        boolean isSet = false;

        //Itterates through each review
        for(int i = 0; i < review_JSON_Array.length(); i++)
        {
            System.out.println("\n--------------New Business-----------\n");
            double currentSimilarityScore = 0;
            JSONObject currentObject = review_JSON_Array.getJSONObject(i);

            System.out.println("Name: " + currentObject.getString("name") + "\n");

            //Checks if this is the UserBuisines
            if(i != userBusinessIndex)
            {
                //The current JSON Object
                //String[] reviewCategories = currentObject.getString("categories").split(",\\s*");

                String[] reviewCategories = fixCategoriesError(currentObject);
                if(reviewCategories == null)
                {
                    break;
                }

                //Itterates Through Each Target Word
                for(int targetWordItr = 0; targetWordItr < TargetWords.length; targetWordItr++)
                {
                    String currentTargetWord = TargetWords[targetWordItr];
                    //Itterates Through Each Category Review of a signle review
                    boolean wordFound = false;
                    for (String reviewCurrentWord : reviewCategories) {
                        //System.out.println("Current Targer: " + currentTargetWord + " == " + reviewCurrentWord);
                        if (currentTargetWord.equals(reviewCurrentWord)) {
                            double TF = (double) 1 / reviewCategories.length;
                            if (DebugMode)
                                System.out.println("Word: " + reviewCurrentWord + " TFValue = " + TF + "   |   1   /   " + reviewCategories.length);

                            double TF_IDF_Value = TF * targetIDFValues[targetWordItr];
                            System.out.println(reviewCurrentWord + "(Hit)     Adding TF_IDF Value: " + TF_IDF_Value);
                            currentSimilarityScore += TF_IDF_Value;
                            wordFound = true;
                        }
                    }
                    if(!wordFound)
                    {
                        if(DebugMode)
                            System.out.println("Word: " + currentTargetWord + " (Miss)");
                    }
                }
            }
            else
            {
                if(DebugMode) 
                    System.out.println("User Business Found: " + userBusinessIndex);
            }

            System.out.println("\nSimilarity Score: " + currentSimilarityScore);


            //AddStar Similarity
            double starSimilarity = CalculateNormalizedStarDifference(starsList, userBusinessIndex, i);

            currentSimilarityScore += starSimilarity;

            if(hieghestBusinessSimilarityScore <= currentSimilarityScore)
            {
                isSet = true;
                mostSimilarBuissness = currentObject;
                hieghestBusinessSimilarityScore = currentSimilarityScore;
            } else if (secondHieghestBusinessSimilarityScore <= currentSimilarityScore) {
                secondMostSimilarBuissness = currentObject;
                secondHieghestBusinessSimilarityScore = currentSimilarityScore;
            }
        }

        
        if(!isSet)
        {
            System.out.println("*******Error: Not Set********");
            mostSimilarBuissness = review_JSON_Array.getJSONObject(0);
        }
        System.out.println("\n\nHighest Similarity - Name: " + mostSimilarBuissness.getString("name") + "\nSimilarity Value: " + hieghestBusinessSimilarityScore);

        JSONArray returnArray = new JSONArray();


        SimilarityScore = hieghestBusinessSimilarityScore;

        SimilarityScore2 = secondHieghestBusinessSimilarityScore;
        //Index 1 = UserBusiness
        returnArray.put(userBusinessObject);
        returnArray.put(mostSimilarBuissness);
        returnArray.put(secondMostSimilarBuissness);

        return returnArray;

    }
}

