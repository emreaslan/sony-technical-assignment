import models.Color;
import models.Node;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

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
                Node nodeMinDegree = citiesMap.values().stream().min(comparator).get();

                color(nodeMaxDegree, numberOfCities);

                citiesMap.forEach( (k,v) -> System.out.println(v));

                // end of solution
                --numberOfProblems;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
