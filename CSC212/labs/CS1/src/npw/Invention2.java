
/*
 * Programming Challenge 4 Problem 7 Program JDK 11 Compatibility Version
 *
 */

package npw;
import java.awt.Color;
import javax.swing.SwingUtilities;
import painter.SPainter;
import shapes.SRectangle;
import java.util.Random;

public class Invention2 {
    //Draw Code
    private void paintTheImage() {
        SPainter jill = new SPainter("Invention2", 1000, 1000);
        jill.paintFrame(new Color(235,235,235), 500);
        Random random = new Random();
        int recNum = 150 + random.nextInt(151);
        paintRectangles(jill, recNum);
    }

    private void paintRectangles(SPainter jill, int recNum) {
        int i = 1;
        while(i <= recNum) {
            paintOneRectangle(jill);
            ++i;
        }
    }

    private void paintOneRectangle(SPainter jill) {
        jill.move();

        Random rgen = new Random();

        double height = 10.0 + rgen.nextInt(141);
        double width = 10.0 + rgen.nextInt(141);
        SRectangle rectangle = new SRectangle(height, width);

        int heading = rgen.nextInt(360);
        jill.setHeading(heading);

        jill.setColor(randomColor());
        jill.paint(rectangle);
        int r = 2 + rgen.nextInt( 2);
        if (r % 2 == 0) {
            jill.setColor(Color.BLACK);
            jill.draw(rectangle);
        } else {
            jill.setColor(Color.WHITE);
            jill.draw(rectangle);
        }
    }

    private static Color randomColor() {
        Random rgen = new Random();
        int r = rgen.nextInt(256);
        int g = rgen.nextInt(256);
        int b = rgen.nextInt(256);
        return new Color(r, g, b);
    }

    //Required Infrastructure
    public Invention2() {paintTheImage();}
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {new Invention2();}
        });
    }
}
