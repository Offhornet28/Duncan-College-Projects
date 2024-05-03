
/*
 * Model a die in terms of two properties
 *  -order: number of faces
 *  -top: the value of the top face
 */

package chance;

public class Die {
    //Instance Variables (state)
    private int order;
    private int top;

    //Constructors
    public Die() {
        order = 6;
        top = (int)((Math.random() * 6) + 1);
    }
    public Die(int nrOfSides) {
        order = nrOfSides;
        top = (int)((Math.random() * nrOfSides) + 1);
    }

    //Methods (behaviors)
    public int top() {
        return top;
    }
    public void roll() {
        top = (int)((Math.random() * order) + 1);
    }
}
