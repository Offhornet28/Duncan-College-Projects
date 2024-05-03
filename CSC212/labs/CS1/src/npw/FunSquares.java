
/*
 * A full version of the FunSquares program
 */

package npw;
import java.awt.Color;
import javax.swing.SwingUtilities;
import painter.SPainter;
import shapes.SSquare;

public class FunSquares {
    //Draw Code
    private void paintTheImage() {
        SPainter jill = new SPainter("Fun Squares", 600, 600);
        SSquare square = new SSquare(150);
        paintYellowSquare(jill, square);
        paintRedSquares(jill, square);
        paintBlueSquares(jill, square);
        paintGreySquares(jill, square);
    }

    private void paintYellowSquare(SPainter jill, SSquare square) {
        //color
        jill.setColor(Color.YELLOW);
        //center
        square.s2(); jill.paint(square); square.x2();
    }

    private void paintRedSquares(SPainter jill, SSquare square) {
        //color
        jill.setColor(Color.RED);
        //top left
        jill.mfd(square.side()); jill.mlt(square.side());
        square.s2(); jill.paint(square); square.x2();
        //top right
        jill.mrt(square.side()*2);
        square.s2(); jill.paint(square); square.x2();
        //reset
        jill.moveToCenter();
    }
    private void paintBlueSquares(SPainter jill, SSquare square) {
        //color
        jill.setColor(Color.BLUE);
        //bottom right
        jill.mbk(square.side()); jill.mrt(square.side());
        square.s2(); jill.paint(square); square.x2();
        //bottom left
        jill.mlt(square.side()*2);
        square.s2(); jill.paint(square); square.x2();
        //reset
        jill.moveToCenter();
    }

    private void paintGreySquares(SPainter jill, SSquare square) {
        //color
        jill.setColor(Color.GRAY);
        //top
        jill.mfd(square.side());
        jill.paint(square);
        //bottom
        jill.mbk(square.side()*2);
        jill.paint(square);
        //left
        jill.mfd(square.side()); jill.mlt(square.side());
        jill.paint(square);
        //right
        jill.mrt(square.side()*2);
        jill.paint(square);
        //reset
        jill.moveToCenter();
    }

    //Required Infrastructure
    public FunSquares() {
        paintTheImage();
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {new FunSquares();}
        });
    }
}