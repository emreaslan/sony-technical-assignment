import models.Color;
import models.Node;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.System.in;

public class Application {

    public static void main(String[] args) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));

        try {
            int numberOfProblems = Integer.parseInt(bufferedReader.readLine());
            while (numberOfProblems > 0){
                int numberOfCities = Integer.parseInt(bufferedReader.readLine());
                Map<Integer, Node> citiesMap = new HashMap<>();
                for(int i=0; i<numberOfCities; ++i){
                    citiesMap.put(i, new Node(i));
                }

                int[] roads = Arrays.stream(bufferedReader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
                for(int i=0; i<numberOfCities-1; ++i){
                    citiesMap.get(i+1).addNeighbour(citiesMap.get(roads[i]));
                    citiesMap.get(roads[i]).addNeighbour(citiesMap.get(i+1));
                }

                citiesMap.forEach( (k,v) -> System.out.println(v));
                // solution
                // graph coloring with 2 colors
                // start with highest degree
                Comparator<Node> comparator = Comparator.comparing(Node::getDegree);
                Node nodeMaxDegree = citiesMap.values().stream().max(comparator).get();

                color(nodeMaxDegree, numberOfCities);

                citiesMap.forEach( (k,v) -> System.out.println(v));

                System.out.println( "Output: " + getResult(citiesMap));

                // end of solution
                --numberOfProblems;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int getResult(Map<Integer, Node> citiesMap) {
        int step = 0;
        Set<Node> toBeConnected = new HashSet<>();
        Map<Integer, Integer> mergeMap = new HashMap<>();

        System.out.println("First CitiesMap= " + citiesMap);

        while (citiesMap.keySet().size() > 1){
        //for(int tt=0; tt<4; ++tt){
            Map<Integer, Set<Integer>> degreeMap = citiesMap.values().stream()
                    .collect(Collectors.groupingBy(Node::getDegree,
                            Collectors.mapping(Node::getId, Collectors.toSet())));

            System.out.println("DegreeMap= " + degreeMap);
            int degreeCount = degreeMap.keySet().size();

            degreeMap.keySet().stream().filter(i->i>0)
                    .forEach(dc->{
                        degreeMap.get(dc).stream().forEach(i->{
                            citiesMap.get(i).getNeighbours().stream().forEach(m->{
                                if (!toBeConnected.contains(m) && !toBeConnected.contains(citiesMap.get(i))){
                                    toBeConnected.add(m);
                                    toBeConnected.add(citiesMap.get(i));
                                    mergeMap.put(m.getId(), i);
                                }
                            });
                        });
                    });

            System.out.println("MergeMap= " + mergeMap);
            mergeMap.forEach((k,v) -> {
                Node m = citiesMap.get(k);
                Node p = citiesMap.get(v);
                m.getMergedNodes().add(p.getId());
                m.getNeighbours().remove(p);
                p.getNeighbours().stream().filter(n -> n.getId() != m.getId()).forEach(n->{
                    m.addNeighbour(n);
                    n.addNeighbour(m);
                });
                p.getMergedNodes().stream().forEach(i->m.getMergedNodes().add(i));
                p.clearAllNeighbours();
            });
/*
            mergeMap.forEach((k,v) -> {
                Node m = citiesMap.get(k);
                Node p = citiesMap.get(v);
                //m.merge(p);

                p.getNeighbours().stream().forEach(n->m.addNeighbour(n));
                p.getMergedNodes().stream().forEach(i->m.getMergedNodes().add(i));
                p.clearAllNeighbours();
                m.getMergedNodes().add(p.getId());
                m.getNeighbours().remove(p);

            });

 */

            toBeConnected.stream()
                    .filter(node -> !mergeMap.containsKey(node.getId()))
                    .filter(node -> node.getNeighbours().isEmpty())
                    .forEach(node -> citiesMap.remove(node.getId()));
            mergeMap.clear();
            toBeConnected.clear();
            ++step;
            System.out.println("CitiesMap= " +  citiesMap);
        }
        return step;


    }

    private static void color(Node node, int numToBeColored) {
        Set<Node> visitedSet = new HashSet<>();
        Stack<Node> toBeColoredNodes = new Stack<>();
        Stack<Node> nodeStack = new Stack<>();

        Color color = Color.BLACK;
        node.setColor(color);
        visitedSet.add(node);
        node.getNeighbours().stream().forEach(n -> toBeColoredNodes.push(n));
        --numToBeColored;

        while (numToBeColored>0){
            color = changeColor(color);
            while(!toBeColoredNodes.isEmpty()){
                Node n = toBeColoredNodes.pop();
                if (!visitedSet.contains(n)){
                    n.setColor(color);
                    visitedSet.add(n);
                    --numToBeColored;
                    n.getNeighbours().stream().forEach(no -> nodeStack.push(no));
                }
            }
            nodeStack.stream().forEach(node1 -> toBeColoredNodes.push(node1));
            nodeStack.clear();
        }
    }

    private static Color changeColor(Color color) {
        if (color.equals(Color.BLACK)){
            return Color.WHITE;
        }
        return Color.BLACK;
    }
}
