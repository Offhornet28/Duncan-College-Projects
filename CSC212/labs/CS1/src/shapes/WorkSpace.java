
/*
 * Programming challenge 3 problem 1 program
 * All number values are in Inches (in)
 * None of the objects on the desk lie atop any others (except for the cans upon the coasters).
 */

package shapes;

public class WorkSpace {

    public static void main(String[] args) {
        //Initial Values
        double deskDepth = 30;
        double deskWidth = 72;
        double notebookArea = 48; // There are two of them. Dimensions: 6 in x 8 in
        double labManualArea = 135; // 10 in x 13.5 in
        double canRadius = 1.325; //There are two of them. Perfectly inscribe 2 respective square coasters.
        double plateDiameter = 6; //There are six of them.
        //Desk Values
        double initialDeskArea = (deskWidth * deskDepth);
        //Notebook Calculations
        double totalNotebookArea = (notebookArea * 2);
        //Coaster Calculations
        SCircle can = new SCircle(canRadius);
        SSquare coaster = can.circumscribingSquare();
        double coasterArea = coaster.area();
        double totalCoasterArea = (coasterArea * 2);
        //Plate Calculations
        SCircle plate = new SCircle(plateDiameter / 2.0);
        double plateArea = plate.area();
        double totalPlateArea = (plateArea * 6);
        //Total Area Calculations
        double totalObjectArea = (totalNotebookArea + labManualArea + totalCoasterArea + totalPlateArea);
        double finalDeskArea = (initialDeskArea - totalObjectArea);
        System.out.println("Initial Desk Area: " + initialDeskArea + " in");
        System.out.println("Total Object Area: " + totalObjectArea + " in");
        System.out.println("Final Desk Area: " + finalDeskArea + " in");
    }
}
