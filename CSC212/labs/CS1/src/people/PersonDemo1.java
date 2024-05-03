
/*
 * PersonDemo1 is a simple program to create and textually display Person objects.
 */

package people;

public class PersonDemo1 {
    public static void main(String[] args) {
        //Create six person objects
        Person bd = new Person("Bob Dylan", 5, 24, 1941);
        Person nr = new Person("Noomi Rapace", 12, 28, 1974);
        Person pw = new Person("Pharrell Williams", 4, 5, 1973);
        Person fs = new Person("Frank Sinatra", 12, 12, 1915);
        Person dk = new Person("Diana Krall", 11, 16, 1964);
        Person dz = new Person("Duncan Zaug", 6, 19, 2004);
        //Display six person objects
        System.out.println(bd + " " + bd.initials() + " " + bd.isBoomer());
        System.out.println(nr + " " + nr.initials() + " " + nr.isBoomer());
        System.out.println(pw + " " + pw.initials() + " " + pw.isBoomer());
        System.out.println(fs + " " + fs.initials() + " " + fs.isBoomer());
        System.out.println(dk + " " + dk.initials() + " " + dk.isBoomer());
        System.out.println(dz + " " + dz.initials() + " " + dz.isBoomer());

    }
}
