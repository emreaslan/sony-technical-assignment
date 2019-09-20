import models.GraphProblemGenerator;
import models.GraphProblemSolver;

public class Application {
    public static void main(String[] args) {
        GraphProblemSolver.solveProblems(GraphProblemGenerator.generateProblems(System.in));
    }
}
