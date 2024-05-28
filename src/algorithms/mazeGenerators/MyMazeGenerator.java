package algorithms.mazeGenerators;

import java.util.Arrays;
import java.util.Random;

public class MyMazeGenerator extends AMazeGenerator {

    @Override
    public Maze generate(int rows, int columns) {
        Maze maze = new Maze(rows, columns);
        Random rand = new Random();

        // Initialize maze with all walls
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                maze.getMaze()[i][j] = 1;
            }
        }

        // Randomly select starting cell and make it part of the maze
        int startRow = rand.nextInt(rows);
        int startCol = rand.nextInt(columns);
        maze.getMaze()[startRow][startCol] = 0;

        // List of walls: Represents whether each cell in the maze is surrounded by a wall.
        boolean[][] walls = new boolean[rows][columns];

        // Initialize each cell as surrounded by a wall.
        // Initially, all cells are considered as walls and will be removed as the maze is generated.
        for (boolean[] row : walls) {
            Arrays.fill(row, true);
        }


        // Connect starting cell to maze
        walls[startRow][startCol] = false;

        // Prim's algorithm: This loop runs until all walls are removed from the maze,
        // effectively creating a spanning tree of the maze.
        while (hasWalls(walls)) {
            // Choose a random wall from the list of remaining walls
            int[] cell = chooseRandomWall(walls); //returns [row index,column index] of random wall.
            int r = cell[0]; // Row index of the chosen wall
            int c = cell[1]; // Column index of the chosen wall

            // Get a random neighboring cell that is separated by a wall
            int[] neighbor = getRandomNeighbor(r, c, rows, columns, walls);
            int nr = neighbor[0]; // Row index of the neighboring cell
            int nc = neighbor[1]; // Column index of the neighboring cell

            // Calculate the coordinates of the wall to break between the current cell and its neighbor
            int wallR = (r + nr) / 2; // Row index of the wall
            int wallC = (c + nc) / 2; // Column index of the wall

            // Break the wall between the current cell and its neighbor, creating a passage
            maze.getMaze()[wallR][wallC] = 0;

            // Mark the current cell as part of the maze by removing its wall status
            walls[r][c] = false;

            // Mark the neighbor cell as part of the maze by removing its wall status
            walls[nr][nc] = false;
        }


        return maze;
    }

    private boolean hasWalls(boolean[][] walls) {
        for (boolean[] row : walls) {
            for (boolean wall : row) {
                if (wall) {
                    return true;
                }
            }
        }
        return false;
    }

    private int[] chooseRandomWall(boolean[][] walls) {
        Random rand = new Random();
        int row = rand.nextInt(walls.length);
        int col = rand.nextInt(walls[0].length);
        while (!walls[row][col]) {
            row = rand.nextInt(walls.length);
            col = rand.nextInt(walls[0].length);
        }
        return new int[]{row, col};
    }

    private int[] getRandomNeighbor(int row, int col, int rows, int cols, boolean[][] walls) {
        int[][] dirs = {{0, 2}, {2, 0}, {0, -2}, {-2, 0}};
        Random rand = new Random();
        while (true) {
            int[] dir = dirs[rand.nextInt(dirs.length)];
            int nr = row + dir[0];
            int nc = col + dir[1];
            if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && walls[nr][nc]) {
                return new int[]{nr, nc};
            }
        }
    }
}
