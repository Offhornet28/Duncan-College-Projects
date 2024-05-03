
/*
 * Name that tune!
 */

package mmw;
import note.SNote;

public class Dorothy {
    public static void main(String[] args) {
        SNote note = new SNote();
        note.text();
        note.x2(); note.play();
        note.rp(7); note.play();
        note.s2(); note.lp(); note.play();
        note.lp(2); note.s2(); note.play();
        note.rp(); note.play();
        note.x2(); note.rp(); note.play();
        note.rp(); note.play();
        System.out.println();
    }
}
