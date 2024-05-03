
/*
 * Class to represent a single snow person
 */

package snowpeople;
import java.awt.Color;
import painter.SPainter;
import shapes.SCircle;
import java.awt.geom.Point2D;

public class SnowPerson {
    //Instance Variables
    private final String name;
    private double height;
    private int nrOfButtons;
    private final Color color;

    //Constructors
    public SnowPerson(String name, double height, int nrOfButtons, Color color) {
        this.name = name;
        this.height = height;
        this.nrOfButtons = nrOfButtons;
        this.color = color;
    }

    //References
    public String name() {
        return this.name;
    }
    public double height() {
        return this.height;
    }
    public int nrOfButtons() {
        return this.nrOfButtons;
    }
    public Color color() {
        return this.color;
    }

    //Methods
    public String toString() {
        return "(" + name + ": " + height + " " + nrOfButtons + " " + color + ")";
    }

    public double width() {
        return (height * (9.0 / 19.0));
    }

    public void paint(SPainter painter) {
        Point2D.Double initialPosition = painter.position();
        Color initialColor = painter.color;
        //Body
        double tempR = (height * (9.0 / 19.0) / 2.0);
        SCircle bottomBody = new SCircle(tempR);
        SCircle centerBody = new SCircle(2.0 * tempR / 3.0);
        SCircle topBody = new SCircle(4.0 * tempR / 9.0);
        painter.setColor(color);
        painter.mbk(bottomBody.radius() + centerBody.radius() - 5.0);
        painter.paint(bottomBody);
        painter.mfd(bottomBody.radius() + centerBody.radius() - 5.0);
        painter.paint(centerBody);
        painter.mfd(centerBody.radius() + topBody.radius() - 5.0);
        painter.paint(topBody);
        //Face
        painter.mfd(topBody.radius() / 4.0);
        painter.setColor(Color.BLACK);
        painter.setFontSize( (int)(topBody.radius() / 1.75439) );
        painter.draw("͡° ͜ʖ ͡°");
        painter.mbk(topBody.radius() / 4.0);
        painter.setColor(color);
        painter.mbk(centerBody.radius() + topBody.radius() - 5.0);
        //Buttons
        SCircle button = new SCircle(centerBody.radius() / 15.785);
        painter.setColor(Color.BLACK);
        painter.mbk( ( ( button.diameter() * (nrOfButtons - 2) ) + (nrOfButtons - 2) ) / 2.0 );
        painter.paint(button);
        for(int i = 2; i <= nrOfButtons; i = i + 1) {
            painter.mfd(button.diameter() + 1);
            painter.paint(button);
        }
        //Invariance
        painter.moveTo(initialPosition);
        painter.setColor(initialColor);
    }

}
