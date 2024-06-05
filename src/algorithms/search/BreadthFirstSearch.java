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
        this.openList = new LinkedList<AState>();
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
        if (domain == null) {
            return null;
        }
        Solution solution;
        AState start = domain.getStartState();
        AState goal = domain.getGoalState();
        this.openList.add(start);
        AState curr;
        while (!this.openList.isEmpty()) {
            curr = popOpenList();
            if (curr.equals(goal)) {
                this.openList.add(goal);
                solution = new Solution(curr);
                domain.resetSearch();
                return solution;
            }
            ArrayList<AState> neighbors = domain.getAllPossibleStates(curr);
            for (AState s : neighbors) {
                s.setCost(s.getCost() + curr.getCost());
                this.openList.add(s);
                s.setCameFrom(curr);
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
        return "BreadthFirstSearch";
    }
}
