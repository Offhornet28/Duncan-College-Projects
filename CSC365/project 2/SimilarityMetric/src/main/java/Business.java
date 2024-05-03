import java.util.List;

public class Business {
    public String name;
    public String id;
    public String termFrequencies;
    public String categories;

    public Business(String name, String id, String termFrequencies, String categories) {
        this.name = name;
        this.id = id;
        this.termFrequencies = termFrequencies;
        this.categories = categories;
    }
}