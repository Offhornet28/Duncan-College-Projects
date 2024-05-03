
/*
 *Orange Dot Program
 */
package npw;
import java.awt.Color;
import javax.swing.SwingUtilities;
import painter.SPainter;
import shapes.SCircle;

public class OrangeDots {

    //DRAW CODE
    private void paintTheImage() {
        //Create the painter
        SPainter jill = new SPainter("Orange Dots",800,800);
        //Set the color to "orange"
        jill.setColor(Color.ORANGE);
        //Create the initial circle and paint it
        SCircle dot = new SCircle(200); jill.paint(dot);
        //Moves painter position to top left of circle
        jill.mfd(200); jill.mlt(200);
        //Changes dot size and paints it, then moves painter back to center
        dot.s2(); jill.paint(dot); jill.moveToCenter();
        //Moves painter position to top right of circle
        jill.mfd(200); jill.mrt(200);
        //Paints the dot then moves the painter back to center
        jill.paint(dot); jill.moveToCenter();
    }

    //REQUIRED INFRASTRUCTURE
    public OrangeDots() {
        paintTheImage();
    }
    public static void main(String[] arg) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new OrangeDots();
            }
        });
    }
}
