import models.graph.GraphProblemGenerator;
import models.graph.GraphSolver;
import models.graph.MultipleGraphProblem;

public class Application {
    public static void main(String[] args) {
        MultipleGraphProblem multipleGraphProblem =
                new MultipleGraphProblem(new GraphProblemGenerator(System.in, new GraphSolver()).generate());

        multipleGraphProblem.solveProblems();
    }
}
