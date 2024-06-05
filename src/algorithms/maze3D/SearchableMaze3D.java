package algorithms.maze3D;

import algorithms.search.AState;
import algorithms.search.ISearchable;

import java.util.ArrayList;

/**
 * Class representing a searchable 3D maze.
 * Implements the ISearchable interface.
 */
public class SearchableMaze3D implements ISearchable {
    private Maze3D maze;
    private byte[][][] MazeMatrix;

    /**
     * Constructor to initialize a searchable 3D maze with a specified maze.
     *
     * @param maze the 3D maze to be made searchable
     */
    public SearchableMaze3D(Maze3D maze) {
        this.maze = maze;
        initMatrix();
    }

    /**
     * Initializes the maze matrix to track visited positions.
     * Sets all positions to 0 (unvisited).
     */
    public void initMatrix() {
        this.MazeMatrix = new byte[this.maze.getDepth()][this.maze.getRows()][this.maze.getColumns()];
        for (int k = 0; k < maze.getDepth(); k++) {
            for (int i = 0; i < maze.getRows(); i++) {
                for (int j = 0; j < maze.getColumns(); j++) {
                    MazeMatrix[k][i][j] = 0;
                }
            }
        }
    }

    /**
     * Gets the start state of the 3D maze.
     *
     * @return the start state of the 3D maze as a Maze3DState object
     */
    @Override
    public AState getStartState() {
        return new Maze3DState(this.maze.getStartPosition());
    }

    /**
     * Gets the goal state of the 3D maze.
     *
     * @return the goal state of the 3D maze as a Maze3DState object
     */
    @Override
    public AState getGoalState() {
        return new Maze3DState(this.maze.getGoalPosition());
    }

    /**
     * Gets all possible states that can be reached from the given state.
     *
     * @param s the current state
     * @return an ArrayList of possible states that can be reached from the given state
     */
    @Override
    public ArrayList<AState> getAllPossibleStates(AState s) {
        ArrayList<AState> solutions = new ArrayList<>();
        int curr_col = ((Maze3DState) s).getCol();
        int curr_row = ((Maze3DState) s).getRow();
        int curr_depth = ((Maze3DState) s).getDepth();
        int left = curr_col - 1;
        int right = curr_col + 1;
        int up = curr_row - 1;
        int down = curr_row + 1;
        int inside = curr_depth + 1;
        int outside = curr_depth - 1;

        // Check possibility for every direction
        if (!maze.isWallOrEmpty(curr_depth, down, curr_col)) {
            allDownAndUpOptions(solutions, curr_depth, down, curr_col);
        }
        if (!maze.isWallOrEmpty(curr_depth, up, curr_col)) {
            allDownAndUpOptions(solutions, curr_depth, up, curr_col);
        }
        if (!maze.isWallOrEmpty(curr_depth, curr_row, right)) {
            allLeftAndRightOptions(solutions, curr_depth, curr_row, right);
        }
        if (!maze.isWallOrEmpty(curr_depth, curr_row, left)) {
            allLeftAndRightOptions(solutions, curr_depth, curr_row, left);
        }
        if (!maze.isWallOrEmpty(inside, curr_row, curr_col)) {
            allDownAndUpOptions(solutions, inside, curr_row, curr_col);
            allLeftAndRightOptions(solutions, inside, curr_row, curr_col);
        }
        if (!maze.isWallOrEmpty(outside, curr_row, curr_col)) {
            allDownAndUpOptions(solutions, outside, curr_row, curr_col);
            allLeftAndRightOptions(solutions, outside, curr_row, curr_col);
        }
        return solutions;
    }

    /**
     * Adds all possible down and up moves to the list of solutions.
     *
     * @param solutions the list of solutions
     * @param depth the current depth index
     * @param row the current row index
     * @param col the current column index
     */
    private void allDownAndUpOptions(ArrayList<AState> solutions, int depth, int row, int col) {
        if (MazeMatrix[depth][row][col] == 0) {
            MazeMatrix[depth][row][col] = 1;
            Maze3DState state = new Maze3DState(new Position3D(depth, row, col));
            state.setCost(10);
            solutions.add(state);
        }
        if (!maze.isWallOrEmpty(depth, row, col - 1) && MazeMatrix[depth][row][col - 1] == 0) {
            MazeMatrix[depth][row][col - 1] = 1;
            Maze3DState state = new Maze3DState(new Position3D(depth, row, col - 1));
            state.setCost(15);
            solutions.add(state);
        }
        if (!maze.isWallOrEmpty(depth, row, col + 1) && MazeMatrix[depth][row][col + 1] == 0) {
            MazeMatrix[depth][row][col + 1] = 1;
            Maze3DState state = new Maze3DState(new Position3D(depth, row, col + 1));
            state.setCost(15);
            solutions.add(state);
        }
    }

    /**
     * Adds all possible left and right moves to the list of solutions.
     *
     * @param solutions the list of solutions
     * @param depth the current depth index
     * @param row the current row index
     * @param col the current column index
     */
    private void allLeftAndRightOptions(ArrayList<AState> solutions, int depth, int row, int col) {
        if (MazeMatrix[depth][row][col] == 0) {
            MazeMatrix[depth][row][col] = 1;
            Maze3DState state = new Maze3DState(new Position3D(depth, row, col));
            state.setCost(10);
            solutions.add(state);
        }
        if (!maze.isWallOrEmpty(depth, row + 1, col) && MazeMatrix[depth][row + 1][col] == 0) {
            MazeMatrix[depth][row + 1][col] = 1;
            Maze3DState state = new Maze3DState(new Position3D(depth, row + 1, col));
            state.setCost(15);
            solutions.add(state);
        }
        if (!maze.isWallOrEmpty(depth, row - 1, col) && MazeMatrix[depth][row - 1][col] == 0) {
            MazeMatrix[depth][row - 1][col] = 1;
            Maze3DState state = new Maze3DState(new Position3D(depth, row - 1, col));
            state.setCost(15);
            solutions.add(state);
        }
    }

    /**
     * Resets the maze matrix to its initial state.
     */
    @Override
    public void resetSearch() {
        initMatrix();
    }
}
