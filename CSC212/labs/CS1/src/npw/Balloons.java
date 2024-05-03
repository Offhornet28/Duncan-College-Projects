
/*
 * Program that paints 100 red, yellow, and orange balloons in a blue sky.
 * It will feature commands.
 */

package npw;
import java.awt.Color;
import java.util.Random;
import javax.swing.SwingUtilities;
import painter.SPainter;
import shapes.SCircle;
import shapes.SSquare;

public class Balloons {

    // REQUIRED INFRASTRUCTURE
    public Balloons() {
        paintTheImage();
    }
    public static void main(String[] arg) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Balloons();
            }
        });
    }
    // THE BALLOONS
    private void paintTheImage() {
        SPainter jill = new SPainter("Balloons",600,600);
        paintSky(jill);
        int nrOfBalloons = 100;
        paintBalloons(jill, nrOfBalloons);
    }
    private void paintSky(SPainter jill) {
        jill.setColor(Color.BLUE);
        SSquare sky = new SSquare(2000);
        jill.paint(sky);
    }
    private void paintBalloons(SPainter jill, int nrOfBalloons) {
        int i = 1;
        while (i <= nrOfBalloons) {
            paintOneBalloon(jill);
            i = i + 1;
        }
    }
    private void paintOneBalloon(SPainter jill) {
        Random rgen = new Random();
        int rn = rgen.nextInt(3);
        if (rn == 0) {
            jill.setColor(Color.RED);
        } else if(rn == 1) {
            jill.setColor(Color.ORANGE);
        } else {
            jill.setColor(Color.YELLOW);
        }
        jill.move();
        int balloonRadius = 20;
        SCircle balloon = new SCircle(balloonRadius);
        jill.paint(balloon);
        jill.setColor(Color.BLACK);
        jill.draw(balloon);
    }
}
