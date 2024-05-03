
/*
 * Program to draw rectangles of stars in the standard output stream. The
 * dimensions of the rectangle are read from the standard input stream.
 */

package npw;
import java.util.Scanner;

public class TextRectangles {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("number of rows? ");
        int nrOfRows = scanner.nextInt();
        System.out.print("number of columns? ");
        int nrOfColumns = scanner.nextInt();
        drawRectangle(nrOfRows, nrOfColumns);
    }
    private static void drawRectangle(int nrOfRows, int nrOfColumns) {
        int i = 1;
        while ( i <= nrOfRows) {
            drawOneRow(nrOfColumns);
            i=i+1;
        }
    }
    private static void drawOneRow(int nrOfColumns) {
        int i = 1;
        while (i <= nrOfColumns) {
            System.out.print("*");
            i = i + 1;
        }
        System.out.println("");
    }
}
