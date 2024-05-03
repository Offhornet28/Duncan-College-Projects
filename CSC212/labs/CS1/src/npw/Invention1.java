
/*
 * Programming Challenge 4 Problem 6 Program
 */

package npw;
import java.awt.Color;
import javax.swing.SwingUtilities;
import painter.SPainter;
import shapes.SCircle;
import shapes.SSquare;

public class Invention1 {

    //Draw Code
    private void paintTheImage() {
        SPainter jill = new SPainter("Invention1", 1000, 1000);
        SCircle circle = new SCircle(300);
        SSquare square = new SSquare(50);
        paintCircles(jill, circle);
        paintSquares(jill, circle, square, 5);
        circle.setRadius(200);
        paintSquares(jill, circle, square, 10);
        circle.setRadius(100);
        paintSquares(jill, circle, square, 20);
    }

    private void paintSquares(SPainter jill, SCircle circle, SSquare square, int spacing) {
        Color color1 = new Color(0,150,255);
        Color color2 = new Color(0,150,125);
        int h = 0;
        int i = 2;
        while(h < 360) {
            int n = i % 2;
            if (n == 0) {
                jill.setColor(color1);
            } else {
                jill.setColor(color2);
            }
            jill.setHeading(h);
            jill.mfd(circle.radius());
            jill.setHeading(h + 45);
            jill.paint(square);
            jill.setColor(Color.BLACK);
            jill.setBrushWidth(3);
            jill.draw(square);
            jill.setBrushWidth(1);
            jill.setHeading(0);
            jill.moveToCenter();
            ++i;
            h = h + spacing;
        }
    }

    private void paintCircles(SPainter jill, SCircle circle) {
        int temp = (int)circle.radius();

        jill.setColor(new Color(0,250,125));
        paintOneCircle(jill, circle);

        circle.setRadius((int)circle.radius() - 100);
        jill.setColor(new Color(0,250,225));
        paintOneCircle(jill,circle);

        circle.setRadius((int)circle.radius() - 100);
        jill.setColor(new Color(0,0,255));
        paintOneCircle(jill, circle);

        circle.setRadius(temp);
    }
    private void paintOneCircle(SPainter jill, SCircle circle) {
        jill.paint(circle);
        jill.setColor(Color.BLACK);
        jill.setBrushWidth(3);
        jill.draw(circle);
        jill.setBrushWidth(1);
    }

    //Required Infrastructure
    public Invention1() {paintTheImage();}
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {new Invention1();}
        });
    }
}

