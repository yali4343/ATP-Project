package algorithms.search;

import algorithms.mazeGenerators.*;

import java.util.ArrayList;
import java.util.List;

public class SearchableMaze implements ISearchable {
    private Maze maze;

    public SearchableMaze(Maze maze) {
        this.maze = maze;
    }

    @Override
    public AState getStartState() {
        Position startPosition = maze.getStartPosition();
        return new MazeState(startPosition.getRowIndex(), startPosition.getColumnIndex());
    }

    @Override
    public AState getGoalState() {
        Position goalPosition = maze.getGoalPosition();
        return new MazeState(goalPosition.getRowIndex(), goalPosition.getColumnIndex());
    }

    @Override
    public List<AState> getAllPossibleStates(AState state) {
        List<AState> possibleStates = new ArrayList<>();
        MazeState mazeState = (MazeState) state;
        int row = mazeState.getRow();
        int column = mazeState.getColumn();

        // Define movements: up, down, left, right, and diagonals
        int[][] movements = {
                {-1, 0}, {1, 0}, {0, -1}, {0, 1},    // up, down, left, right
                {-1, -1}, {-1, 1}, {1, -1}, {1, 1}   // diagonals
        };

        for (int[] movement : movements) {
            int newRow = row + movement[0];
            int newColumn = column + movement[1];

            if (isValidMove(row, column, newRow, newColumn)) {
                possibleStates.add(new MazeState(newRow, newColumn));
            }
        }
        return possibleStates;
    }

    private boolean isValidMove(int currentRow, int currentColumn, int newRow, int newColumn) {
        if (newRow < 0 || newColumn < 0 || newRow >= maze.getRows() || newColumn >= maze.getColumns()) {
            return false;
        }
        if (maze.getMaze()[newRow][newColumn] == 1) {
            return false;
        }

        // Check for diagonal movement validity (R-shaped movement)
        if (Math.abs(currentRow - newRow) == 1 && Math.abs(currentColumn - newColumn) == 1) {
            return (maze.getMaze()[currentRow][newColumn] == 0 && maze.getMaze()[newRow][currentColumn] == 0);
        }

        return true;
    }
}
