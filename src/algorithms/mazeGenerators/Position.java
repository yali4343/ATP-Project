package algorithms.mazeGenerators;

import java.io.Serializable;

/**
 * Class to represent a position in a maze.
 * Encapsulates the row and column indices of a position.
 */
public class Position implements Serializable {
    private int row;
    private int column;

    /**
     * Constructs a Position object with the specified row and column indices.
     *
     * @param row the row index of the position
     * @param column the column index of the position
     */
    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Returns the row index of the position.
     *
     * @return the row index
     */
    public int getRowIndex() {
        return row;
    }

    /**
     * Returns the column index of the position.
     *
     * @return the column index
     */
    public int getColumnIndex() {
        return column;
    }

    /**
     * Returns a string representation of the position in the format "{row,column}".
     *
     * @return a string representation of the position
     */
    @Override
    public String toString() {
        return "{" + row + "," + column + "}";
    }
}
