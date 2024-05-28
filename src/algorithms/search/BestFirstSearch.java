package algorithms.search;

import java.util.*;

public class BestFirstSearch extends BreadthFirstSearch {

    @Override
    public Solution solve(ISearchable searchable) {
        numberOfNodesEvaluated = 0;
        PriorityQueue<MazeState> openList = new PriorityQueue<>(Comparator.comparingInt(this::heuristic));
        Set<AState> closedList = new HashSet<>();
        Map<AState, AState> predecessors = new HashMap<>();

        MazeState startState = (MazeState) searchable.getStartState();
        MazeState goalState = (MazeState) searchable.getGoalState();
        openList.add(startState);

        while (!openList.isEmpty()) {
            MazeState currentState = openList.poll();
            numberOfNodesEvaluated++;

            if (currentState.equals(goalState)) {
                return constructSolution(predecessors, goalState);
            }

            closedList.add(currentState);

            for (AState neighbor : searchable.getAllPossibleStates(currentState)) {
                if (!closedList.contains(neighbor)) {
                    openList.add((MazeState) neighbor);
                    predecessors.put(neighbor, currentState);
                }
            }
        }
        return null;
    }

    private int heuristic(AState state) {
        // Define a heuristic function (for example, Manhattan distance for maze)
        // Placeholder for actual heuristic
        return 0;
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
        return "Best First Search";
    }
}
