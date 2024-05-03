
/*
 * Programming challenge 3 problem 4 program
 * All number values do not have specified units
 */

package shapes;
import java.util.Scanner;

public class ShippingContainer {

    public static void main(String[] args) {
        //Prompt the user for values
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a width value: ");
        double containerWidth = scanner.nextDouble();
        System.out.print("Enter a length value: ");
        double containerLength = scanner.nextDouble();
        System.out.print("Enter a height value: ");
        double containerHeight =  scanner.nextDouble();
        //Solve for the diagonal
        SRectangle face = new SRectangle(containerWidth, containerLength);
        double keyLength = face.diagonal();
        SRectangle key = new SRectangle(containerHeight, keyLength);
        double distance = key.diagonal();
        //Display the results
        System.out.println("W: " + containerWidth);
        System.out.println("L: " + containerLength);
        System.out.println("H: " + containerHeight);
        System.out.println("Diagonal Distance of Interest: " + distance);
    }
}
