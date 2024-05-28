package algorithms.search;

import java.util.*;

public class DepthFirstSearch extends ASearchingAlgorithm {

    @Override
    public Solution solve(ISearchable searchable) {
        numberOfNodesEvaluated = 0;
        Stack<AState> stack = new Stack<>();
        Set<AState> visited = new HashSet<>();
        Map<AState, AState> predecessors = new HashMap<>();

        AState startState = searchable.getStartState();
        AState goalState = searchable.getGoalState();
        stack.push(startState);
        visited.add(startState);

        while (!stack.isEmpty()) {
            AState currentState = stack.pop();
            numberOfNodesEvaluated++;

            if (currentState.equals(goalState)) {
                return constructSolution(predecessors, goalState);
            }

            for (AState neighbor : searchable.getAllPossibleStates(currentState)) {
                if (!visited.contains(neighbor)) {
                    stack.push(neighbor);
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
        return "Depth First Search";
    }
}
