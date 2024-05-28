package algorithms.maze3D;

import algorithms.search.AState;

import java.util.Objects;

public class Maze3DState extends AState {
    private int depth;
    private int row;
    private int column;

    public Maze3DState(int depth, int row, int column) {
        super("{" + depth + "," + row + "," + column + "}");
        this.depth = depth;
        this.row = row;
        this.column = column;
    }

    public int getDepth() {
        return depth;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Maze3DState that = (Maze3DState) obj;
        return depth == that.depth && row == that.row && column == that.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(depth, row, column);
    }

    @Override
    public String toString() {
        return "{" + depth + "," + row + "," + column + "}";
    }
}
