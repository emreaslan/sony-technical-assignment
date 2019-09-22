package models.graph;

import messages.ExceptionMessages;
import validators.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class GraphProblemGenerator {
    private static final int minNumOfCity = 2;
    private static final int maxNumOfCity = 600;
    private static final int maxNumOfProblems = 1000;
    private InputStream inputStream;
    private GraphProblemValidatorIfc<Integer[]> graphProblemValidator;
    private GraphProblemSolverIfc solver;

    public GraphProblemGenerator(InputStream inputStream, GraphProblemSolverIfc solver,
                                 GraphProblemValidatorIfc<Integer[]> graphProblemValidator) {
        this.inputStream = inputStream;
        this.solver = solver;
        this.graphProblemValidator = graphProblemValidator;
    }

    public List<GraphProblem> generate() {
        List<GraphProblem> problems = new ArrayList<>();
        IntegerRangeValidatorIfc rangeValidator = new IntegerRangeValidator();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            int numberOfProblems = Integer.parseInt(bufferedReader.readLine());
            rangeValidator.isValidRange(numberOfProblems, 1, maxNumOfProblems, ExceptionMessages.NUM_OF_PROBLEMS_RANGE_ERR);
            while (numberOfProblems > 0) {

                int numberOfCities = Integer.parseInt(bufferedReader.readLine());
                rangeValidator.isValidRange(numberOfCities, minNumOfCity, maxNumOfCity, ExceptionMessages.NUM_OF_CITIES_RANGE_ERR);

                Integer[] roads = Arrays.stream(bufferedReader.readLine()
                                        .split(" "))
                                        .mapToInt(Integer::parseInt)
                                        .boxed()
                                        .toArray(Integer[]::new);
                rangeValidator.isValidRange(roads.length, 0, numberOfCities - 1, ExceptionMessages.NUM_OF_ROADS_RANGE_ERR);
                graphProblemValidator.isValidGraphProblem(roads);

                problems.add(new GraphProblem.Builder(roads).withNumberOfCity(numberOfCities).withSolver(solver).build());

                --numberOfProblems;
            }
        } catch (NumberFormatException e) {
            System.err.print(ExceptionMessages.NUMBER_FORMAT_ERR);
        } catch (IOException e) {
            System.err.print(ExceptionMessages.INPUT_ERROR_MESSAGE);
        } catch (Exception e) {
            System.err.print(e.getMessage());
        }
        return problems;
    }


}
