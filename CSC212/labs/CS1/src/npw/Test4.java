
/*
 * messing around with painters to figure out why the infrastructure is needed
 */

package npw;
import java.awt.Color;
import painter.SPainter;
import shapes.SCircle;
import shapes.SSquare;
import javax.swing.JColorChooser;
//import javax.swing.SwingUtilities; this is not needed

public class Test4 {
    public static void main(String[] args) {
        Color color1 = JColorChooser.showDialog(null, "Choose a color", Color.BLUE);
        SPainter jill = new SPainter("Test", 800, 800);
        jill.paintFrame(Color.BLACK, jill.canvasHeight());
        SSquare square = new SSquare(200);
        SCircle circle = square.circumscribingCircle();
        jill.setBrushWidth(4);
        jill.setColor(color1);
        jill.draw(square);
        jill.setColor(Color.WHITE);
        jill.draw(circle);
    }
}
//So then what does the infrastructure do?
//So basically without the infrastructure it works fin 80% of the time but
//when you do certain things like circumscribing circle it gives a blank/black screen.
//What the infrastructure does is that it makes the program run on its own thread instead of the main/current thread.
//The bug doesn't show up here, but I would bet that it has to do with the linux machines being linked to a server
//where all the processing and cpu power is located.
//I cannot replicate the bug even on the linux computers I wonder what he was using