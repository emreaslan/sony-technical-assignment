package models.graph;

import java.util.List;

public class MultipleGraphProblem {
    private List<GraphProblem> graphProblems;

    public MultipleGraphProblem(List<GraphProblem> graphProblems) {
        this.graphProblems = graphProblems;
    }

    public void solveProblems() {
        graphProblems.forEach(problem -> {
            try {
                System.out.println("Output: " + problem.solve());
            } catch (Exception e) {
                System.out.println("Output: " + e.getMessage());
            }
        });
    }
}
