package algorithms.search;
import java.util.Queue;

/**
 * Abstract class representing a generic searching algorithm.
 * Implements the ISearchingAlgorithm interface.
 */
public abstract class ASearchingAlgorithm implements ISearchingAlgorithm {

    protected Queue<AState> openList;
    protected int visitedNodes;

    /**
     * Constructor to initialize the searching algorithm.
     * Initializes the number of visited nodes to zero.
     */
    public ASearchingAlgorithm() {
        this.visitedNodes = 0;
    }

    /**
     * Solves the given search domain.
     * This method must be implemented by any subclass of ASearchingAlgorithm.
     *
     * @param domain the search domain to solve
     * @return a Solution object representing the solution to the search problem
     */
    public abstract Solution solve(ISearchable domain);

    /**
     * Pops the next state from the open list and increments the visited nodes count.
     *
     * @return the next state from the open list
     */
    protected AState popOpenList() {
        this.visitedNodes++;
        return this.openList.poll();
    }

    /**
     * Gets the number of nodes evaluated by the searching algorithm.
     *
     * @return the number of nodes evaluated
     */
    public int getNumberOfNodesEvaluated() {
        return this.visitedNodes;
    }
}
