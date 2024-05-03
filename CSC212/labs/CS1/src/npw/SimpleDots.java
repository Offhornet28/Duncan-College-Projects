
/*
 * A program to paint, centered on the canvas, a circle of red, green, or blue colored dots,
 * That are spaced the value of its radius apart
 */

package npw;
import painter.SPainter;
import shapes.SCircle;
import java.awt.Color;
import javax.swing.SwingUtilities;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class SimpleDots {
    private void paintTheImage() {
        //get input info
        int radius = getNumber("circle radius");
        int diameter = getNumber("dot diameter");
        Color color = getColor("color");
        //create painter
        SPainter jill = new SPainter("Simple Dots", radius*2 + 50, radius*2 + 50);
        jill.setBrushWidth(3);
        SCircle circle = new SCircle(radius);
        SCircle dot = new SCircle((double)diameter / 2);
        //paint the dots
        paintCircleOfDots(jill, circle, dot, color);
    }

    private void paintCircleOfDots(SPainter jill, SCircle circle, SCircle dot, Color color) {
        //paint the circle of dots
        double howFarToMove = 0;
        while (howFarToMove < circle.radius()) {
            double chord = chordLength(howFarToMove, circle);
            int dots = dotsOnLineCount(chord, dot.diameter());
            if (howFarToMove == 0) {
                paintRow(jill, dot, dots, color);
            } else {
                //Invariance Moment
                jill.mfd(howFarToMove);
                paintRow(jill, dot, dots, color);
                jill.mbk(howFarToMove * 2);
                paintRow(jill, dot, dots, color);
                jill.mfd(howFarToMove);
            }
            howFarToMove = howFarToMove + dot.diameter();
        }
    }
    //Assumes the painter is at the center of the row to paint, facing right
    private void paintRow(SPainter jill, SCircle dot, int dotsToPaint, Color color) {
        //turn right to start painting the row
        jill.tr();
        //move backward 1/2 of the length we're painting to get ready to paint the row
        double centerOffset = ((dotsToPaint * dot.diameter()) / 2) - (dot.diameter() / 2);
        jill.mbk(centerOffset);
        //paint the row of dots
        int painted = 0;
        while (painted < dotsToPaint){
            paintOneDot(jill, dot, color);
            jill.mfd(dot.diameter());
            painted = painted + 1;
        }
        //Invariance Moment
        jill.mbk(centerOffset + dot.diameter());
        jill.tl();
    }
    private void paintOneDot(SPainter jill, SCircle dot, Color color) {
        dot.s2();
        jill.setColor(color);
        jill.paint(dot);
        dot.x2();
    }
    private static int dotsOnLineCount(double lineLength, double diameterLength) {
        //calculates the number of dots in a row
        int dots = ((int)Math.ceil( (lineLength - diameterLength) / diameterLength ) + 1);
        return dots;
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
    private static Color getColor(String prompt) {
        String nss = JOptionPane.showInputDialog(null,prompt+"?");
        if(nss.equalsIgnoreCase("red")) {
            return Color.RED;
        } else if (nss.equalsIgnoreCase("green")) {
            return Color.GREEN;
        } else if (nss.equalsIgnoreCase("blue")) {
            return Color.BLUE;
        } else {
            return Color.BLACK;
        }

    }

    //Required Infrastructure
    public SimpleDots() {
        paintTheImage();
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() { new SimpleDots(); }
        });
    }
}