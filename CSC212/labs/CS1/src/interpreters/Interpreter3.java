
/*
 *This interpreter is intended to paint different colored dots in a window
 *
 * The commands that the interpreter can recognize and respond to are:
 *  -BLUE: paint a blue dot
 *  -RED: paint a red dot
 *  -GREEN: paint a green dot
 *  -YELLOW: paint a yellow dot
 *  -RANDOM: paint a random colored dot
 *  -HELP: show a list of commands in a dialogue message box
 *  -EXIT: terminate the program
 */

package interpreters;
import painter.SPainter;
import shapes.SCircle;
import java.awt.Color;
import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;

public class Interpreter3 {
    //DRAW CODE
    private void paintTheImage() {
        //PAINTER & CIRCLE SETUP
        SPainter jill = new SPainter("Dot Thing",400,400);
        jill.setScreenLocation(0,0);
        SCircle dot = new SCircle(180);
        //DIALOGUE BOX CODE
        while (true) {
            String command = JOptionPane.showInputDialog(null, "Command?");
            if (command == null) { command = "exit"; } //When cancel button is clicked
            if (command.equalsIgnoreCase("blue")) {
                jill.setColor(Color.BLUE);
                jill.paint(dot);
            } else if (command.equalsIgnoreCase("red")) {
                jill.setColor(Color.RED);
                jill.paint(dot);
            } else if (command.equalsIgnoreCase("green")) {
                jill.setColor(Color.GREEN);
                jill.paint(dot);
            } else if (command.equalsIgnoreCase("yellow")) {
                jill.setColor(Color.YELLOW);
                jill.paint(dot);
            } else if (command.equalsIgnoreCase("random")) {
                jill.setColor(randomColor());
                jill.paint(dot);
            } else if (command.equalsIgnoreCase("help")) {
                JOptionPane.showMessageDialog(null, "Valid commands are: " +
                        "RED | BLUE | GREEN | YELLOW | RANDOM | HELP | EXIT");
            } else if (command.equalsIgnoreCase("exit")) {
                jill.end();
                System.out.println("Thank you for viewing the dots ...");
                break;
            } else {
                JOptionPane.showMessageDialog(null, "Unrecognizable command: " +
                        command.toUpperCase());
            }
        }
    }

    private Color randomColor() {
        int rv = (int)(Math.random()*256);
        int gv = (int)(Math.random()*256);
        int bv = (int)(Math.random()*256);
        return new Color(rv, gv, bv);
    }

    //REQUIRED INFRASTRUCTURE
    public Interpreter3() {
        paintTheImage();
    }
    public static void main(String[] arg) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Interpreter3();
            }
        });
    }
}
