package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import java.util.ArrayList;

/**
 * Class representing a searchable maze.
 * Implements the ISearchable interface.
 */
public class SearchableMaze implements ISearchable {
    private Maze maze;
    private byte[][] MazeMatrix;

    /**
     * Constructor to initialize a searchable maze with a specified maze.
     *
     * @param maze the maze to be made searchable
     */
    public SearchableMaze(Maze maze) {
        this.maze = maze;
        initMatrix();
    }

    /**
     * Initializes the maze matrix to track visited positions.
     * Sets all positions to 0 (unvisited).
     */
    private void initMatrix() {
        if (this.maze == null) {
            return;
        }
        this.MazeMatrix = new byte[this.maze.getRows()][this.maze.getColumns()];
        for (int i = 0; i < maze.getRows(); i++) {
            for (int j = 0; j < maze.getColumns(); j++) {
                this.MazeMatrix[i][j] = 0;
            }
        }
    }

    /**
     * Gets the start state of the maze.
     *
     * @return the start state of the maze as a MazeState object
     */
    @Override
    public AState getStartState() {
        if (this.maze == null) {
            return null;
        }
        return new MazeState(this.maze.getStartPosition());
    }

    /**
     * Gets the goal state of the maze.
     *
     * @return the goal state of the maze as a MazeState object
     */
    @Override
    public AState getGoalState() {
        if (this.maze == null) {
            return null;
        }
        return new MazeState(this.maze.getGoalPosition());
    }

    /**
     * Gets all possible states that can be reached from the given state.
     *
     * @param s the current state
     * @return an ArrayList of possible states that can be reached from the given state
     */
    @Override
    public ArrayList<AState> getAllPossibleStates(AState s) {
        if (s == null || this.maze == null) {
            return null;
        }
        ArrayList<AState> solutions = new ArrayList<>();
        int curr_col = ((MazeState) s).getCol();
        int curr_row = ((MazeState) s).getRow();
        int left = curr_col - 1;
        int right = curr_col + 1;
        int up = curr_row - 1;
        int down = curr_row + 1;

        if (!maze.isPositionWallOrEmpty(down, curr_col)) {
            allDownAndUpOptions(solutions, down, curr_col);
        }
        if (!maze.isPositionWallOrEmpty(curr_row, right)) {
            allLeftAndRightOptions(solutions, curr_row, right);
        }
        if (!maze.isPositionWallOrEmpty(up, curr_col)) {
            allDownAndUpOptions(solutions, up, curr_col);
        }
        if (!maze.isPositionWallOrEmpty(curr_row, left)) {
            allLeftAndRightOptions(solutions, curr_row, left);
        }
        return solutions;
    }

    /**
     * Adds all possible down and up moves to the list of solutions.
     *
     * @param solutions the list of solutions
     * @param row the current row index
     * @param col the current column index
     */
    private void allDownAndUpOptions(ArrayList<AState> solutions, int row, int col) {
        if (MazeMatrix[row][col] == 0) {
            MazeMatrix[row][col] = 1;
            MazeState state = new MazeState(new Position(row, col));
            state.setCost(10);
            solutions.add(state);
        }
        if (!maze.isPositionWallOrEmpty(row, col + 1) && MazeMatrix[row][col + 1] == 0) {
            MazeMatrix[row][col + 1] = 1;
            MazeState state = new MazeState(new Position(row, col + 1));
            state.setCost(15);
            solutions.add(state);
        }
        if (!maze.isPositionWallOrEmpty(row, col - 1) && MazeMatrix[row][col - 1] == 0) {
            MazeMatrix[row][col - 1] = 1;
            MazeState state = new MazeState(new Position(row, col - 1));
            state.setCost(15);
            solutions.add(state);
        }
    }

    /**
     * Adds all possible left and right moves to the list of solutions.
     *
     * @param solutions the list of solutions
     * @param row the current row index
     * @param col the current column index
     */
    private void allLeftAndRightOptions(ArrayList<AState> solutions, int row, int col) {
        if (MazeMatrix[row][col] == 0) {
            MazeMatrix[row][col] = 1;
            MazeState state = new MazeState(new Position(row, col));
            state.setCost(10);
            solutions.add(state);
        }
        if (!maze.isPositionWallOrEmpty(row + 1, col) && MazeMatrix[row + 1][col] == 0) {
            MazeMatrix[row + 1][col] = 1;
            MazeState state = new MazeState(new Position(row + 1, col));
            state.setCost(15);
            solutions.add(state);
        }
        if (!maze.isPositionWallOrEmpty(row - 1, col) && MazeMatrix[row - 1][col] == 0) {
            MazeMatrix[row - 1][col] = 1;
            MazeState state = new MazeState(new Position(row - 1, col));
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
