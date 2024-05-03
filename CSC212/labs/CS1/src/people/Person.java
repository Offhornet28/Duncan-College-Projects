
/*
 * Class to model a person in terms of first name, last name, and date of birth
 */

package people;

public class Person implements PersonSpecification {
    //Instance Variables
    private String firstName;
    private String lastName;
    private int month;
    private int day;
    private int year;

    //Constructors
    public Person(String name, int month, int day, int year) {
        int spacePosition = name.indexOf(' ');
        this.firstName = name.substring(0, spacePosition);
        this.lastName = name.substring(spacePosition + 1);
        this.month = month;
        this.day = day;
        this.year = year;
    }
    //Methods
    public String toString() {
        return firstName + " " + lastName + ", born " + month + "/" + day + "/" + year;
    }

    public String firstName() {
        return firstName;
    }

    public String lastName() {
        return lastName;
    }

    public int month() {
        return month;
    }

    public int day() {
        return day;
    }

    public int year() {
        return year;
    }

    public String initials() {
        return firstName.substring(0,1) + lastName.substring(0,1);
    }

    public boolean isBoomer() {
        return (year >= 1946 & year <= 1964);
    }
}
