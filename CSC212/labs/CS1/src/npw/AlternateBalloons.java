
/*
 * Program that paints 300 balloons 6 different colors in a blue sky.
 * It will feature commands.
 */

package npw;
import java.awt.Color;
import java.util.Random;
import javax.swing.SwingUtilities;
import painter.SPainter;
import shapes.SCircle;
import shapes.SSquare;

public class AlternateBalloons {

    // REQUIRED INFRASTRUCTURE
    public AlternateBalloons() {
        paintTheImage();
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AlternateBalloons();
            }
        });
    }
    // THE BALLOONS
    private void paintTheImage() {
        SPainter jill = new SPainter("Balloons",600,600);
        paintSky(jill);
        int nrOfBalloons = 300;
        paintBalloons(jill, nrOfBalloons);
    }
    private void paintSky(SPainter jill) {
        Color skyColor = new Color(150,255,255);
        jill.setColor(skyColor); //The blue sky doesn't look like an actual Sky, so I changed it
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
        int rn = rgen.nextInt(6);
        if (rn == 0) {
            Color color0 = new Color(0,0,255);
            jill.setColor(color0);
        } else if (rn == 1) {
            Color color1 = new Color(0,255,0);
            jill.setColor(color1);
        } else if (rn == 2) {
            Color color2 = new Color(0,255,200);
            jill.setColor(color2);
        } else if (rn == 3) {
            Color color3 = new Color(0,0,125);
            jill.setColor(color3);
        } else if (rn == 4) {
            Color color4 = new Color(150,50,255);
            jill.setColor(color4);
        } else {
            Color color5 = new Color(255,0,0);
            jill.setColor(color5);
        }
        jill.move();
        int balloonRadius = 30;
        SCircle balloon = new SCircle(balloonRadius);
        jill.paint(balloon);
        jill.setColor(Color.BLACK);
        jill.draw(balloon);
    }
}
