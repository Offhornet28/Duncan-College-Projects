
/*
 *A program that uses values from a String ArrayList
 */

package arraylistplay;
import java.util.ArrayList;

public class Streets {
    public static void main(String[] args) {
        //streets ArrayList setup
        ArrayList<String> streets = new ArrayList<>();
        streets.add("Iberville");
        streets.add("Decatur");
        streets.add("Toulouse");
        streets.add("Bourbon");
        streets.add("Dauphine");
        streets.add("Royal");
        streets.add("St Ann");
        streets.add("St Peter");
        streets.add("Conti");
        streets.add("Exchange");
        streets.add("Bienville");
        streets.add("Dumaine");
        //streets ArrayList info
        System.out.println("length of streets array = " + streets.size());
        System.out.println("first streets = " + streets.get(0));
        System.out.println("last streets = " + streets.get(11));
        System.out.println("last streets = " + streets.get(streets.size() - 1));
        //loops & stuff
        System.out.println("\nThe initial array ...");
        for(String street : streets) {
            System.out.println(street);
        }

        String temp = streets.get(0);
        streets.set(0, streets.get(streets.size() - 1));
        streets.set(streets.size() - 1, temp);

        System.out.println("\nThe final list ...");
        for(String street : streets) {
            System.out.println(street);
        }
    }
}