/*
 * Scanner circle test
 */
package npw;
import java.awt.Color;
import javax.swing.SwingUtilities;
import painter.SPainter;
import shapes.SCircle;
import java.util.Scanner;
import javax.swing.JColorChooser;
public class Test2 {
    //Draw Code
    private void paintTheImage() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Circle Radius: ");
        int input = scanner.nextInt();
        double radius = input;
        SCircle dot = new SCircle(radius);
        SPainter jill = new SPainter("Canvas", 1000, 1000);
        //Color chooser menu is not showing up on Windows
        Color chosenColor = JColorChooser.showDialog(null, "Choose a Color", Color.BLUE);
        jill.paintFrame(Color.BLACK, 1000);
        jill.setColor(chosenColor);
        jill.paint(dot);
    }

    //Required Infrastructure
    public Test2() {paintTheImage();}
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {new Test2();}
        });
    }
}
/*
 * Tester code
 * System.out.println("input --> " + input);
 * System.out.println("radius --> " + radius);
 */