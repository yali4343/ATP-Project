package algorithms.maze3D;
import algorithms.search.AState;


/**
 * Class representing a state in a 3D maze search problem.
 * Extends the abstract class AState.
 */
public class Maze3DState extends AState {
    private Position3D state;

    /**
     * Constructor to initialize a 3D maze state with the specified position.
     *
     * @param pos the position of the maze state
     */
    public Maze3DState(Position3D pos) {
        super(pos.toString());
        this.state = pos;
    }

    /**
     * Gets the row index of the 3D maze state.
     *
     * @return the row index
     */
    public int getRow() {
        return state.getRowIndex();
    }

    /**
     * Gets the column index of the 3D maze state.
     *
     * @return the column index
     */
    public int getCol() {
        return state.getColumnIndex();
    }

    /**
     * Gets the depth index of the 3D maze state.
     *
     * @return the depth index
     */
    public int getDepth() {
        return state.getDepthIndex();
    }

    /**
     * Returns a string representation of the 3D maze state.
     *
     * @return a string representation of the 3D maze state
     */
    @Override
    public String toString() {
        return state.toString();
    }
}

