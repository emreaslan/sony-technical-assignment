import models.Node;
import utils.Messages;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;


public class Application {

    private static Map<Integer, Node> buildQuestion(int numberOfCities, int [] roads){
        Map<Integer, Node> citiesMap = new HashMap<>();
        if (numberOfCities == roads.length + 1) {
            for (int i = 0; i < numberOfCities; ++i) {
                citiesMap.put(i, new Node(i));
            }

            for (int i = 0; i < numberOfCities - 1; ++i) {
                citiesMap.get(i + 1).addNeighbour(citiesMap.get(roads[i]));
                citiesMap.get(roads[i]).addNeighbour(citiesMap.get(i + 1));
            }
        }
        return citiesMap;
    }

    public static List getInput(InputStream inputStream) {
        List<Map<Integer, Node>> problems = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream)))
        {
            int numberOfProblems = Integer.parseInt(bufferedReader.readLine());
            while (numberOfProblems > 0) {

                int numberOfCities = Integer.parseInt(bufferedReader.readLine());
                int[] roads = Arrays.stream(bufferedReader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

                problems.add(buildQuestion(numberOfCities, roads));

                --numberOfProblems;
            }
        } catch (IOException e){
            System.out.println(Messages.INPUT_ERROR_MESSAGE);
        }
        return problems;
    }

    public static void solveProblems(List<Map<Integer, Node>> problemList) {
        problemList.forEach(problem -> {
            try {
                System.out.println("Output: " + getResult(problem));
            } catch (Exception e) {
                System.out.println("Output: " + e.getMessage());
            }
        });
    }

    public static void main(String[] args) {
        solveProblems(getInput(System.in));
    }

    private static int getResult(Map<Integer, Node> citiesMap) throws Exception {
        if (citiesMap.isEmpty()) {
            throw new Exception(Messages.INPUT_ERROR_MESSAGE);
        }
        int step = 0;
        Set<Node> toBeConnected = new HashSet<>();
        Map<Integer, Integer> mergeMap = new HashMap<>();

        //System.out.println("First CitiesMap= " + citiesMap);

        while (citiesMap.keySet().size() > 1) {
            Map<Integer, Set<Integer>> degreeMap = citiesMap.values().stream()
                    .collect(Collectors.groupingBy(Node::getDegree,
                            Collectors.mapping(Node::getId, Collectors.toSet())));

            //System.out.println("DegreeMap= " + degreeMap);

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

            //System.out.println("MergeMap= " + mergeMap);
            mergeMap.forEach((k, v) -> {
                Node m = citiesMap.get(k);
                Node p = citiesMap.get(v);
                m.getMergedNodes().add(p.getId());
                m.getNeighbours().remove(p);
                p.getNeighbours().stream().filter(n -> n.getId() != m.getId()).forEach(n -> {
                    m.addNeighbour(n);
                    n.addNeighbour(m);
                });
                p.getMergedNodes().forEach(i -> m.getMergedNodes().add(i));
                p.clearAllNeighbours();
            });

            toBeConnected.stream()
                    .filter(node -> !mergeMap.containsKey(node.getId()))
                    .filter(node -> node.getNeighbours().isEmpty())
                    .forEach(node -> citiesMap.remove(node.getId()));
            mergeMap.clear();
            toBeConnected.clear();
            ++step;
            //System.out.println("CitiesMap= " + citiesMap);
        }
        return step;
    }
}
