
/*
 * Variant of the traditional starter program that features a widget.
 */

package greetings;
import javax.swing.JOptionPane;

public class HelloYou {
    public static void main(String[] args){
        String name = JOptionPane.showInputDialog(null, "Who are you?");
        System.out.println("Hello, " + name + "!");
    }
}
