import java.util.ArrayList;

public class BusinessCluster
{
    public String clusterName;

    public int clusterIndex;
    public ArrayList<String> categoryTerms = new ArrayList<>();
    public ArrayList<String> Term_TF_Values = new ArrayList<>();
    public ArrayList<String> Term_IDF_Values = new ArrayList<>();
    public ArrayList<String> namesInCluster = new ArrayList<>();
    public ArrayList<String> businessTermsInCluster = new ArrayList<>();

    public ArrayList<Double> businessTotalSimilarity = new ArrayList<>();
    public ArrayList<String> MostImportantWords = new ArrayList<>();
    public BusinessCluster (String clusterName)
    {
        this.clusterName = clusterName;
    }

}
