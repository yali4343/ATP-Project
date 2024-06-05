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
        assertTrue(best.openList instanceof PriorityQueue);
    }
    @Test
    void testNullSolve(){
        BestFirstSearch best = new BestFirstSearch();
        Solution sol = best.solve(null);
        assertNull(sol);
    }
    @Test
    void testMinusMaze(){
        IMazeGenerator im = new MyMazeGenerator();
        Maze m = im.generate(-1,-1);
        assertEquals(2,m.getColumns());
        assertEquals(2,m.getRows());
    }
}