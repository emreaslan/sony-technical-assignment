package models.graph;

import models.Node;

import java.util.HashMap;
import java.util.Map;

public class GraphProblem {
    private Map<Integer, Node> citiesMap;

    private GraphProblem(Map<Integer, Node> citiesMap){
        this.citiesMap = citiesMap;
    }

    public Map<Integer, Node> getCitiesMap() {
        return citiesMap;
    }

    public static class Builder {
        private int numberOfCities;
        private int roads[];

        public Builder(int roads[]){
            this.roads = roads;
        }

        public Builder withNumberOfCity(int numberOfCities){
            this.numberOfCities = numberOfCities;
            return this;
        }

        private Map<Integer, Node> buildMap(){
            Map<Integer, Node> citiesMap = new HashMap<>();
            if (numberOfCities == 0){
                numberOfCities = roads.length + 1;
            }
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

        public GraphProblem build(){
            return new GraphProblem(buildMap());
        }
    }
}
