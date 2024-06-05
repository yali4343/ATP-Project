package algorithms.mazeGenerators;

/**
 * Abstract class that provides a template for maze generation algorithms.
 * Implements the IMazeGenerator interface and provides a method to measure
 * the time taken by the maze generation algorithm.
 */
public abstract class AMazeGenerator implements IMazeGenerator {

    /**
     * Measures the time taken by the maze generation algorithm to generate a maze
     * with the specified number of rows and columns.
     *
     * @param rows the number of rows in the maze
     * @param columns the number of columns in the maze
     * @return the time taken by the maze generation algorithm in milliseconds
     */
    @Override
    public long measureAlgorithmTimeMillis(int rows, int columns) {
        long before = System.currentTimeMillis();
        generate(rows, columns);
        return System.currentTimeMillis() - before;
    }
}
