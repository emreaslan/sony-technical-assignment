package models.graph;

import models.Node;
import messages.ExceptionMessages;

import java.util.HashMap;
import java.util.Map;

public class GraphProblem {
    private Map<Integer, Node> citiesMap;
    private GraphProblemSolverIfc solver;
    private GraphProblem(Map<Integer, Node> citiesMap, GraphProblemSolverIfc solver){
        this.citiesMap = citiesMap;
        this.solver = solver;
    }

    public Map<Integer, Node> getCitiesMap() {
        return citiesMap;
    }

    public Integer solve() throws Exception {
        if (solver != null){
            return solver.solve(this);
        }
        throw new Exception(ExceptionMessages.NO_SOLVER_DEFINED);
    }

    public static class Builder {
        private int numberOfCities;
        private int roads[];
        private GraphProblemSolverIfc solver;

        public Builder(int roads[]){
            this.roads = roads;
        }

        public Builder withNumberOfCity(int numberOfCities){
            this.numberOfCities = numberOfCities;
            return this;
        }

        public Builder withSolver(GraphProblemSolverIfc solver){
            this.solver = solver;
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
            return new GraphProblem(buildMap(), solver);
        }
    }
}
