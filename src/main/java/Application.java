import models.Node;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.System.in;

public class Application {

    public static List getInput() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        List<Map<Integer, Node>> problems = new ArrayList<>();

        try {
            int numberOfProblems = Integer.parseInt(bufferedReader.readLine());
            while (numberOfProblems > 0) {

                int numberOfCities = Integer.parseInt(bufferedReader.readLine());
                int[] roads = Arrays.stream(bufferedReader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

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

                //solution
                problems.add(citiesMap);
                //System.out.println( "Output: " + getResult(numberOfCities, citiesMap));

                // end of solution
                --numberOfProblems;
            }
        } catch (IOException e) {
            e.printStackTrace();
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
        solveProblems(getInput());
    }

    private static int getResult(Map<Integer, Node> citiesMap) throws Exception {
        if (citiesMap.isEmpty()) {
            throw new Exception("Input Missmatch Error!");
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
