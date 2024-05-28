package algorithms.maze3D;

import algorithms.search.AState;
import algorithms.search.ISearchable;

import java.util.ArrayList;
import java.util.List;

public class SearchableMaze3D implements ISearchable {
    private Maze3D maze;

    public SearchableMaze3D(Maze3D maze) {
        this.maze = maze;
    }

    @Override
    public AState getStartState() {
        Position3D startPosition = maze.getStartPosition();
        return new Maze3DState(startPosition.getDepthIndex(), startPosition.getRowIndex(), startPosition.getColumnIndex());
    }

    @Override
    public AState getGoalState() {
        Position3D goalPosition = maze.getGoalPosition();
        return new Maze3DState(goalPosition.getDepthIndex(), goalPosition.getRowIndex(), goalPosition.getColumnIndex());
    }

    @Override
    public List<AState> getAllPossibleStates(AState state) {
        List<AState> possibleStates = new ArrayList<>();
        Maze3DState mazeState = (Maze3DState) state;
        int depth = mazeState.getDepth();
        int row = mazeState.getRow();
        int column = mazeState.getColumn();

        // Define movements: in, out, up, down, left, right
        int[][] movements = {
                {1, 0, 0}, {-1, 0, 0},  // in, out
                {0, 1, 0}, {0, -1, 0},  // up, down
                {0, 0, 1}, {0, 0, -1}   // left, right
        };

        for (int[] movement : movements) {
            int newDepth = depth + movement[0];
            int newRow = row + movement[1];
            int newColumn = column + movement[2];

            if (isValidMove(newDepth, newRow, newColumn)) {
                possibleStates.add(new Maze3DState(newDepth, newRow, newColumn));
            }
        }
        return possibleStates;
    }

    private boolean isValidMove(int depth, int row, int column) {
        if (depth < 0 || row < 0 || column < 0 || depth >= maze.getMap().length || row >= maze.getMap()[0].length || column >= maze.getMap()[0][0].length) {
            return false;
        }
        return maze.getMap()[depth][row][column] == 0;
    }
}
