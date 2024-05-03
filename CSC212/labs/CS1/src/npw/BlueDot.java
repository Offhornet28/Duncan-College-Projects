
/*
 * Program to paint a blue dot in context of the Nonrepresentational Painting World, NPW.
 */
package npw;
import java.awt.Color;
import javax.swing.SwingUtilities;
import painter.SPainter;
import shapes.SCircle;

public class BlueDot {

    // THE SOLUTION TO THE BLUE DOT PROBLEM
    private void paintTheImage() {
        SPainter jill = new SPainter("Blue Dot",600,600);
        SCircle dot = new SCircle(200);
        jill.setColor(Color.BLUE);
        jill.paint(dot);
    }

    // REQUIRED INFRASTRUCTURE
    public BlueDot() {
        paintTheImage();
    }
    public static void main(String[] arg) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BlueDot();
            }
        });
    }
}
