package algorithms.search;

import java.util.ArrayList;

/**
 * Class representing a solution to a search problem.
 * Contains the goal state and provides a method to retrieve the solution path.
 */
public class Solution {

    private AState goal;

    /**
     * Constructor to initialize the solution with the goal state.
     *
     * @param goal the goal state of the search problem
     */
    public Solution(AState goal) {
        this.goal = goal;
    }

    /**
     * Retrieves the solution path from the start state to the goal state.
     *
     * @return an ArrayList of AState objects representing the solution path
     */
    public ArrayList<AState> getSolutionPath() {
        ArrayList<AState> sol = new ArrayList<>();
        boolean found = false;
        AState curr = this.goal;
        int idx = -1;

        // Trace back from the goal state to the start state
        while (!found) {
            sol.add(++idx, curr);
            curr = curr.getCameFrom();
            if (curr == null)
                found = true;
        }

        // Reverse the solution path to get it from start to goal
        ArrayList<AState> revSol = new ArrayList<>();
        for (int i = sol.size() - 1; i >= 0; i--) {
            revSol.add(sol.get(i));
        }

        return revSol;
    }
}
