
/*
 * This program will do a bit of character string processing.
 */

package stringthing;

public class StringThing {
    public static void main(String[] args) {

        //POINT A: CREATE A PRINT SOME STRINGS THAT REPRESENT NAMES
        String singer = "Holiday, Billie";
        String sculptor = "Claudel, Camille";
        String painter = "Picasso, Pablo";
        String dancer = "Zotto, Osvaldo";
        String self = "Zaug, Duncan";
        System.out.println("\nNames ...");
        System.out.println("singer = " + singer);
        System.out.println("sculptor = " + sculptor);
        System.out.println("painter = " + painter);
        System.out.println("dancer = " + dancer);
        System.out.println("self = " + self);

        //POINT B: COMPUTE AND PRINT THE LENGTHS OF THE STRINGS, WITHOUT LABELS
        int singerLength = singer.length();
        int sculptorLength = sculptor.length();
        int painterLength = painter.length();
        int dancerLength = dancer.length();
        int selfLength = self.length();
        System.out.println("\nName lengths ...");
        System.out.println("singerLength = " + singerLength);
        System.out.println("sculptorLength = " + sculptorLength);
        System.out.println("painterLength = " + painterLength);
        System.out.println("dancerLength = " + dancerLength);
        System.out.println("selfLength = " + selfLength);

        //POINT C: COMPUTE AND PRINT THE LOCATION OF THE COMMA WITHIN EACH STRING, NO LABELS
        int singerCommaPosition = singer.indexOf(',');
        int sculptorCommaPosition = sculptor.indexOf(',');
        int painterCommaPosition = painter.indexOf(',');
        int dancerCommaPosition = dancer.indexOf(',');
        int selfCommaPosition = self.indexOf(',');
        System.out.println("\nComma positions ...");
        System.out.println("singerCommaPosition = " + singerCommaPosition);
        System.out.println("sculptorCommaPosition = " + sculptorCommaPosition);
        System.out.println("painterCommaPosition = " + painterCommaPosition);
        System.out.println("dancerCommaPosition = " + dancerCommaPosition);
        System.out.println("selfCommaPosition = " + selfCommaPosition);

        //POINT D: COMPUTE AND PRINT THE FIVE FIRST NAMES, WITH NO LABELS
        String singerFirst = singer.substring(singerCommaPosition + 2);
        String sculptorFirst = sculptor.substring(sculptorCommaPosition + 2);
        String painterFirst = painter.substring(painterCommaPosition + 2);
        String dancerFirst = dancer.substring(dancerCommaPosition + 2);
        String selfFirst = self.substring(selfCommaPosition + 2);
        System.out.println("\nFirst names ...");
        System.out.println("singerFirst = " + singerFirst);
        System.out.println("sculptorFirst = " + sculptorFirst);
        System.out.println("painterFirst = " + painterFirst);
        System.out.println("dancerFirst = " + dancerFirst);
        System.out.println("selfFirst = " + selfFirst);

        //POINT E: COMPUTE AND PRINT THE FIVE LAST NAMES, WITH NO LABELS
        String singerLast = singer.substring(0,singerCommaPosition);
        String sculptorLast = sculptor.substring(0,sculptorCommaPosition);
        String painterLast = painter.substring(0,painterCommaPosition);
        String dancerLast = dancer.substring(0,dancerCommaPosition);
        String selfLast = self.substring(0,selfCommaPosition);
        System.out.println("\nLast names ...");
        System.out.println("singerLast = " + singerLast);
        System.out.println("sculptorLast = " + sculptorLast);
        System.out.println("painterLast = " + painterLast);
        System.out.println("dancerLast = " + dancerLast);
        System.out.println("selfLast = " + selfLast);

        //POINT F: COMPUTE AND PRINT THE FIRST NAMES, AGAIN
        System.out.println("\nFirst names, once again ...");
        System.out.println(firstName(singer));
        System.out.println(firstName(sculptor));
        System.out.println(firstName(painter));
        System.out.println(firstName(dancer));
        System.out.println(firstName(self));

        //POINT G: COMPUTE AND PRINT THE LAST NAMES, AGAIN
        System.out.println("\nLast names, once again ...");
        System.out.println(lastName(singer));
        System.out.println(lastName(sculptor));
        System.out.println(lastName(painter));
        System.out.println(lastName(dancer));
        System.out.println(lastName(self));

        //POINT H: COMPUTE AND PRINT THE FULL NAMES, NATURAL STYLE
        System.out.println("\nFull names, natural style ...");
        System.out.println(fullName(singer));
        System.out.println(fullName(sculptor));
        System.out.println(fullName(painter));
        System.out.println(fullName(dancer));
        System.out.println(fullName(self));

    }

    private static String firstName(String directoryStyleName) {
        int commaPosition = directoryStyleName.indexOf(',');
        String firstName = directoryStyleName.substring(commaPosition + 2);
        return firstName;

    }
    private static String lastName(String directoryStyleName) {
        int commaPosition = directoryStyleName.indexOf(',');
        String lastName = directoryStyleName.substring(0,commaPosition);
        return lastName;
    }
    private static String fullName(String dsn) {
        String fullName = firstName(dsn) + " " + lastName(dsn);
        return fullName;
    }
}
