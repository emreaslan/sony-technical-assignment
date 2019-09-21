import models.graph.GraphProblemGenerator;
import models.graph.GraphProblemSolver;
import models.graph.MultipleGraphProblem;

public class Application {
    public static void main(String[] args) {
        MultipleGraphProblem multipleGraphProblem =
                new MultipleGraphProblem(new GraphProblemGenerator(System.in, new GraphProblemSolver()).generate());

        multipleGraphProblem.solveProblems().forEach(System.out::println);
    }
}
