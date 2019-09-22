package models.graph;

import java.util.ArrayList;
import java.util.List;

public class MultipleGraphProblem {
    private List<GraphProblem> graphProblems;

    public MultipleGraphProblem(List<GraphProblem> graphProblems) {
        this.graphProblems = graphProblems;
    }

    public List<Integer> solveProblems() {
        List<Integer> solutions = new ArrayList<>();
        graphProblems.forEach(problem -> {
            try {
                solutions.add(problem.solve());
            } catch (Exception e) {
                solutions.add(null);
                System.err.print(e.getMessage());
            }
        });
        return solutions;
    }
}
