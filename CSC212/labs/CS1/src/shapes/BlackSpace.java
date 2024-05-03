
/*
 * Programming challenge 3 problem 3 program
 * All number values are in Millimeters (mm) unless stated otherwise
 */

package shapes;
import java.awt.Color;
import painter.SPainter;
import shapes.SCircle;
import shapes.SSquare;

public class BlackSpace {
    public static void main(String[] args) {
        SSquare tableCloth = new SSquare(800); //side length of 800 mm
        SCircle blueCircle = new SCircle(360); //circle is 40 mm away from the edges of the square
        SSquare greenDiamond = blueCircle.inscribingSquare(); //inscribing square of blue circle
        SCircle interiorBlackCircle = new SCircle((greenDiamond.side() / 2) - 30); // 30 mm away from the midpoint of green diamond
        double initialSquareArea = tableCloth.area(); //640,000 mm
        double blackCircleArea = interiorBlackCircle.area();
        double blueCircleArea = blueCircle.area();
        double blackSpaceArea = (initialSquareArea - (blueCircleArea - blackCircleArea));
        System.out.println("Black Space Area: " + blackSpaceArea + " mm");
    }

    //ignore this, it just makes me visualize it better
    private void displayProblem() {
        SPainter jill = new SPainter("Table Cloth",1000,1000);
        SSquare outerSquare = new SSquare(800);
        jill.setColor(Color.BLACK); jill.paint(outerSquare);
        SCircle outerCircle = new SCircle(360);
        jill.setColor(Color.BLUE); jill.paint(outerCircle);
        SSquare middleDiamond = outerCircle.inscribingSquare();
        jill.setColor(Color.GREEN); jill.setHeading(45); jill.paint(middleDiamond); jill.faceNorth();
        SCircle innerCircle = new SCircle((middleDiamond.side() / 2) - 30);
        jill.setColor(Color.BLACK); jill.paint(innerCircle);
    }
}
