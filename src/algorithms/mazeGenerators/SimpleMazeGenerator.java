package algorithms.mazeGenerators;

import java.util.Random;

/**
 * Class to generate a simple random maze.
 * Extends the abstract class AMazeGenerator.
 */
public class SimpleMazeGenerator extends AMazeGenerator {

    /**
     * Generates a maze with the specified number of rows and columns.
     * Randomly fills the maze with walls (1) and paths (0).
     *
     * @param row the number of rows in the maze
     * @param col the number of columns in the maze
     * @return a Maze object representing the generated maze
     */
    @Override
    public Maze generate(int row, int col) {
        // Create random numbers
        Random rand = new Random();
        // Create a maze
        Maze maze = new Maze(row, col);
        // Initialize the maze with random walls and paths
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                // Creates a number between 0 and 1
                int num = rand.nextInt(2);
                // Adds the value to the maze
                maze.changePositionValue(i, j, num);
            }
        }
        // Ensure there is a path from start to end
        generatePath(maze);
        maze.changePositionValue(0, 0, 0);
        maze.changePositionValue(row - 1, col - 1, 0);
        return maze;
    }

    /**
     * Generates a path from the start position to the goal position in the maze.
     *
     * @param maze the Maze object being modified
     */
    public void generatePath(Maze maze) {
        int curr_row = 0;
        int curr_col = 0;
        // Create a random number generator
        Random rand = new Random();
        // Loop to create a path in the random maze
        while (curr_row != maze.getRows() - 1 || curr_col != maze.getColumns() - 1) {
            // Creates a number between 0 and 1
            int num = rand.nextInt(2);
            switch (num) {
                // Move right
                case 0:
                    if (maze.indexValidation(curr_row, curr_col + 1)) {
                        curr_col += 1;
                    }
                    maze.changePositionValue(curr_row, curr_col, 0);
                    break;
                // Move down
                case 1:
                    if (maze.indexValidation(curr_row + 1, curr_col)) {
                        curr_row += 1;
                    }
                    maze.changePositionValue(curr_row, curr_col, 0);
                    break;
            }
        }
    }
}
