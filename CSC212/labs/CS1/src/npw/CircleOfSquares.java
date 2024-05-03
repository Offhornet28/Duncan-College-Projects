
/*
 * A program to paint, centered on the canvas, a circle of randomly colored, black-framed squares.
 */

package npw;
import painter.SPainter;
import shapes.SCircle;
import shapes.SSquare;
import java.awt.Color;
import javax.swing.SwingUtilities;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class CircleOfSquares {
    private void paintTheImage() {
        //get input info
        int radius = getNumber("circle radius");
        int side = getNumber("square side length");
        //create painter
        SPainter jill = new SPainter("Circle of Squares", radius*2 + 50, radius*2 + 50);
        jill.setBrushWidth(3);
        SCircle circle = new SCircle(radius);
        SSquare square = new SSquare(side);
        //paint the squares
        paintCircleOfSquares(jill, circle, square);
    }

    private void paintCircleOfSquares(SPainter jill, SCircle circle, SSquare square) {
        //paint the circle of squares
        double howFarToMove = 0;
        while (howFarToMove < circle.radius()) {
            double chord = chordLength(howFarToMove, circle);
            int squares = squaresOnLineCount(chord, square.side());
            if (howFarToMove == 0) {
                paintRow(jill, square, squares);
            } else {
                //invariance with respect to painter position
                jill.mfd(howFarToMove);
                paintRow(jill, square, squares);
                jill.mbk(howFarToMove * 2);
                paintRow(jill, square, squares);
                jill.mfd(howFarToMove);
            }
            howFarToMove = howFarToMove + square.side();
        }
    }
    //Assumes the painter is at the center of the row to paint, facing right.
    private void paintRow(SPainter jill, SSquare square, int squaresToPaint) {
        //turn right to start painting the row.
        jill.tr();
        //move backward 1/2 of the length we're painting to get ready to paint the row.
        double centerOffset = ((squaresToPaint * square.side()) / 2) - (square.side() / 2);
        jill.mbk(centerOffset);
        //paint the row of squares.
        int painted = 0;
        while (painted < squaresToPaint){
            paintOneSquare(jill, square);
            jill.mfd(square.side());
            painted = painted + 1;
        }
        //Invariance Moment
        jill.mbk(centerOffset + square.side());
        jill.tl();
    }
    private void paintOneSquare(SPainter jill, SSquare square) {
        jill.setColor(randomColor());
        jill.paint(square);
        jill.setColor(Color.BLACK);
        jill.draw(square);
    }
    private static int squaresOnLineCount(double lineLength, double sideLength) {
        //calculates the number of squares in a row
        int squares = ((int)Math.ceil( (lineLength - sideLength) / sideLength ) + 1);
        return squares;
    }
    private double chordLength(double yOffset, SCircle circle) {
        //gets the sqrt of the radius^2 and subtracts in from howFarToMove^2
        double xLength = Math.sqrt(Math.pow(circle.radius(), 2) - Math.pow(yOffset, 2));
        double chordLength = xLength * 2;
        return chordLength;
    }

    private static int getNumber(String prompt) {
        String nss = JOptionPane.showInputDialog(null,prompt+"?");
        Scanner scanner = new Scanner(nss);
        return scanner.nextInt();
    }
    private static Color randomColor() {
        Random rgen = new Random();
        int r = rgen.nextInt(256);
        int g = rgen.nextInt(256);
        int b = rgen.nextInt(256);
        return new Color(r,g,b);
    }

    //Required Infrastructure
    public CircleOfSquares() {
        paintTheImage();
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() { new CircleOfSquares(); }
        });
    }
}
