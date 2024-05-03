
/*
 * Modified variation of the target program allowing for a changeable target color
 */

package npw;
import java.awt.Color;
import javax.swing.SwingUtilities;
import painter.SPainter;
import shapes.SCircle;
import javax.swing.JColorChooser;
public class TargetModified {
    //DRAW CODE
    private void paintTheImage() {
        SPainter nut = new SPainter("target", 1000, 1000);
        Color backColor = JColorChooser.showDialog(null, "Choose a Background Color", Color.BLACK);
        nut.paintFrame(backColor, 500);
        Color color1 = JColorChooser.showDialog(null, "Choose Outer Target Color", Color.RED);
        Color color2 = JColorChooser.showDialog(null,"Choose Inner Target Color", Color.WHITE);
        SCircle out = new SCircle(300);
        SCircle in = new SCircle(200);
        SCircle center = new SCircle(100);
        nut.setColor(color1);
        nut.paint(out);
        nut.setColor(color2);
        nut.paint(in);
        nut.setColor(color1);
        nut.paint(center);
    }
    //INFRASTRUCTURE
    public TargetModified() {paintTheImage();}
    public static void main(String[] arg) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {new TargetModified();}
        });
    }
}
