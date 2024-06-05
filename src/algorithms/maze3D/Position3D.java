package algorithms.maze3D;

/**
 * Class representing a position in a 3D maze.
 */
public class Position3D {
    private int depthIndex;
    private int rowIndex;
    private int columnIndex;

    /**
     * Constructor to initialize a position in a 3D maze with the specified depth, row, and column indices.
     *
     * @param depthIndex the depth index of the position
     * @param rowIndex the row index of the position
     * @param columnIndex the column index of the position
     */
    public Position3D(int depthIndex, int rowIndex, int columnIndex) {
        this.depthIndex = depthIndex;
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
    }

    /**
     * Gets the depth index of the position.
     *
     * @return the depth index
     */
    public int getDepthIndex() {
        return depthIndex;
    }

    /**
     * Gets the row index of the position.
     *
     * @return the row index
     */
    public int getRowIndex() {
        return rowIndex;
    }

    /**
     * Gets the column index of the position.
     *
     * @return the column index
     */
    public int getColumnIndex() {
        return columnIndex;
    }

    /**
     * Returns a string representation of the position in the format "{depthIndex,rowIndex,columnIndex}".
     *
     * @return a string representation of the position
     */
    @Override
    public String toString() {
        return "{" + depthIndex + "," + rowIndex + "," + columnIndex + "}";
    }
}
