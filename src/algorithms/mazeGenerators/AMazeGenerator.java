package algorithms.mazeGenerators;

public abstract class AMazeGenerator implements IMazeGenerator {

    @Override
    public long measureAlgorithmTimeMillis(int rows, int columns) {
        long before = System.currentTimeMillis();
        generate(rows, columns);
        return System.currentTimeMillis() - before;
    }
}
