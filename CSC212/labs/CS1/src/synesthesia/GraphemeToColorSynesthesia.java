
/*
 * Program to simulate the phenomenon known as grapheme to color synesthesia.
 * This program is written as an interpreter that recognizes and responds to:
 * - exit | terminate the program
 * - remap | redefine the mapping from letters to colors
 * - WORD OR PHRASE | simply show the word or phrase in synesthetic color
 */

package synesthesia;
import java.awt.Color;
import java.awt.Point;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import painter.SPainter;

public class GraphemeToColorSynesthesia {
    //Initial Variables
    private static final int fontSize = 30;
    private static final String theLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static String[] letters;
    private static Color[] colors;
    
    //Draw Code
    private void paintingCode() {

        //Initialization
        SPainter jill = new SPainter(1200,220);
        jill.setScreenLocation(30, 30);
        jill.setFontSize(fontSize);
        initializeColorMap(theLetters);

        //Interpretation
        while (true) {
            String input = JOptionPane.showInputDialog(null, "Please enter a word, or a few words ...");
            if (input == null) { input = "EXIT"; }
            input = input.toUpperCase();
            if (input.equals("EXIT")) {
                break;
            } else if (input.equals("REMAP")) {
                initializeColorMap(theLetters);
                showLetters(jill,theLetters);
            } else {
                showLetters(jill,input.toUpperCase());
            }
        }
        jill.end();
    }

    private static void showLetters(SPainter jill, String input) {
        //Ready
        eraseWhiteBoard(jill);
        //Set
        jill.moveTo(new Point.Double(100,100));
        //Go
        for (int i = 0; i < input.length(); ++i) {
            String letter = input.substring(i, i + 1);
            Color color = getLetterColor(letter);
            jill.setColor(color);
            jill.draw(letter);
            jill.mrt(fontSize);
        }
    }

    private static void initializeColorMap(String specialLetters) {
        letters = new String[specialLetters.length()];
        colors = new Color[specialLetters.length()];
        for (int i = 0; i < specialLetters.length(); ++i) {
            letters[i] = specialLetters.substring(i, i + 1);
            colors[i] = randomColor();
        }
    }

    private static Color getLetterColor(String letter) {
        for (int i = 0; i < letters.length; ++i) {
            if (letter.equalsIgnoreCase(letters[i])) {
                return colors[i];
            }
        }
        return Color.BLACK;
    }

    private static void eraseWhiteBoard(SPainter jill) {
        jill.setColor(Color.WHITE);
        jill.wash();
        jill.paintFrame(Color.BLACK, 5);
    }

    private static Color randomColor() {
        Random rgen = new Random();
        int r = rgen.nextInt(256);
        int g = rgen.nextInt(256);
        int b = rgen.nextInt(256);
        return new Color(r, g, b);
    }

    //Infrastructure
    public GraphemeToColorSynesthesia() { paintingCode(); }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GraphemeToColorSynesthesia();
            }
        });
    }
}
