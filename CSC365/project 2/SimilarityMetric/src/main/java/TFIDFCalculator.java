import java.util.HashMap;
import java.util.Map;

public class TFIDFCalculator {
    // This method now calculates binary frequency instead of term frequency
    public Map<String, Double> calculateBinaryFrequency(String[] doc) {
        Map<String, Double> termPresence = new HashMap<>();
        
        // Loop through each term in the document
        for (String term : doc) {
            // If the term is not already in the map, add it with a value of 1.0
            termPresence.putIfAbsent(term, 1.0);
        }
        
        return termPresence;
    }
}