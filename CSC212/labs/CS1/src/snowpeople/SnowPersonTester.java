
/*
 * Tester of the SnowPerson Class
 */

package snowpeople;
import painter.SPainter;
import javax.swing.SwingUtilities;
import java.awt.Color;

import shapes.SCircle;

public class SnowPersonTester {
    public SnowPersonTester() {
        SnowFamily family1 = new SnowFamily(1);
        SPainter jill = new SPainter("Snow Men", 1000, 1000);
        paintBackground(jill);
        SnowPerson billy = new SnowPerson("billy", 500, 5, Color.WHITE);
        System.out.println(family1);
        family1.paint(jill);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SnowPersonTester();
            }
        });
    }

    private void paintBackground(SPainter jill) {
        Color backgroundColor = new Color(150,255,255);
        if(jill.canvasWidth() > jill.canvasHeight()) {
            jill.paintFrame(backgroundColor, jill.canvasWidth());
        }
        jill.paintFrame(backgroundColor, jill.canvasHeight());
        jill.setColor(Color.WHITE);
    }
    private void showPainterPosition(SPainter jill) {
        SCircle circle = new SCircle(5);
        jill.setColor(Color.red);
        jill.paint(circle);
    }
}
