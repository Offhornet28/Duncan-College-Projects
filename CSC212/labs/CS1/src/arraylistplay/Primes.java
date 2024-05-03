
/*
 *A program that uses for-each loops to display values of an ArrayList in different orders.
 */

package arraylistplay;
import java.util.ArrayList;

public class Primes {
    public static void main(String[] args) {
        //primes ArrayList setup
        ArrayList<Integer> primes = new ArrayList<>();
        primes.add(2);
        primes.add(3);
        primes.add(5);
        primes.add(7);
        //primes ArrayList info
        System.out.println("size of primes list = " + primes.size());
        System.out.println("first prime = " + primes.get(0));
        System.out.println("last prime = " + primes.get(3));
        System.out.println("last prime  = " + primes.get(primes.size() - 1));
        //loops & stuff
        System.out.println("\nThe initial list ...");
        for(Integer prime : primes) {
            System.out.println(prime);
        }

        int temp = primes.get(0);
        primes.set(0, primes.get(primes.size() - 1));
        primes.set(primes.size() - 1, temp);

        System.out.println("\nThe final list ...");
        for(Integer prime : primes) {
            System.out.println(prime);
        }
    }
}
