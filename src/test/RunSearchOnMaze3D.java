package test;

import algorithms.maze3D.IMaze3DGenerator;
import algorithms.maze3D.MyMaze3DGenerator;
import algorithms.maze3D.Maze3D;
import algorithms.maze3D.SearchableMaze3D;
import algorithms.search.*;

import java.util.ArrayList;

public class RunSearchOnMaze3D {
    public static void main(String[] args) {
        IMaze3DGenerator mazeGenerator = new MyMaze3DGenerator();
        Maze3D maze = mazeGenerator.generate(30, 30, 30);

        // Print the generated maze
        System.out.println("Generated 3D Maze:");
        print3DMaze(maze);

        SearchableMaze3D searchableMaze = new SearchableMaze3D(maze);

        solveProblem(searchableMaze, new BreadthFirstSearch());
        solveProblem(searchableMaze, new DepthFirstSearch());
        solveProblem(searchableMaze, new BestFirstSearch());
    }

    private static void solveProblem(ISearchable domain, ISearchingAlgorithm searcher) {
        // Solve a searching problem with a searcher
        Solution solution = searcher.solve(domain);
        System.out.println(String.format("'%s' algorithm - nodes evaluated: %s", searcher.getName(), searcher.getNumberOfNodesEvaluated()));

        // Printing Solution Path
        System.out.println("Solution path:");
        ArrayList<AState> solutionPath = (ArrayList<AState>) solution.getSolutionPath();
        for (int i = 0; i < solutionPath.size(); i++) {
            System.out.println(String.format("%s. %s", i, solutionPath.get(i)));
        }
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
