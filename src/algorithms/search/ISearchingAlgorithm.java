package algorithms.search;

/**
 * Interface representing a searching algorithm.
 * Defines the necessary methods for a class to implement a search algorithm.
 */
public interface ISearchingAlgorithm {

    /**
     * Solves the given search domain.
     *
     * @param domain the search domain to solve
     * @return a Solution object representing the solution to the search problem
     */
    Solution solve(ISearchable domain);

    /**
     * Gets the name of the searching algorithm.
     *
     * @return the name of the searching algorithm
     */
    String getName();

    /**
     * Gets the number of nodes evaluated by the searching algorithm.
     *
     * @return the number of nodes evaluated
     */
    int getNumberOfNodesEvaluated();
}
