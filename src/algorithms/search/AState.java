package algorithms.search;

public abstract class AState {
    private String state;

    public AState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract int hashCode();

    @Override
    public abstract String toString();
}
