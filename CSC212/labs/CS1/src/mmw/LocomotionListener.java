
/*
 *Locomotion Listener Program
 */
package mmw;
import composer.SComposer;

public class LocomotionListener {
    public static void main(String[] args) {
        //Create the new composer
        SComposer e = new SComposer();
        //make the composer print text
        e.text();
        //I like the key A better than C (even thought C# would have been better)
        //Lowers the pitch of the composer by 2 steps
        e.lp(2);
        //Prints out what is going to be play, then plays the code, then calls "space"
        //This is true for every line that starts with "System.out.println"
        System.out.println("e.mms_87_Stroll ..."); e.mms_87_Stroll(); space(e);
        System.out.println("e.mms_87_Hill ..."); e.mms_87_Hill(); space(e);
        System.out.println("e.mms_85_HillFlat ..."); e.mms_85_HillFlat(); space(e);
        System.out.println("e.mms_86_HillStones ..."); e.mms_86_HillStones(); space(e);
        System.out.println("e.mms_87_ZigZag ..."); e.mms_87_ZigZag(); space(e);
        System.out.println("e.mms_87_StrollUpDown ..."); e.mms_87_StrollUpDown(); space(e);
        System.out.println("e.mms_87_ZagZig ..."); e.mms_87_ZagZig(); space(e);
        System.out.println("e.mms_85_HillFlat() ..."); e.mms_85_HillFlat(); space(e);
        System.out.println("e.mms_85_StrollDown ..."); e.mms_85_StrollDown(); space(e);
        System.out.println("e.mms_87_Stroll ..."); e.mms_87_Stroll(); space(e);
        //Makes sure that texts stops printing after the final time "space" is called upon
        e.untext();
    }
    //Code that makes the composer rest for 1/2 sec and change lines in between each score
    private static void space(SComposer e) {
        e.untext(); e.rest(1/2); e.text();
    }
}
