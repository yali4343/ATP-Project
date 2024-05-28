package algorithms.mazeGenerators;

import java.util.Random;

public class SimpleMazeGenerator extends AMazeGenerator{
    @Override
    public Maze generate(int rows, int columns) {
        Maze maze = new Maze(rows, columns);
        Random rand = new Random();
        // randomly fill the maze with 1s (walls) and 0s (paths)
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                maze.getMaze()[i][j] = rand.nextInt(2); // generates either 0 or 1 randomly
            }
        }
        return maze;
    }
}
