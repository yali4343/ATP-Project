package algorithms.mazeGenerators;

public class Maze {
    protected int[][] maze; // 2D array representing the maze
    protected int rows; // Number of rows in the maze
    protected int columns; // Number of columns in the maze
    private Position startPosition;
    private Position goalPosition;

    public Maze(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        maze = new int[rows][columns];
        setStartPosition(new Position(0, 0)); // Default start position
        setGoalPosition(new Position(rows - 1, columns - 1)); // Default goal position
    }

    public int[][] getMaze() {
        return maze;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setWall(int rowIndex, int columnIndex) {
        if (rowIndex < 0 || columnIndex < 0 || rowIndex >= this.rows || columnIndex >= this.columns) {
            System.out.println("Invalid rowIndex or columnIndex");
            return;
        }
        maze[rowIndex][columnIndex] = 1;
    }

    public void breakWall(int rowIndex, int columnIndex) {
        if (rowIndex < 0 || columnIndex < 0 || rowIndex >= this.rows || columnIndex >= this.columns) {
            System.out.println("Invalid rowIndex or columnIndex");
            return;
        }
        maze[rowIndex][columnIndex] = 0;
    }

    public Position getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(Position startPosition) {
        this.startPosition = startPosition;
    }

    public Position getGoalPosition() {
        return goalPosition;
    }

    public void setGoalPosition(Position goalPosition) {
        this.goalPosition = goalPosition;
    }

    public void print() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (i == startPosition.getRowIndex() && j == startPosition.getColumnIndex()) {
                    System.out.print("S ");
                } else if (i == goalPosition.getRowIndex() && j == goalPosition.getColumnIndex()) {
                    System.out.print("E ");
                } else {
                    System.out.print((maze[i][j] == 1 ? "1 " : "0 "));
                }
            }
            System.out.println();
        }
    }
}