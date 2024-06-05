package algorithms.maze3D;
import java.util.Random;

/**
 * Class to generate a 3D maze using a custom algorithm.
 * Extends the abstract class AMaze3DGenerator.
 */
public class MyMaze3DGenerator extends AMaze3DGenerator {
    private Random rand = new Random();

    /**
     * Generates a 3D maze with the specified depth, rows, and columns.
     * The algorithm starts with a full maze of walls and carves out paths.
     *
     * @param depth the depth of the maze
     * @param row the number of rows in the maze
     * @param column the number of columns in the maze
     * @return a Maze3D object representing the generated maze
     */
    @Override
    public Maze3D generate(int depth, int row, int column) {
        Maze3D maze = generateFullMaze(depth, row, column);
        maze.changePositionValue(0, row - 1, column - 1, 0);
        int choose = -1;
        for (int k = 0; k < depth; k++) {
            for (int i = row - 1; i >= 0; i--) {
                for (int j = column - 1; j >= 0; j--) {
                    choose = getDirection(k, i, j, depth - 1);
                    switch (choose) {
                        case 0:
                            maze.changePositionValue(k, i, j - 1, 0);
                            break;
                        case 1:
                            maze.changePositionValue(k, i - 1, j, 0);
                            break;
                        case 2:
                            maze.changePositionValue(k + 1, i, j, 0);
                            break;
                        case -1:
                            break;
                    }
                }
            }
        }
        return maze;
    }

    /**
     * Generates a full 3D maze filled with walls (1s).
     *
     * @param depth the depth of the maze
     * @param row the number of rows in the maze
     * @param column the number of columns in the maze
     * @return a Maze3D object representing the full maze filled with walls
     */
    private Maze3D generateFullMaze(int depth, int row, int column) {
        Maze3D maze = new Maze3D(depth, row, column);
        for (int k = 0; k < depth; k++) {
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < column; j++) {
                    maze.changePositionValue(k, i, j, 1);
                }
            }
        }
        return maze;
    }

    /**
     * Determines the direction to carve out a path in the maze.
     *
     * @param depth the current depth index
     * @param row the current row index
     * @param column the current column index
     * @param max_depth the maximum depth of the maze
     * @return an integer representing the direction to carve:
     *         -1 for no direction,
     *         0 for left,
     *         1 for up,
     *         2 for deeper
     */
    private int getDirection(int depth, int row, int column, int max_depth) {
        int direction;
        if (column == 0 && row == 0 && depth == max_depth) {
            direction = -1;
        } else if (column == 0 && row == 0) {
            direction = 2;
        } else if (column == 0 && depth == max_depth) {
            direction = 1;
        } else if (row == 0 && depth == max_depth) {
            direction = 0;
        } else if (column == 0) {
            direction = rand.nextInt(2);
            if (direction == 0) {
                direction = 2;
            }
        } else if (row == 0) {
            direction = rand.nextInt(2);
            if (direction == 1) {
                direction = 2;
            }
        } else if (depth == max_depth) {
            direction = rand.nextInt(2);
        } else {
            direction = rand.nextInt(3);
        }
        return direction;
    }
}
