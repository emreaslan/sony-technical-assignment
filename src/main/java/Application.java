import models.graph.*;
import validators.GraphProblemValidator;

import java.util.List;

public class Application {

    public static List<Integer> compute(){

        GraphProblemDefinitionIfc problemDefinition;
        try {
            problemDefinition = new GraphProblemDefinition(2, 600, 1,1000);
        } catch (Exception e){
            System.err.println(e.getMessage());
            return null;
        }

        MultipleGraphProblem multipleGraphProblem =
                new MultipleGraphProblem(
                        new GraphProblemGenerator(
                                System.in,
                                problemDefinition,
                                new GraphProblemSolver(),
                                new GraphProblemValidator())
                                .generate());

        return  multipleGraphProblem.solveProblems();
    }

    public static void main(String[] args) {
        compute().forEach(System.out::println);
    }
}
