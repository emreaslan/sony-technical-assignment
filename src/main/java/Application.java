import models.Color;
import models.Node;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

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
                    citiesMap.get(i+1).addNeighbour(roads[i]);
                    citiesMap.get(roads[i]).addNeighbour(i+1);
                }

                citiesMap.forEach( (k,v) -> System.out.println(v));
                // solution
                // graph coloring with 2 colors
                // start with highest degree
                Comparator<Node> comparator = Comparator.comparing(Node::getDegree);
                Node node = citiesMap.values().stream().max(comparator).get();

                node.setColor(Color.BLACK);
                node.getNeighbours().stream().forEach( id -> citiesMap.get(id).setColor(Color.WHITE));

                citiesMap.forEach( (k,v) -> System.out.println(v));

                // end of solution
                --numberOfProblems;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
