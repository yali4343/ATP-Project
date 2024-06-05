package algorithms.maze3D;

/**
 * Abstract class that provides a template for 3D maze generation algorithms.
 * Implements the IMaze3DGenerator interface and provides a method to measure
 * the time taken by the 3D maze generation algorithm.
 */
public abstract class AMaze3DGenerator implements IMaze3DGenerator {

    /**
     * Generates a 3D maze with the specified depth, rows, and columns.
     * This method must be implemented by any subclass of AMaze3DGenerator.
     *
     * @param depth the depth of the maze
     * @param row the number of rows in the maze
     * @param column the number of columns in the maze
     * @return a Maze3D object representing the generated maze
     */
    public abstract Maze3D generate(int depth, int row, int column);

    /**
     * Measures the time taken by the 3D maze generation algorithm to generate a maze
     * with the specified depth, rows, and columns.
     *
     * @param depth the depth of the maze
     * @param row the number of rows in the maze
     * @param column the number of columns in the maze
     * @return the time taken by the 3D maze generation algorithm in milliseconds
     */
    @Override
    public long measureAlgorithmTimeMillis(int depth, int row, int column) {
        long StartTime = System.currentTimeMillis();
        generate(depth, row, column);
        long EndTime = System.currentTimeMillis();
        return EndTime - StartTime;
    }
}
