package models.graph;

import utils.ExceptionMessages;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GraphProblemGenerator {
    InputStream inputStream;
    Solver solver;

    public GraphProblemGenerator(InputStream inputStream, Solver solver) {
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
                int[] roads = Arrays.stream(bufferedReader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

                problems.add(new GraphProblem.Builder(roads).withNumberOfCity(numberOfCities).withSolver(solver).build());

                --numberOfProblems;
            }
        } catch (IOException e){
            System.out.println(ExceptionMessages.INPUT_ERROR_MESSAGE);
        }
        return problems;
    }
}
