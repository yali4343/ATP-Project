package algorithms.search;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Class representing the Depth-First Search (DFS) algorithm.
 * Extends the abstract class ASearchingAlgorithm.
 */
public class DepthFirstSearch extends ASearchingAlgorithm {

    private Stack<AState> aStateStack;

    /**
     * Constructor to initialize the DFS algorithm.
     * Initializes the open list as a Stack.
     */
    public DepthFirstSearch() {
        super();
        this.aStateStack = new Stack<AState>();
    }

    /**
     * Solves the given search domain using the DFS algorithm.
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
        // Push the start state onto the stack
        this.aStateStack.push(start);

        AState curr;
        // Loop until the stack is empty
        while (!this.aStateStack.isEmpty()) {
            // Pop the current state from the stack
            curr = this.aStateStack.pop();
            // Increment the count of visited nodes
            this.visitedNodes++;
            // Check if the current state is the goal state
            if (curr.equals(goal)) {
                // If goal is found, push it onto the stack and create the solution
                this.aStateStack.push(goal);
                solution = new Solution(curr);
                // Reset the domain's search status
                domain.resetSearch();
                // Return the found solution
                return solution;
            }
            // Get all possible states from the current state
            ArrayList<AState> neighbors = domain.getAllPossibleStates(curr);
            for (AState s : neighbors) {
                // Push the neighbor state onto the stack
                this.aStateStack.push(s);
                // Set the current state as the predecessor of the neighbor state
                s.setCameFrom(curr);
                // Update the cost of the neighbor state
                s.setCost(s.getCost() + curr.getCost());
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
        return "DepthFirstSearch";
    }
}
