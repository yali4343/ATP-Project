package algorithms.search;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Class representing the Depth-First Search (DFS) algorithm.
 * Extends the abstract class ASearchingAlgorithm.
 */
public class DepthFirstSearch extends ASearchingAlgorithm {

    private Stack<AState> openList;

    /**
     * Constructor to initialize the DFS algorithm.
     * Initializes the open list as a Stack.
     */
    public DepthFirstSearch() {
        super();
        this.openList = new Stack<AState>();
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
        if (domain == null) {
            return null;
        }
        Solution solution;
        AState start = domain.getStartState();
        AState goal = domain.getGoalState();
        this.openList.push(start);
        AState curr;
        while (!this.openList.isEmpty()) {
            curr = this.openList.pop();
            this.visitedNodes++;
            if (curr.equals(goal)) {
                this.openList.push(goal);
                solution = new Solution(curr);
                domain.resetSearch();
                return solution;
            }
            ArrayList<AState> neighbors = domain.getAllPossibleStates(curr);
            for (AState s : neighbors) {
                this.openList.push(s);
                s.setCameFrom(curr);
                s.setCost(s.getCost() + curr.getCost());
            }
        }
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
