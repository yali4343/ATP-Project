package algorithms.mazeGenerators;

/**
 * Interface for maze generation algorithms.
 * Defines methods for generating a maze and measuring the time taken to generate a maze.
 */
public interface IMazeGenerator {

    /**
     * Generates a maze with the specified number of rows and columns.
     *
     * @param rows the number of rows in the maze
     * @param columns the number of columns in the maze
     * @return a Maze object representing the generated maze
     */
    Maze generate(int rows, int columns);

    /**
     * Measures the time taken by the maze generation algorithm to generate a maze
     * with the specified number of rows and columns.
     *
     * @param rows the number of rows in the maze
     * @param columns the number of columns in the maze
     * @return the time taken by the maze generation algorithm in milliseconds
     */
    long measureAlgorithmTimeMillis(int rows, int columns);
}
