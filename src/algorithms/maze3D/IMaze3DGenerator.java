package algorithms.maze3D;

/**
 * Interface for 3D maze generation algorithms.
 * Defines methods for generating a 3D maze and measuring the time taken to generate a 3D maze.
 */
public interface IMaze3DGenerator {

    /**
     * Generates a 3D maze with the specified depth, rows, and columns.
     *
     * @param depth the depth of the maze
     * @param row the number of rows in the maze
     * @param column the number of columns in the maze
     * @return a Maze3D object representing the generated maze
     */
    Maze3D generate(int depth, int row, int column);

    /**
     * Measures the time taken by the 3D maze generation algorithm to generate a maze
     * with the specified depth, rows, and columns.
     *
     * @param depth the depth of the maze
     * @param row the number of rows in the maze
     * @param column the number of columns in the maze
     * @return the time taken by the 3D maze generation algorithm in milliseconds
     */
    long measureAlgorithmTimeMillis(int depth, int row, int column);
}
