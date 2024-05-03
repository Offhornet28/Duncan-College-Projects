
/*
 * Blindness simulator
 */

package npw;
import java.awt.Color;
import javax.swing.SwingUtilities;
import painter.SPainter;
import shapes.SCircle;

public class Blindness {
    //Draw Code
    private void paintTheImage() {
        SPainter jill = new SPainter("Blindness", 600, 600);
        SCircle dot = new SCircle(5);
        jill.setColor(Color.BLACK);
        while ( true ) {
            jill.move();
            jill.paint(dot);
        }
    }

    //Required Infrastructure
    public Blindness() {
        paintTheImage();
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Blindness();
            }
        });
    }
}