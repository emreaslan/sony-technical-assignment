import models.GraphProblem;
import models.Node;
import utils.Messages;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;


public class Application {

    public static List<GraphProblem> getInput(InputStream inputStream) {
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

    public static void solveQuestions(List<GraphProblem> problemList) {
        problemList.forEach(problem -> {
            try {
                System.out.println("Output: " + solveQuestion(problem));
            } catch (Exception e) {
                System.out.println("Output: " + e.getMessage());
            }
        });
    }

    public static void main(String[] args) {
        solveQuestions(getInput(System.in));
    }

    private static int solveQuestion(GraphProblem graphProblem) throws Exception {
        Map<Integer, Node> citiesMap = graphProblem.getCitiesMap();
        if (citiesMap.isEmpty()) {
            throw new Exception(Messages.INPUT_ERROR_MESSAGE);
        }
        int step = 0;
        Set<Node> toBeConnected = new HashSet<>();
        Map<Integer, Integer> mergeMap = new HashMap<>();

        while (citiesMap.keySet().size() > 1) {
            Map<Integer, Set<Integer>> degreeMap = citiesMap.values().stream()
                    .collect(Collectors.groupingBy(Node::getDegree,
                            Collectors.mapping(Node::getId, Collectors.toSet())));

            degreeMap.keySet().stream().filter(i -> i > 0)
                    .forEach(dc -> {
                        degreeMap.get(dc).stream().forEach(i -> {
                            citiesMap.get(i).getNeighbours().stream().forEach(m -> {
                                if (!toBeConnected.contains(m) && !toBeConnected.contains(citiesMap.get(i))) {
                                    toBeConnected.add(m);
                                    toBeConnected.add(citiesMap.get(i));
                                    mergeMap.put(m.getId(), i);
                                }
                            });
                        });
                    });

            mergeMap.forEach((k, v) -> {
               citiesMap.get(k).merge(citiesMap.get(v));
            });

            toBeConnected.stream()
                    .filter(node -> !mergeMap.containsKey(node.getId()))
                    .filter(node -> node.getNeighbours().isEmpty())
                    .forEach(node -> citiesMap.remove(node.getId()));
            mergeMap.clear();
            toBeConnected.clear();
            ++step;
        }
        return step;
    }
}
