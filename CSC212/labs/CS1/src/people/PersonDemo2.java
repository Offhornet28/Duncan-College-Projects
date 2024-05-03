
/*
 * PersonDemo2 is a simple program to create and textually display Person objects in an array.
 */

package people;

public class PersonDemo2 {
    public static void main(String[] args) {
        //array of six Person objects
        Person[] people = new Person[6];
        people[0] = new Person("Bob Dylan", 5, 24, 1941);
        people[1] = new Person("Noomi Rapace", 12, 28, 1974);
        people[2] = new Person("Pharrell Williams", 4, 5, 1973);
        people[3] = new Person("Frank Sinatra", 12, 12, 1915);
        people[4] = new Person("Diana Krall", 11, 16, 1964);
        people[5] = new Person("Duncan Zaug", 6, 19, 2004);
        //for loop to display six Person objects
        for(Person p : people) {
            System.out.println(p + " " + p.initials() + " " + p.isBoomer());
        }
    }
}
