package test;

import algorithms.maze3D.IMaze3DGenerator;
import algorithms.maze3D.MyMaze3DGenerator;
import algorithms.maze3D.Maze3D;

public class RunMaze3DGenerator {
    public static void main(String[] args) {
        IMaze3DGenerator mazeGenerator = new MyMaze3DGenerator();

        // Test small maze
        System.out.println("Testing small 3D maze generation:");
        Maze3D smallMaze = mazeGenerator.generate(5, 5, 5);
        print3DMaze(smallMaze);

        // Measure time for large maze
        System.out.println("Measuring time for large 3D maze generation:");
        long time = mazeGenerator.measureAlgorithmTimeMillis(500, 500, 500);
        System.out.println("Time taken to generate 500x500x500 maze: " + time + " ms");
    }

    private static void print3DMaze(Maze3D maze) {
        int[][][] map = maze.getMap();
        for (int d = 0; d < map.length; d++) {
            System.out.println("Depth " + d + ":");
            for (int r = 0; r < map[d].length; r++) {
                for (int c = 0; c < map[d][r].length; c++) {
                    System.out.print(map[d][r][c] + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }
}
