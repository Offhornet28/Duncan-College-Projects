
/*
 * Dots program challenge with a key thing being that the image stays the same
 * even if you change the circle size or canvas (hopefully).
 */

package npw;
import java.awt.Color;
import javax.swing.SwingUtilities;
import painter.SPainter;
import shapes.SCircle;
import shapes.SRectangle;

public class Dots {

    //Draw Code
    private void paintTheImage() {
        SPainter jill = new SPainter("Dots",1200,600);
        SCircle dot = new SCircle(50.0);
        setupCanvas(jill);
        paintCenterDot(jill, dot);
        paintInnerDotColumns(jill, dot);
        paintOuterDotColumns(jill, dot);
        paintDotEnds(jill, dot);
    }

    private void setupCanvas(SPainter jill) {
        SRectangle rectangle = new SRectangle(jill.canvasHeight() * 1.0, jill.canvasWidth() * (1.0/2.0));
        jill.setColor(Color.BLACK);
        jill.mlt(jill.canvasWidth() / 4.0);
        jill.paint(rectangle);
        jill.moveToCenter();
    }

    private void paintCenterDot(SPainter jill, SCircle dot) {
        jill.setColor(Color.GREEN);
        //set radius to 45
        dot.shrink(dot.radius() / 10.0);
        jill.paint(dot);
        //reset circle
        dot.expand(dot.radius() / 9.0);
    }

    private void paintInnerDotColumns(SPainter jill, SCircle dot) {
        //left side
        jill.setColor(Color.RED);
        jill.mlt(dot.diameter() + (dot.radius() / 2.0));
        //set its radius to 60
        dot.expand(dot.radius() / 5.0);
        jill.mfd(((jill.canvasHeight() / 2.0) - 60.0) - dot.radius());
        jill.paint(dot);
        jill.mbk((jill.canvasHeight() - 120.0) - dot.diameter());
        jill.paint(dot);
        //reset painter and circle
        dot.shrink(dot.radius() / 6.0); jill.moveToCenter();

        //right side
        jill.setColor(Color.BLUE);
        jill.mrt(dot.diameter() + (dot.radius() / 2.0));
        //set its radius to 60
        dot.expand(dot.radius() / 5.0);
        jill.mbk(((jill.canvasHeight() / 2.0) - 60.0) - dot.radius());
        jill.paint(dot);
        jill.mfd((jill.canvasHeight() - 120.0) - dot.diameter());
        jill.paint(dot);
        //reset painter and circle
        dot.shrink(dot.radius() / 6.0); jill.moveToCenter();
    }

    private void paintOuterDotColumns(SPainter jill, SCircle dot) {
        //left side
        jill.setColor(Color.RED);
        jill.mlt((dot.diameter() * 3.0) + (dot.radius() / 2.0));
        //radius is 50
        jill.mfd(((jill.canvasHeight() / 2.0) - 120.0) - dot.radius());
        jill.paint(dot);
        jill.mbk((jill.canvasHeight() - 240.0) - dot.diameter());
        jill.paint(dot);
        //reset painter
        jill.moveToCenter();

        //right side
        jill.setColor(Color.BLUE);
        jill.mrt((dot.diameter() * 3.0) + (dot.radius() / 2.0));
        //radius is 50
        jill.mbk(((jill.canvasHeight() / 2.0) - 120.0) - dot.radius());
        jill.paint(dot);
        jill.mfd((jill.canvasHeight() - 240.0) - dot.diameter());
        jill.paint(dot);
        //reset painter and circle
        jill.moveToCenter();
    }

    private void paintDotEnds(SPainter jill, SCircle dot) {
        //color
        jill.setColor(Color.GREEN);
        //left side
        jill.mlt((dot.diameter() * 4.0) + (dot.radius() / 2.0));
        //set its radius to 40
        dot.shrink(dot.radius() / 5.0);
        jill.paint(dot);
        //rest painter and circle
        dot.expand(dot.radius() / 4.0); jill.moveToCenter();

        //right side
        jill.mrt((dot.diameter() * 4.0) + (dot.radius() / 2.0));
        //set its radius to 40
        dot.shrink(dot.radius() / 5.0);
        jill.paint(dot);
        //reset painter and circle
        dot.expand(dot.radius() / 4.0); jill.moveToCenter();
    }

    //Infrastructure
    public Dots() {
        paintTheImage();
    }
    public static void main(String[] arg) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {new Dots();}
        });
    }
}

/*
 *Things needed:
 * 1) 11 painted circles
 * 2) There has to be exactly 4 different sizes of circles
 * 3) There has to be exactly 3 different colors of circles
 * 4) None of the circles can touch
 * 5) The image has to be symmetric around the y-axis
 * 6) Not all of the circles can touch the y-axis
 */