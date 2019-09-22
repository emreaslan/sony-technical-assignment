package models.graph;

import messages.ExceptionMessages;
import validators.AcyclicGraphValidator;
import validators.AcyclicGraphValidatorIfc;
import validators.IntegerRangeValidator;
import validators.IntegerRangeValidatorIfc;

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
    private InputStream inputStream;
    private GraphProblemSolverIfc solver;

    public GraphProblemGenerator(InputStream inputStream, GraphProblemSolverIfc solver) {
        this.inputStream = inputStream;
        this.solver = solver;
    }

    public List<GraphProblem> generate() {
        List<GraphProblem> problems = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream)))
        {
            int numberOfProblems = Integer.parseInt(bufferedReader.readLine());
            while (numberOfProblems > 0) {

                int numberOfCities = Integer.parseInt(bufferedReader.readLine());
                validateNumberOfCities(numberOfCities);

                int[] roads = Arrays.stream(bufferedReader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
                validateNumberOfRoads(roads.length, numberOfCities - 1);
                validateRoadPaths(roads);
                validateGraphIsAcyclic(roads);

                problems.add(new GraphProblem.Builder(roads).withNumberOfCity(numberOfCities).withSolver(solver).build());

                --numberOfProblems;
            }
        } catch (NumberFormatException e){
            System.out.println(ExceptionMessages.NUMBER_FORMAT_ERR);
        } catch (IOException e){
            System.out.println(ExceptionMessages.INPUT_ERROR_MESSAGE);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return problems;
    }

    private void validateGraphIsAcyclic(int[] roads) throws Exception{
        AcyclicGraphValidatorIfc<Integer[]> validator = new AcyclicGraphValidator();
        Integer[] roadsBoxed = IntStream.of( roads ).boxed().toArray( Integer[]::new );

        if (!validator.isGraphAcyclic(roadsBoxed)){
            throw new Exception(ExceptionMessages.GRAPH_IS_NOT_ACYCLIC);
        }
    }

    private void validateRoadPaths(int[] roads) throws Exception{
        IntegerRangeValidatorIfc rangeValidator = new IntegerRangeValidator();
        if (Arrays.stream(roads).anyMatch(i-> !rangeValidator.isValidRange(i, 0, roads.length))){
            throw new Exception(ExceptionMessages.ROAD_PATH_OUT_OF_RANGE_ERR);
        }
    }

    private void validateNumberOfRoads(int numberOfRoads, int expected) throws Exception {
        IntegerRangeValidatorIfc rangeValidator = new IntegerRangeValidator();
        if (! rangeValidator.isValidRange(numberOfRoads, expected, expected)){
            throw new Exception(ExceptionMessages.NUM_OF_ROADS_RANGE_ERR);
        }
    }

    private void validateNumberOfCities(int numberOfCities) throws Exception {
        IntegerRangeValidatorIfc rangeValidator = new IntegerRangeValidator();
        if (! rangeValidator.isValidRange(numberOfCities, minNumOfCity, maxNumOfCity)){
            throw new Exception(ExceptionMessages.NUM_OF_CITIES_RANGE_ERR);
        }
    }
}
