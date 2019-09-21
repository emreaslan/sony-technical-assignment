import models.graph.GraphProblemGenerator;
import models.graph.GraphProblemSolver;

public class Application {
    public static void main(String[] args) {
        GraphProblemSolver.solveProblems(new GraphProblemGenerator(System.in).generate());
    }
}
