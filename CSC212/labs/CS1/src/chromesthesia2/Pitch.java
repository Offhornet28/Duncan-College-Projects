
/*
 * The Pitch class models the pitch of a note in a manner that will facilitate
 * the chromesthetic processing of the pitch. A Pitch object will have five
 * properties:
 * - String name | ABC notation pitch name
 * - SPainter painter | the painting agent
 * - Note note | a note that will be set to the pitch corresponding to the ABC notation pitch name
 * - SRectangle box | an SRectangle object that will chromesthetically represent the pitch
 * - Color color | the color associated with the pitch for the presumed chromesthete
 */

package chromesthesia2;
import java.awt.Color;
import note.SNote;
import painter.SPainter;
import shapes.SRectangle;

public class Pitch {
    //Instance Variables
    private String abcName;
    private SPainter painter;
    private SRectangle box;
    private SNote note;
    private Color color;

    //Constructors
    public Pitch(String abcName, SPainter painter) {
        this.abcName = abcName;
        this.painter = painter;
        this.box = new SRectangle(painter.painterHeight-50,painter.painterWidth-50);
        this.note = createNoteForThisPitch(abcName);
        this.color = getPitchClassColor(abcName.substring(0,1).toUpperCase());
    }

    //References
    public String toString() {
        return "[ " + abcName + " | " + note.degree() + " | " + color + " ]";
    }

    public String abcName() {
        return abcName;
    }

    //Methods
    private SNote createNoteForThisPitch(String abcPitchClassName) {
        SNote note = new SNote();
        if(abcPitchClassName.equals("A")) { //A
            note.lp(2);
        } else if(abcPitchClassName.equals("A,")) {
            note.lp(9);
        } else if(abcPitchClassName.equals("a")) {
            note.rp(5);
        } else if(abcPitchClassName.equals("B")) { //B
            note.lp(1);
        } else if(abcPitchClassName.equals("B,")) {
            note.lp(8);
        } else if(abcPitchClassName.equals("b")) {
            note.rp(6);
        } else if(abcPitchClassName.equals("C")) { //C
            note.rp(0);
        } else if(abcPitchClassName.equals("C,")) {
            note.lp(7);
        } else if(abcPitchClassName.equals("c")) {
            note.rp(7);
        } else if(abcPitchClassName.equals("D")) { //D
            note.rp(1);
        } else if(abcPitchClassName.equals("D,")) {
            note.lp(6);
        } else if(abcPitchClassName.equals("d")) {
            note.rp(8);
        } else if(abcPitchClassName.equals("E")) { //E
            note.rp(2);
        } else if(abcPitchClassName.equals("E,")) {
            note.lp(5);
        } else if(abcPitchClassName.equals("e")) {
            note.rp(9);
        } else if(abcPitchClassName.equals("F")) { //F
            note.rp(3);
        } else if(abcPitchClassName.equals("F,")) {
            note.lp(4);
        } else if(abcPitchClassName.equals("f")) {
            note.rp(10);
        } else if(abcPitchClassName.equals("G")) { //G
            note.rp(5);
        } else if(abcPitchClassName.equals("G,")) {
            note.lp(3);
        } else if(abcPitchClassName.equals("g")) {
            note.rp(11);
        }
        return note;
    }

    public void play(String d) {
        painter.setColor(color);
        painter.paint(box);
        painter.setColor(randomColor());
        painter.draw(box);
        if(d.equals("1")) {
            note.play();
        } else if(d.equals("2")) {
            note.x2();
            note.play();
            note.s2();
        } else if(d.equals("1/2")) {
            note.s2();
            note.play();
            note.x2();
        } else if(d.equals("3")) {
            note.x3();
            note.play();
            note.s3();
        } else if(d.equals("1/3")) {
            note.s3();
            note.play();
            note.x3();
        } else if(d.equals("2/3")) {
            note.s3(); note.x2();
            note.play();
            note.s2(); note.x3();
        }
    }

    //Infrastructure
    private Color getPitchClassColor(String letter) {
        if(letter.equals("A")) {
            return new Color(0,0,255);
        } else if(letter.equals("B")) {
            return new Color(0,255,0);
        } else if(letter.equals("C")) {
            return  new Color(127,0,127);
        } else if(letter.equals("D")) {
            return new Color(255,255,0);
        } else if(letter.equals("E")) {
            return new Color(255,0,0);
        } else if(letter.equals("F")) {
            return new Color(255,127,0);
        } else if(letter.equals("G")) {
            return new Color(0,255,255);
        } else {
            return Color.BLACK;
        }
    }

    private static Color randomColor() {
        int rv = (int)(Math.random()*256);
        int gv = (int)(Math.random()*256);
        int bv = (int)(Math.random()*256);
        return new Color(rv, gv, bv);
    }

}
