package algorithms.search;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    private List<AState> path;

    public Solution() {
        path = new ArrayList<>();
    }

    public void addState(AState state) {
        path.add(state);
    }

    public List<AState> getSolutionPath() {
        return path;
    }
}
