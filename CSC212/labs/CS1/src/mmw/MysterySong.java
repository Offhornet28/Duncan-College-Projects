
/*
 *Mystery Song Program
 */
package mmw;
import note.SNote;

public class MysterySong {
    public static void main(String[] args) {
        //Create the note
        SNote note = new SNote();
        //Print what is played
        note.text();
        //Play the note twice
        note.play(); note.play();
        //Pitch the note up 4 steps and play it twice
        note.rp(4); note.play(); note.play();
        //Pitch the note up 1 step and play it twice
        note.rp(1); note.play(); note.play();
        //Pitch the note down 1 step, double its length, and play it. Then rest its length
        note.lp(1); note.x2(); note.play(); note.s2();
        //Pitch the note down 1 step and play it twice
        note.lp(1); note.play(); note.play();
        //Pitch the note down 1 step and play it twice
        note.lp(1); note.play(); note.play();
        //Pitch the note down 1 step and play it twice
        note.lp(1); note.play(); note.play();
        //Pitch the note down 1 step, double its length, and play it. Then rest its length
        note.lp(1); note.x2(); note.play(); note.s2();
    }
}
//ITS TWINKLE TWINKLE LITTLE STAR