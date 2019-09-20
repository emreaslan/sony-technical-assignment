package models.graph;

import utils.Messages;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GraphProblemGenerator {
    public static List<GraphProblem> generateProblems(InputStream inputStream) {
        List<GraphProblem> problems = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream)))
        {
            int numberOfProblems = Integer.parseInt(bufferedReader.readLine());
            while (numberOfProblems > 0) {

                int numberOfCities = Integer.parseInt(bufferedReader.readLine());
                int[] roads = Arrays.stream(bufferedReader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

                problems.add(new GraphProblem.Builder(roads).withNumberOfCity(numberOfCities).build());

                --numberOfProblems;
            }
        } catch (IOException e){
            System.out.println(Messages.INPUT_ERROR_MESSAGE);
        }
        return problems;
    }
}
