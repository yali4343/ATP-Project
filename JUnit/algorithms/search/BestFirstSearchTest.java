package algorithms.search;

import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import org.junit.jupiter.api.Test;

import java.util.PriorityQueue;

import static org.junit.jupiter.api.Assertions.*;

class BestFirstSearchTest {

    @Test
    void getName() {
        BestFirstSearch best = new BestFirstSearch();
        assertEquals("BestFirstSearch",best.getName());
    }

    @Test
    void testType(){
        BestFirstSearch best = new BestFirstSearch();
        assertTrue(best.aStateQueue instanceof PriorityQueue);
    }

    @Test
    void testNullSolve(){
        BestFirstSearch best = new BestFirstSearch();
        Solution sol = best.solve(null);
        assertNull(sol);
    }

    @Test
    void testInvalidColumnsMaze(){
        IMazeGenerator im = new MyMazeGenerator();
        Maze m = im.generate(100,-50);
        assertEquals(2,m.getColumns());
        assertEquals(100,m.getRows());
    }

    @Test
    void testMinusMaze(){
        IMazeGenerator im = new MyMazeGenerator();
        Maze m = im.generate(-1,-1);
        assertEquals(2,m.getColumns());
        assertEquals(2,m.getRows());
    }

    @Test
    void testInvalidMaze(){
        IMazeGenerator im = new MyMazeGenerator();
        Maze m = im.generate(0,0);
        assertEquals(2,m.getColumns());
        assertEquals(2,m.getRows());
    }

    @Test
    void testInvalidRowsMaze(){
        IMazeGenerator im = new MyMazeGenerator();
        Maze m = im.generate(-50,100);
        assertEquals(100,m.getColumns());
        assertEquals(2,m.getRows());
    }

}