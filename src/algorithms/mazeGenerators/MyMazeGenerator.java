package algorithms.mazeGenerators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class to generate a maze using Prim's algorithm.
 * Extends the abstract class AMazeGenerator.
 */
public class MyMazeGenerator extends AMazeGenerator {

    private Random rand = new Random();

    /**
     * Generates a maze with the specified number of rows and columns.
     * Uses Prim's algorithm to create the maze.
     *
     * @param row the number of rows in the maze
     * @param col the number of columns in the maze
     * @return a Maze object representing the generated maze
     */
    @Override
    public Maze generate(int row, int col) {
        row = Math.max(row, 2);
        col = Math.max(col, 2);

        // Create a maze full of walls
        Maze ret = new Maze(row, col);

        // Initialize the maze with walls
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                ret.changePositionValue(i, j, 1);
            }
        }

        // Create a list to store wall positions
        List<Position> wall_list = new ArrayList<>();

        // Start the maze from position (0, 0)
        ret.changePositionValue(0, 0, 0);

        // Add initial walls to the list
        wall_list.add(new Position(0, 1));
        wall_list.add(new Position(1, 0));

        // Run Prim's algorithm from the start position
        primAlg(ret, wall_list);

        // Ensure the goal position is reachable
        int choose = rand.nextInt(2);
        switch (choose) {
            case 0:
                ret.changePositionValue(row - 1, col - 2, 0);
                break;
            case 1:
                ret.changePositionValue(row - 2, col - 1, 0);
                break;
        }
        ret.changePositionValue(0, 0, 0);
        ret.changePositionValue(row - 1, col - 1, 0);

        return ret;
    }

    /**
     * Runs Prim's algorithm to generate the maze.
     *
     * @param maze the Maze object being generated
     * @param wall_list the list of wall positions to process
     */
    public void primAlg(Maze maze, List<Position> wall_list) {
        if (maze == null) {
            return;
        }
        while (!wall_list.isEmpty()) {
            int wall = rand.nextInt(wall_list.size());
            Position p = wall_list.get(wall);
            wall_list.remove(wall);

            int curr_row = p.getRowIndex();
            int curr_col = p.getColumnIndex();
            int count = checkSurroundings(maze, curr_row, curr_col);

            if (count == 3) {
                maze.changePositionValue(curr_row, curr_col, 0);
                addWalls(maze, wall_list, curr_row, curr_col);
            }
        }
    }

    /**
     * Checks the surrounding positions of a given cell in the maze.
     *
     * @param maze the Maze object being checked
     * @param curr_row the current row index
     * @param curr_col the current column index
     * @return the count of walls or empty positions around the current cell
     */
    public int checkSurroundings(Maze maze, int curr_row, int curr_col) {
        int count = 0;
        if (maze.isPositionWallOrEmpty(curr_row, curr_col + 1)) {
            count += 1;
        }
        if (maze.isPositionWallOrEmpty(curr_row, curr_col - 1)) {
            count += 1;
        }
        if (maze.isPositionWallOrEmpty(curr_row + 1, curr_col)) {
            count += 1;
        }
        if (maze.isPositionWallOrEmpty(curr_row - 1, curr_col)) {
            count += 1;
        }
        return count;
    }

    /**
     * Adds the neighboring walls of a given cell to the wall list.
     *
     * @param maze the Maze object being generated
     * @param wall_list the list of wall positions to add to
     * @param curr_row the current row index
     * @param curr_col the current column index
     */
    public void addWalls(Maze maze, List<Position> wall_list, int curr_row, int curr_col) {
        if (maze.isPositionWall(curr_row, curr_col + 1) && !wall_list.contains(new Position(curr_row, curr_col + 1))) {
            wall_list.add(new Position(curr_row, curr_col + 1));
        }
        if (maze.isPositionWall(curr_row, curr_col - 1) && !wall_list.contains(new Position(curr_row, curr_col - 1))) {
            wall_list.add(new Position(curr_row, curr_col - 1));
        }
        if (maze.isPositionWall(curr_row + 1, curr_col) && !wall_list.contains(new Position(curr_row + 1, curr_col))) {
            wall_list.add(new Position(curr_row + 1, curr_col));
        }
        if (maze.isPositionWall(curr_row - 1, curr_col) && !wall_list.contains(new Position(curr_row - 1, curr_col))) {
            wall_list.add(new Position(curr_row - 1, curr_col));
        }
    }
}
