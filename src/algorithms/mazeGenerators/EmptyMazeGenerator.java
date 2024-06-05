package algorithms.mazeGenerators;

/**
 * Class to generate an empty maze.
 * Extends the abstract class AMazeGenerator.
 */
public class EmptyMazeGenerator extends AMazeGenerator {

    /**
     * Generates an empty maze with the specified number of rows and columns.
     * The maze will contain only open paths (0) and no walls.
     *
     * @param rows the number of rows in the maze
     * @param columns the number of columns in the maze
     * @return a Maze object representing the generated empty maze
     */
    @Override
    public Maze generate(int rows, int columns) {
        return new Maze(rows, columns);
    }
}
