
/*
 * Program to make use of the Die class
 */

package chanceapps;
import chance.Die;

public class Roller {
    public static void main(String[] args) {
        //Create a standard die and roll 5 times
        createAndRollStandardDieFiveTimes();
        //Create a 20-sided die and roll 5 times
        createAndRollTwentySidedDieFiveTimes();
        //Create a standard die and roll 20 times
        createAndRollStandardDie(20);
        //Create a standard die and roll 30 times
        createAndRollStandardDie(30);
        //Create a 9-sided die and roll 20 times
        createAndRollNineSidedDie(20);
        //Create a 9-sided die and roll 30 times
        createAndRollNineSidedDie(30);
        //Ten times, Create a standard die and roll until you get a 1
        System.out.println("Roll a standard Die until 1 lands 10 times... ");
        for(int i = 1; i <= 10; ++i) {
            createAndRollStandardDieFor1();
        }
        System.out.println("\n");
        //Ten times, Create a 12-sided die and roll until you get a 1
        System.out.println("Roll a 12-sided Die until 1 lands 10 times ... ");
        for(int i = 1; i <= 10; ++i) {
            createAndRollTwelveSidedDieFor1();
        }
        System.out.println("\n");
    }

    private static void createAndRollStandardDieFiveTimes() {
        System.out.println("Roll a standard Die 5 times ... ");
        Die die = new Die();
        die.roll(); System.out.print(die.top() + " ");
        die.roll(); System.out.print(die.top() + " ");
        die.roll(); System.out.print(die.top() + " ");
        die.roll(); System.out.print(die.top() + " ");
        die.roll(); System.out.print(die.top() + " ");
        System.out.println("\n");
    }

    private static void createAndRollTwentySidedDieFiveTimes() {
        System.out.println("Roll a 20-sided Die 5 times ... ");
        Die die = new Die(20);
        for (int i = 1; i <= 5; ++i) {
            die.roll(); System.out.print(die.top() + " ");
        }
        System.out.println("\n");
    }

    private static void createAndRollStandardDie(int nrOfTimes) {
        System.out.println("Roll a standard die " + nrOfTimes + " times ...");
        Die lucky = new Die();
        int i = 1;
        while (i <= nrOfTimes) {
            lucky.roll();
            System.out.print(lucky.top() + " ");
            ++i;
        }
        System.out.println("\n");
    }

    private static void createAndRollNineSidedDie(int nrOfTimes) {
        System.out.println("Roll a 9-sided die " + nrOfTimes + " times ...");
        Die lucky = new Die(9);
        for (int i = 1; i <= nrOfTimes; ++i) {
            lucky.roll();
            System.out.print(lucky.top() + " ");
        }
        System.out.println("\n");
    }

    private static void createAndRollStandardDieFor1() {
        Die pain = new Die();
        System.out.print(pain.top() + " ");
        int roll = pain.top();
        while(roll != 1) {
            pain.roll();
            System.out.print(pain.top() + " ");
            roll = pain.top();
        }
        System.out.println();
    }

    private static void createAndRollTwelveSidedDieFor1() {
        Die pain = new Die(12);
        System.out.print(pain.top() + " ");
        int roll = pain.top();
        while(roll != 1) {
            pain.roll();
            System.out.print(pain.top() + " ");
            roll = pain.top();
        }
        System.out.println();
    }
}
