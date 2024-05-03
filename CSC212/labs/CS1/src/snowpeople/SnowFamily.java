
/*
 * A class to represent a family of snow people
 */

package snowpeople;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.util.Scanner;
import java.awt.Color;
import painter.SPainter;
import java.awt.geom.Point2D;

public class SnowFamily {

    public ArrayList<SnowPerson> snowFamily;

    public SnowFamily(int nrOfPeople) {
        snowFamily = new ArrayList<>();
        for(int i = 1; i < nrOfPeople + 1; ++i) {
            String nameInput = JOptionPane.showInputDialog(null, "Name of snowPerson" + i + "?");
            double heightInput = getNumber("Height of snowPerson" + i);
            int buttonInput = getNumber("Number of buttons on snowPerson" + i);
            Color colorInput = getColor("Color of snowPerson" + i);
            snowFamily.add(new SnowPerson(nameInput, heightInput, buttonInput, colorInput));
        }
    }

    public String toString() {
        String out = "[";
        String temp;
        for (SnowPerson snowPerson : snowFamily) {
            temp = out;
            out = temp + " " + snowPerson.toString() + ",";
        }
        return out + " ]";
    }

    public void paint(SPainter painter) {
        Point2D.Double initialPosition = painter.position();
        //Spacing Calculation
        double painterWidth = painter.getWidth();
        double temp;
        double finalWidth = painterWidth;
        for(SnowPerson snowPerson : snowFamily) {
            temp = finalWidth;
            finalWidth = temp - snowPerson.width();
        }
        double spacing = finalWidth / snowFamily.size();
        //Painting the Family
        painter.mlt(painter.canvasWidth() / 2.0);
        for (int idx = 0; idx < snowFamily.size(); ++idx) {
            if(idx == 0) {
                painter.mrt(spacing / 2.0);
                painter.mrt((snowFamily.get(idx).width()) / 2.0);
                snowFamily.get(idx).paint(painter);
            } else {
                painter.mrt((snowFamily.get(idx - 1).width()) / 2.0);
                painter.mrt(spacing);
                painter.mrt((snowFamily.get(idx).width()) / 2.0);
                snowFamily.get(idx).paint(painter);
            }
        }
        //Invariance
        painter.moveTo(initialPosition);
    }

    //Infrastructure to get things to work
    private static int getNumber(String prompt) {
        String nss = JOptionPane.showInputDialog(null,prompt+"?");
        Scanner scanner = new Scanner(nss);
        return scanner.nextInt();
    }
    //This is much more complex than it needs to be, but it works
    private static Color getColor(String prompt) {
        boolean i = false;
        while (i != true) {
            String nss = JOptionPane.showInputDialog(null, prompt + "?");
            if (nss.equalsIgnoreCase("red")) {
                i = true;
                return Color.RED;
            } else if (nss.equalsIgnoreCase("orange")) {
                i = true;
                return Color.ORANGE;
            } else if (nss.equalsIgnoreCase("yellow")) {
                i = true;
                return Color.YELLOW;
            } else if (nss.equalsIgnoreCase("pink")) {
                i = true;
                return Color.PINK;
            } else if (nss.equalsIgnoreCase("magenta")) {
                i = true;
                return Color.MAGENTA;
            } else if (nss.equalsIgnoreCase("blue")) {
                i = true;
                return Color.BLUE;
            } else if (nss.equalsIgnoreCase("cyan")) {
                i = true;
                return Color.CYAN;
            } else if (nss.equalsIgnoreCase("green")) {
                i = true;
                return Color.GREEN;
            } else if (nss.equalsIgnoreCase("black")) {
                i = true;
                return Color.BLACK;
            } else if (nss.equalsIgnoreCase("dark gray") | nss.equalsIgnoreCase("dark grey")) {
                i = true;
                return Color.DARK_GRAY;
            } else if (nss.equalsIgnoreCase("grey") | nss.equalsIgnoreCase("gray")) {
                i = true;
                return Color.GRAY;
            } else if (nss.equalsIgnoreCase("white")) {
                i = true;
                return Color.WHITE;
            } else if (nss.equalsIgnoreCase("help")) {
                JOptionPane.showMessageDialog(null, "Valid commands are: " +
                        "red | orange | yellow | pink | magenta | blue | cyan | green | black | dark gray | grey | white | help");
            }
        }
        return Color.WHITE;
    }
}
