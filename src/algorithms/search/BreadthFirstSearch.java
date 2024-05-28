package algorithms.search;

import java.util.*;

public class BreadthFirstSearch extends ASearchingAlgorithm {

    @Override
    public Solution solve(ISearchable searchable) {
        numberOfNodesEvaluated = 0;
        Queue<AState> queue = new LinkedList<>();
        Set<AState> visited = new HashSet<>();
        Map<AState, AState> predecessors = new HashMap<>();

        AState startState = searchable.getStartState();
        AState goalState = searchable.getGoalState();
        queue.add(startState);
        visited.add(startState);

        while (!queue.isEmpty()) {
            AState currentState = queue.poll();
            numberOfNodesEvaluated++;

            if (currentState.equals(goalState)) {
                return constructSolution(predecessors, goalState);
            }

            for (AState neighbor : searchable.getAllPossibleStates(currentState)) {
                if (!visited.contains(neighbor)) {
                    queue.add(neighbor);
                    visited.add(neighbor);
                    predecessors.put(neighbor, currentState);
                }
            }
        }
        return null;
    }

    private Solution constructSolution(Map<AState, AState> predecessors, AState goalState) {
        Solution solution = new Solution();
        AState currentState = goalState;
        while (currentState != null) {
            solution.addState(currentState);
            currentState = predecessors.get(currentState);
        }
        Collections.reverse(solution.getSolutionPath());
        return solution;
    }

    @Override
    public String getName() {
        return "Breadth First Search";
    }
}
