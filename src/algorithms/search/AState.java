package algorithms.search;
import java.io.Serializable;
import java.util.Comparator;

/**
 * Abstract class to represent a state in a search algorithm.
 * Contains information about the state, its cost, and the state it came from.
 */
public abstract class AState implements Serializable {
    private String state;
    protected int cost;
    private AState cameFrom;

    /**
     * Constructor to initialize a state with a specified state description.
     *
     * @param state the description of the state
     */
    public AState(String state) {
        this.state = state;
        this.cameFrom = null;
    }

    /**
     * Checks if this state is equal to another state based on their descriptions.
     *
     * @param state the other state to compare to
     * @return true if the states are equal, else false
     */
    public boolean equals(AState state) {
        return this.state.equals(state.toString());
    }

    /**
     * Sets the state from which this state was reached.
     *
     * @param state the state from which this state was reached
     */
    public void setCameFrom(AState state) {
        this.cameFrom = state;
    }

    /**
     * Gets the state from which this state was reached.
     *
     * @return the state from which this state was reached
     */
    public AState getCameFrom() {
        return this.cameFrom;
    }

    /**
     * Sets the cost associated with this state.
     *
     * @param cost the cost associated with this state
     */
    public void setCost(int cost) {
        this.cost = cost;
    }

    /**
     * Gets the cost associated with this state.
     *
     * @return the cost associated with this state
     */
    public int getCost() {
        return this.cost;
    }

    /**
     * Returns a string representation of the state.
     * This method must be implemented by any subclass of AState.
     *
     * @return a string representation of the state
     */
    public abstract String toString();
}


/**
 * Comparator class to compare two AState objects based on their cost.
 */
class AStateComparator implements Comparator<AState> {

    /**
     * Compares two AState objects based on their cost.
     *
     * @param s1 the first state to compare
     * @param s2 the second state to compare
     * @return a positive integer if the cost of s1 is greater than the cost of s2,
     *         a negative integer if the cost of s1 is less than the cost of s2,
     *         and zero if the costs are equal.
     */
    @Override
    public int compare(AState s1, AState s2) {
        if (s1.getCost() <= s2.getCost())
            return 1;
        return -1;
    }
}
