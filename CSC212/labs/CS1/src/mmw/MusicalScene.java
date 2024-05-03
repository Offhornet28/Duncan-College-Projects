
/*
 * Attempt at the Musical Scene challenge program
 */

package mmw;
import composer.SComposer;

public class MusicalScene {
    public static void main(String[] args) {
        //create the composer
        SComposer nut = new SComposer();
        //make the composer print text
        nut.text();
        //key starts on C
        nut.mms_87_Stroll(); //7
        //raise the key to D
        nut.rp(1);
        nut.mms_87_Hill(); //14
        nut.mms_85_HillFlat(); //19
        //raise the key to G
        nut.rp(3);
        nut.mms_86_HillStones(); //24
        //lower the key to F
        nut.lp(1);
        nut.mms_87_Hill(); //31
        //lower the key to D
        nut.lp(2);
        nut.mms_85_StrollDown(); //36
        //lower the key to C
        nut.lp(1);
        nut.mms_87_Stroll(); //43
    }
}
