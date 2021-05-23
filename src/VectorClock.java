import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VectorClock {
    private int owner;
    private List <Integer> cells;
    public VectorClock(int n, int owner){
        /**
         * @param n = processes count
         */
        this.owner = owner;
        this.cells = new  ArrayList<Integer>(Collections.nCopies(n, 0));
    }

    private boolean equalTo(VectorClock otherClock) {
        /**
         * ta = tb iff ∀ i, ta[i] = tb[i]
         */

        // return this.cells.get(owner).equals(otherClock.cells.get(owner));
        return this.cells.equals(otherClock.cells);
    }
    
    private boolean isLessThanOrEqualTo(VectorClock otherClock) {
        /**
         * ta ≤ tb iff ∀ i, ta[i] ≤ tb[i]
         */
        for (int i = 0; i < this.cells.size(); i++) {
            if (this.cells.get(i) > otherClock.cells.get(i)) { return false; }
        }
        return true;
    }
    
    private boolean isLessThan(VectorClock otherClock) {
        /**
         * ta < tb iff (ta ≤ tb  ^  ta ≠ tb)
         */
        return this.isLessThanOrEqualTo(otherClock) && !this.equalTo(otherClock);
        /**
        for (int i = 0; i < this.cells.size(); i++) {
            if (this.cells.get(i) >= otherClock.cells.get(i)) { return false; }
        }
        return true;
        */
    }
    
    private boolean isConcurrentWith(VectorClock otherClock){
        /**
         * ta || tb iff (ta !< tb  ^  tb !< ta)
         */
        return !this.isLessThan(otherClock) && !otherClock.isLessThan(this);
    }

    public int compare(VectorClock otherClass) {
        if ( this.isConcurrentWith(otherClass)) { return 0; }
        if ( this.isLessThan(otherClass)) { return -1; }
        return 1;
    }

    public int getOwnerId(){
        return this.owner;
    }
}
