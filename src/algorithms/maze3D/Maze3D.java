package algorithms.maze3D;

public class Maze3D {
    private int[][][] map;
    private Position3D startPosition;
    private Position3D goalPosition;

    public Maze3D(int[][][] map, Position3D startPosition, Position3D goalPosition) {
        this.map = map;
        this.startPosition = startPosition;
        this.goalPosition = goalPosition;
    }

    public int[][][] getMap() {
        return map;
    }

    public Position3D getStartPosition() {
        return startPosition;
    }

    public Position3D getGoalPosition() {
        return goalPosition;
    }
}
