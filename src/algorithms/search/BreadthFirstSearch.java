package algorithms.search;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Class representing the Breadth-First Search (BFS) algorithm.
 * Extends the abstract class ASearchingAlgorithm.
 */
public class BreadthFirstSearch extends ASearchingAlgorithm {

    /**
     * Constructor to initialize the BFS algorithm.
     * Initializes the open list as a LinkedList.
     */
    public BreadthFirstSearch() {
        super();
        this.aStateQueue = new LinkedList<AState>();
    }

    /**
     * Solves the given search domain using the BFS algorithm.
     *
     * @param domain the search domain to solve
     * @return a Solution object representing the solution to the search problem,
     *         or null if the domain is null or no solution is found
     */
    @Override
    public Solution solve(ISearchable domain) {
        // Check if the domain is null and return null if it is
        if (domain == null) {
            return null;
        }

        Solution solution;
        // Get the start state from the domain
        AState start = domain.getStartState();
        // Get the goal state from the domain
        AState goal = domain.getGoalState();
        // Add the start state to the queue
        this.aStateQueue.add(start);

        AState curr;
        // Loop until the queue is empty
        while (!this.aStateQueue.isEmpty()) {
            // Pop the current state from the queue
            curr = popRegularQueue();
            // Check if the current state is the goal state
            if (curr.equals(goal)) {
                // If goal is found, add it to the queue and create the solution
                this.aStateQueue.add(goal);
                solution = new Solution(curr);
                // Reset the domain's search status
                domain.resetSearch();
                // Return the found solution
                return solution;
            }
            // Get all possible states from the current state
            ArrayList<AState> neighbors = domain.getAllPossibleStates(curr);
            for (AState s : neighbors) {
                // Update the cost of the neighbor state
                s.setCost(s.getCost() + curr.getCost());
                // Add the neighbor state to the queue
                this.aStateQueue.add(s);
                // Set the current state as the predecessor of the neighbor state
                s.setCameFrom(curr);
            }
        }
        // Return null if no solution is found
        return null;
    }

    /**
     * Gets the name of the searching algorithm.
     *
     * @return the name of the searching algorithm
     */
    @Override
    public String getName() {
        return "BreadthFirstSearch";
    }
}
