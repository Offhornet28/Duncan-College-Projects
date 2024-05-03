
/*
 *A program that uses values from a String array
 */

package arrayplay;

public class Streets {
    public static void main(String[] args) {
        //Initial Array
        String[] streets = new String[12];
        streets[0] = "Iberville";
        streets[1] = "Decatur";
        streets[2] = "Toulouse";
        streets[3] = "Bourbon";
        streets[4] = "Dauphine";
        streets[5] = "Royal";
        streets[6] = "St Ann";
        streets[7] = "St Peter";
        streets[8] = "Conti";
        streets[9] = "Exchange";
        streets[10] = "Bienville";
        streets[11] = "Dumaine";
        //Initial Array Values
        System.out.println("length of streets array = " + streets.length);
        System.out.println("first streets = " + streets[0]);
        System.out.println("last streets = " + streets[11]);
        System.out.println("last streets = " + streets[streets.length - 1]);
        //Loops
        System.out.println("\nThe initial array ...");
        int i = 0;
        while (i < streets.length) {
            System.out.println(streets[i]);
            ++i;
        }

        String temp = streets[0];
        streets[0] = streets[streets.length - 1];
        streets[streets.length - 1] = temp;

        System.out.println("\nThe final Array");
        for (int x = 0; x < streets.length; ++x) {
            System.out.println(streets[x]);
        }
    }
}
