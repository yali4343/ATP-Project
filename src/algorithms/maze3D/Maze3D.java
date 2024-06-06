package algorithms.maze3D;

/**
 * Class to represent a 3D maze.
 */
public class Maze3D {
    private int col;
    private int row;
    private int depth;
    private int[][][] maze;

    /**
     * Constructor to initialize a 3D maze with specified depth, rows, and columns.
     *
     * @param depth the depth of the maze
     * @param row the number of rows in the maze
     * @param col the number of columns in the maze
     */
    public Maze3D(int depth, int row, int col) {
        maze = new int[depth][row][col];
        this.row = row;
        this.col = col;
        this.depth = depth;
    }

    /**
     * Changes the value at a specific position in the maze.
     *
     * @param depth the depth index
     * @param row the row index
     * @param col the column index
     * @param value the value to be placed in the maze (0 or 1)
     */
    public void changePositionValue(int depth, int row, int col, int value) {
        if (!this.indexValidation(depth, row, col) || (value != 0 && value != 1)) {
            System.out.println("Invalid Input: depth " + depth + " row " + row + " col " + col);
            return;
        }
        maze[depth][row][col] = value;
    }

    /**
     * Validates if the given indices are within the bounds of the maze.
     *
     * @param depth the depth index
     * @param row the row index
     * @param col the column index
     * @return true if the indices are valid, else false
     */
    public boolean indexValidation(int depth, int row, int col) {
        if (row >= this.row || col >= this.col || depth >= this.depth || row < 0 || col < 0 || depth < 0) {
            return false;
        }
        return true;
    }

    /**
     * Checks if a position in the maze is a wall or out of bounds.
     *
     * @param depth the depth index
     * @param row the row index
     * @param col the column index
     * @return true if the position is a wall or out of bounds, else false
     */
    public boolean isWallOrEmpty(int depth, int row, int col) {
        if (!this.indexValidation(depth, row, col)) {
            return true;
        }
        return this.maze[depth][row][col] == 1;
    }

    /**
     * Gets the number of columns in the maze.
     *
     * @return the number of columns
     */
    public int getColumns() {
        return col;
    }

    /**
     * Gets the number of rows in the maze.
     *
     * @return the number of rows
     */
    public int getRows() {
        return row;
    }

    /**
     * Gets the depth of the maze.
     *
     * @return the depth
     */
    public int getDepth() {
        return depth;
    }

    /**
     * Prints the 3D maze to the console.
     * Each layer is printed separately.
     */
    public void print() {
        for (int k = 0; k < depth; k++) {
            System.out.println("Layer " + k + ":");
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    System.out.print(maze[k][i][j] + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    /**
     * Gets the start position of the maze.
     *
     * @return the start position as a Position3D object
     */
    public Position3D getStartPosition() {
        return new Position3D(0, 0, 0);
    }

    /**
     * Gets the goal position of the maze.
     *
     * @return the goal position as a Position3D object
     */
    public Position3D getGoalPosition() {
        return new Position3D(0, row - 1, col - 1);
    }

    /**
     * Gets the 3D array representing the maze.
     *
     * @return the maze as a 3D array
     */
    public int[][][] getMaze() {
        return maze;
    }
}
