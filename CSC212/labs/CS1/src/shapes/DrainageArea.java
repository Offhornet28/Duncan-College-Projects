
/*
 * Programming challenge 3 problem 2 program
 * All number values are in Inches (in) unless stated otherwise
 */

package shapes;

public class DrainageArea {

    public static void main(String[] args) {
        //Given Values
        double twelveFootBoardArea = 792; //There are 15 of them. Dimensions: 5.5 in x 144 in (12ft)
        double eightFootBoardArea = 528; //There are 19 of them. Dimensions: 5.5 in x 96 in (8ft)
        //Calculate total board area
        double twelveFootBoardAreaTotal = (twelveFootBoardArea * 15); //15 boards
        double eightFootBoardAreaTotal = (eightFootBoardArea * 19); //19 boards
        double totalBoardArea = (twelveFootBoardAreaTotal + eightFootBoardAreaTotal);
        //Split the L into two rectangles
        double lSubRecArea1 = (84 * 144); //7 ft x 12 ft
        double lSubRecArea2 = (108 * 96); //9 ft x 8 ft
        double lTotalArea = (lSubRecArea1 + lSubRecArea2);
        //Calculate the Results
        double drainageAreaInches = (lTotalArea - totalBoardArea);
        double drainageAreaFeet = (drainageAreaInches / 12);
        System.out.println("total area of the boards: " + totalBoardArea + " inches");
        System.out.println("total area of the deck: " + lTotalArea + " inches");
        System.out.println("Drainage Area: " + drainageAreaInches + " inches");
        System.out.println("Drainage Area: " + drainageAreaFeet + " feet");
    }
}
