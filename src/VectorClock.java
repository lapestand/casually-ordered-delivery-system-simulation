import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class VectorClock {
    private int owner;
    private List <Integer> vector;

    public static final int CONCURRENT = 0;
    public static final int LESS_THAN = -1;
    public static final int NOT_LESS_THAN = 1;


    public VectorClock(int n, int owner){
        /**
         * @param n = processes count
         * @param owner = Process ID
         */
        this.owner = owner;
        this.vector = new  ArrayList<Integer>(Collections.nCopies(n, 0));
        this.update();
    }

    public VectorClock(int owner, List <Integer> ts){
        /**
         * @param n = processes count
         * @param ts = timestamp value for importing
         */
        this.owner = owner;
        this.vector = new  ArrayList<Integer>(ts);
    }

    private boolean equalTo(VectorClock otherClock) {
        /**
         * ta = tb iff ∀ i, ta[i] = tb[i]
         */

        return this.vector.equals(otherClock.getVector());
    }
    
    private boolean isLessThanOrEqualTo(VectorClock otherClock) {
        /**
         * ta ≤ tb iff ∀ i, ta[i] ≤ tb[i]
         */
        for (int i = 0; i < this.vector.size(); i++) {
            if (this.getCell(i) > otherClock.getCell(i)) { return false; }
        }
        return true;
    }
    
    private boolean isLessThan(VectorClock otherClock) {
        /**
         * ta < tb iff (ta ≤ tb  ^  ta ≠ tb)
         */
        return this.isLessThanOrEqualTo(otherClock) && !this.equalTo(otherClock);
    }
    
    private boolean isConcurrentWith(VectorClock otherClock){
        /**
         * ta || tb iff (ta !< tb  ^  tb !< ta)
         */
        return !this.isLessThan(otherClock) && !otherClock.isLessThan(this);
    }

    public int compare(VectorClock otherClass) {
        // If concurrent return 0 
        if ( this.isConcurrentWith(otherClass)) { return CONCURRENT; }

        // if lessthan return -1
        if ( this.isLessThan(otherClass)) { return LESS_THAN; }

        // if not less than return 1
        return NOT_LESS_THAN;
    }

    public int getOwnerId(){
        return this.owner;
    }

    public List<Integer> getVector(){
        return this.vector;
    }

    public int getCell(int index){
        return this.vector.get(index);
    }
    
    public void setCell(int index, int newValue){
        this.vector.set(index, newValue);
    }

    public void update(VectorClock otherClock) {
        // Update the clock based on otherClock
        for (int i = 0; i < this.getVector().size(); i++) {
            // If any value in otherClock bigger than this.vector, then update that cell
            if (otherClock.getCell(i) > this.getCell(i)) {
                this.setCell(i, otherClock.getCell(i));
            }
        }
    }

    public void update() {
        // Increase the owner process' timestamp 1
        this.setCell(this.owner, this.getCell(this.owner) + 1);
    }

    @Override
    public String toString(){
        return String.format("[ " + this.getVector().stream().map(Object::toString).collect(Collectors.joining(", ")) + " ]" + " for n = " + this.getVector().size());
    }

}
