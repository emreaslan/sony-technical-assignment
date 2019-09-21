import models.graph.GraphProblemGenerator;
import models.graph.GraphProblemSolver;
import models.graph.MultipleGraphProblem;

import java.util.List;

public class Application {

    public static List<Integer> compute(){
        MultipleGraphProblem multipleGraphProblem =
                new MultipleGraphProblem(new GraphProblemGenerator(System.in, new GraphProblemSolver()).generate());
        return  multipleGraphProblem.solveProblems();
    }

    public static void main(String[] args) {
        compute().forEach(System.out::println);
    }
}
