
/*
 * This program allows for the exploration of the construction of the arithmetic expressions in the form of simple
 * problem-solving.
 */
package expressions;
import java.lang.Math;

public class ExpressionsThing {
    public static void main(String args[]) {
        double one = 3.14 * 5 + 5;
        System.out.println("one = " + one);
        double two = 3.14 * (5 + 5);
        System.out.println("two = " + two);
        double three = (3.14 * (5 + 5));
        System.out.println("three = " + three);
        int four = (5 * 6);
        System.out.println("four = " + four);
        double five = (55.0 / 2.0);
        System.out.println("five = " + five);
        double six = (65.0 * (1.0/3.0));
        System.out.println("six = " + six);
        double seven = (six + five);
        System.out.println("seven = " + seven);
        //(PI * (R * R))
        double eight = (3.14 * (11.3 * 11.3));
        System.out.println("eight = " + eight);
        //(S * S)
        double nine = (27.7 * 27.7);
        System.out.println("nine = " + nine);
        double ten = ((eight + nine) / 2.0);
        System.out.println("ten = " + ten);
        double eleven = (243.5 * 0.17);
        System.out.println("eleven = " + eleven);
        //Source: 3,3  Goal: 1
        int twelve = (3 / 3);
        System.out.println("twelve = " + twelve);
        //Source:  4,7,2  Goal: 1
        int thirteen = (7 - (4 + 2));
        System.out.println("thirteen = " + thirteen);
        //Source: 1,3,7,9  Goal: 4
        int fourteen = ((9 - 7) + (3 - 1));
        System.out.println("fourteen = " + fourteen);
        //Source 2,2,4,6,8  Goal: 5
        int fifteen = (((8 * 2) - (6 * 2)) + (2 / 2));
        System.out.println("fifteen = " + fifteen);
    }
}
