
/*
 * This program interprets melodic lines given in ABC notation as a
 * chromesthete might.
 *
 * A Pitch class will be defined, and will take center stage in the
 * processing.
 *
 * Interpreting a melody in ABC notation will amount to flashing
 * colored rectangles for prescribed durations, while sounding
 * the pitch! The color of the rectangle will correspond to pitch
 * class. The duration will correspond to the duration of the note.
 *
 * For this second version of the program, the duration will be held
 * constant at 1 beat.
 *
 * Three sorts of images will appear on the screen, the chromesthetic
 * output box, a text input box, and an error message box. Simplicity
 * of design is rendered by permitting only one box to be on the screen
 * at a time.
 *
 * ABC represents notes in a manner consistent with these examples:
 * A, B, C, D, E, F, G, A B C D E F G a b c d e f g
 *
 * Google ABC music representation if you would like to know more about it.
 */

package chromesthesia1;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import painter.SPainter;

public class Chromesthesia {
    //Infrastructure -- Launching a graphics thread
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new ThreadForGUI());
    }

    private static class ThreadForGUI implements Runnable {
        @Override
        public void run() {
            new Chromesthesia();
        }
    }

    public Chromesthesia() {
        interpreter();
    }

    //Variables
    private static SPainter jill;
    private static Pitch[] pitches;

    //Interpreter
    public static void interpreter() {
        initialization(); //jill and pitches
        while(true) {
            String input = getInput();
            if(input.equalsIgnoreCase("EXIT")) {
                break;
            } else {
                try {
                    playMelody(input,pitches);
                } catch(Exception ex) {
                    showErrorMessage(ex.toString());
                }
            }
        }
        cleanup(); //jill has to go
    }

    //Methods for Chromesthetic Pitches
    private static Pitch[] establishPitches(SPainter painter) {
        Pitch[] pitches = new Pitch[21];
        Pitch pitchMiddleA = new Pitch("A", painter); //A
        pitches[0] = pitchMiddleA;
        Pitch pitchLowA = new Pitch("A,", painter);
        pitches[1] = pitchLowA;
        Pitch pitchHighA = new Pitch("a", painter);
        pitches[2] = pitchHighA;
        Pitch pitchMiddleB = new Pitch("B", painter); //B
        pitches[3] = pitchMiddleB;
        Pitch pitchLowB = new Pitch("B,", painter);
        pitches[4] = pitchLowB;
        Pitch pitchHighB = new Pitch("b", painter);
        pitches[5] = pitchHighB;
        Pitch pitchMiddleC = new Pitch("C", painter); //C
        pitches[6] = pitchMiddleC;
        Pitch pitchLowC = new Pitch("C,", painter);
        pitches[7] = pitchLowC;
        Pitch pitchHighC = new Pitch("c", painter);
        pitches[8] = pitchHighC;
        Pitch pitchMiddleD = new Pitch("D", painter); //D
        pitches[9] = pitchMiddleD;
        Pitch pitchLowD = new Pitch("D,", painter);
        pitches[10] = pitchLowD;
        Pitch pitchHighD = new Pitch("d", painter);
        pitches[11] = pitchHighD;
        Pitch pitchMiddleE = new Pitch("E", painter); //E
        pitches[12] = pitchMiddleE;
        Pitch pitchLowE = new Pitch("E,", painter);
        pitches[13] = pitchLowE;
        Pitch pitchHighE = new Pitch("e", painter);
        pitches[14] = pitchHighE;
        Pitch pitchMiddleF = new Pitch("F", painter); //F
        pitches[15] = pitchMiddleF;
        Pitch pitchLowF = new Pitch("F,", painter);
        pitches[16] = pitchLowF;
        Pitch pitchHighF = new Pitch("f", painter);
        pitches[17] = pitchHighF;
        Pitch pitchMiddleG = new Pitch("G", painter); //G
        pitches[18] = pitchMiddleG;
        Pitch pitchLowG = new Pitch("G,", painter);
        pitches[19] = pitchLowG;
        Pitch pitchHighG = new Pitch("g", painter);
        pitches[20] = pitchHighG;
        return pitches;
    }

    private static Pitch find(String token, Pitch[] pitches) throws Exception {
        for(int i = 0; i < pitches.length; i = i + 1) {
            Pitch pitch = pitches[i];
            if(pitch.abcName().equals(token)) {
                return pitch;
            }
        }
        throw new Exception("### PITCH " + token + " NOT FOUND");
    }

    private static void display(Pitch[] pitches) {
        for(int i = 0; i < pitches.length; i = i + 1) {
            System.out.println(pitches[i].toString());
        }
    }

    private static void playMelody(String input, Pitch[] pitches) throws Exception {
        Scanner scanner = new Scanner(input);
        while(scanner.hasNext()) {
            String token = scanner.next();
            Pitch pitch = find(token, pitches);
            pitch.play("1");
        }
    }

    //Initialization, Cleanup, Getting Input, Error Messaging
    static private void showErrorMessage(String message) {
        jill.setVisible(false);
        JOptionPane.showMessageDialog(null, message);
    }

    private static void initialization() {
        //Establish the Painter
        jill = new SPainter("Chromesthesia",500,500);
        jill.setVisible(false);
        jill.setBrushWidth(7);
        //Establish the Chromestitic Pitch Class Objects
        pitches = establishPitches(jill);
        display(pitches);
    }

    private static String getInput() {
        jill.setVisible(false);
        String label = "Please enter a melody in ABC notation, or EXIT ... ";
        String input = JOptionPane.showInputDialog(null,label);
        jill.setVisible(true);
        if(input == null) {
            input = "";
        }
        return input;
    }

    static private void cleanup() {
        System.exit(0);
    }

}
