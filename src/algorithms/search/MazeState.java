package algorithms.search;

import algorithms.mazeGenerators.Position;

/**
 * Class representing a state in a maze search problem.
 * Extends the abstract class AState.
 */
public class MazeState extends AState {
    private Position state;

    /**
     * Constructor to initialize a maze state with the specified position.
     *
     * @param state the position of the maze state
     */
    public MazeState(Position state) {
        super(state.toString());
        this.state = state;
    }

    /**
     * Gets the row index of the maze state.
     *
     * @return the row index
     */
    public int getRow() {
        return this.state.getRowIndex();
    }

    /**
     * Gets the column index of the maze state.
     *
     * @return the column index
     */
    public int getCol() {
        return this.state.getColumnIndex();
    }

    /**
     * Returns a string representation of the maze state.
     *
     * @return a string representation of the maze state
     */
    @Override
    public String toString() {
        return state.toString();
    }
}
