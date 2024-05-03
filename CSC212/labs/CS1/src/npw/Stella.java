
/*
 * Programming Challenge 4 Problem 8 Program
 */

package npw;
import java.awt.Color;
import javax.swing.SwingUtilities;
import painter.SPainter;
import shapes.SSquare;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class Stella {
    private void paintTheImage() {
        int nrOfSquares = getNumber("number of squares");
        SPainter jill = new SPainter("Stella", 800, 800);
        SSquare square = new SSquare(700);
        Color color1 = randomColor();
        Color color2 = randomColor();
        int i = 2;
        while (i <= (nrOfSquares + 2)) {
            int n = i % 2;
            if (n == 0) {
                jill.setColor(color1);
                jill.paint(square);
            } else {
                jill.setColor(color2);
                jill.paint(square);
            }
            square.shrink(700.0/(double)nrOfSquares);
            i = i + 1;
        }
    }

    private static int getNumber(String prompt) {
        String nss = JOptionPane.showInputDialog(null,prompt+"?");
        Scanner scanner = new Scanner(nss);
        return scanner.nextInt();
    }
    private static Color randomColor() {
        Random rgen = new Random();
        int r = rgen.nextInt(256);
        int g = rgen.nextInt(256);
        int b = rgen.nextInt(256);
        return new Color(r,g,b);
    }

    //Required Infrastructure
    public Stella() {paintTheImage();}
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {new Stella();}
        });
    }
}
