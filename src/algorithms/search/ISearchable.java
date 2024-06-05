package algorithms.search;

import java.util.ArrayList;

/**
 * Interface representing a searchable domain for search algorithms.
 * Defines the necessary methods for a class to be searchable.
 */
public interface ISearchable {

    /**
     * Gets the start state of the search domain.
     *
     * @return the start state
     */
    AState getStartState();

    /**
     * Gets the goal state of the search domain.
     *
     * @return the goal state
     */
    AState getGoalState();

    /**
     * Gets all possible states that can be reached from the given state.
     *
     * @param s the state from which to find all possible states
     * @return an ArrayList of possible states that can be reached from the given state
     */
    ArrayList<AState> getAllPossibleStates(AState s);

    /**
     * Resets the search domain to its initial state.
     */
    void resetSearch();
}
