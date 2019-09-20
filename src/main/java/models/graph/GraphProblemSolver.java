package models.graph;

import models.Node;
import utils.Messages;

import java.util.*;
import java.util.stream.Collectors;

public class GraphProblemSolver {
    private GraphProblem graphProblem;

    public GraphProblemSolver(GraphProblem graphProblem) {
        this.graphProblem = graphProblem;
    }

    public int solve() throws Exception {
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

    public static void solveProblems(List<GraphProblem> problemList) {
        problemList.forEach(problem -> {
            try {
                System.out.println("Output: " + new GraphProblemSolver(problem).solve());
            } catch (Exception e) {
                System.out.println("Output: " + e.getMessage());
            }
        });
    }
}
