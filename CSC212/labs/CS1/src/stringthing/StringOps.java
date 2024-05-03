
/*
 * Program to illustrate some basic character string processing functionality
 */

package stringthing;

public class StringOps {
    public static void main(String[] args) {
        //Establish some Strings
        String date = "Wednesday, October 5, 2022";
        String time = "11:30 AM";
        String lab = "String Thing";
        //Compute the lengths of the Strings
        int dateLength = date.length();
        int timeLength = time.length();
        int labLength = lab.length();
        System.out.println("\ndateLength = " + dateLength);
        System.out.println("timeLength = " + timeLength);
        System.out.println("labLength = " + labLength);
        //Compute some positions
        int p1 = date.indexOf(",");
        int p2 = time.indexOf(" ");
        int p3 = lab.indexOf("ing");
        System.out.println("\np1 = " + p1);
        System.out.println("p2 = " + p2);
        System.out.println("p3 = " + p3);
        //Compute some 2 argument Substring values
        System.out.println("\ndate.substring(0,9) = " + date.substring(0,9));
        System.out.println("time.substring(2,4) = " + time.substring(2,4));
        System.out.println("lab.substring(7,8) = " + lab.substring(7,8));
        //Compute some 1 argument Substring values
        System.out.println("\ndate.substring(11) = " + date.substring(11));
        System.out.println("time.substring(2) = " + time.substring(2));
        System.out.println("lab.substring(7) = " + lab.substring(7));
        //Create a String
        String line = date + " | " + time + " | " + lab;
        System.out.println("\nline = " + line);
    }
}
