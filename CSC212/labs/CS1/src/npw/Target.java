
/*
 * Program to paint a target in context of the Nonrepresentational Painting World, NPW.
 */
package npw;
import java.awt.Color;
import javax.swing.SwingUtilities;
import painter.SPainter;
import shapes.SCircle;

public class Target {

    //DRAW CODE
    private void paintTheImage() {
        SPainter nut = new SPainter("target", 1000, 1000);
        nut.paintFrame(Color.BLACK, 500);
        SCircle out = new SCircle(300);
        SCircle in = new SCircle(200);
        SCircle center = new SCircle(100);
        nut.setColor(Color.RED);
        nut.paint(out);
        nut.setColor(Color.WHITE);
        nut.paint(in);
        nut.setColor(Color.RED);
        nut.paint(center);
    }

    //INFRASTRUCTURE
    public Target() {
        paintTheImage();
    }
    public static void main(String[] arg) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Target();
            }
        });
    }
}
