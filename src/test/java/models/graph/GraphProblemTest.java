package models.graph;

import messages.ExceptionMessages;
import models.Node;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class GraphProblemTest {
    private Node[] nodes;
    private GraphProblem graphProblem;

    private void prepareGraphProblem(GraphProblemSolverIfc solver){
        nodes = new Node[4];
        for (int i = 0; i<nodes.length; ++i){
            nodes[i] = new Node(i);
        }
        for (int i = 0; i<nodes.length-1; ++i){
            nodes[i+1].addNeighbour(nodes[i]);
            nodes[i].addNeighbour(nodes[i+1]);
        }
        Integer[] roads = Stream.of(0, 1, 2).mapToInt(Integer::intValue).boxed().toArray(Integer[]::new);
        graphProblem = new GraphProblem.Builder(roads)
                .withNumberOfCity(nodes.length)
                .withSolver(solver).build();
    }

    @Test
    void getCitiesMap() {
        prepareGraphProblem(new GraphProblemSolver());

        Map<Integer, Node> actualCitiesMap = graphProblem.getCitiesMap();
        Map<Integer, Node> expectedCitiesMap = Arrays.stream(nodes).collect(Collectors.toMap(n->n.getId(), n->n));

        assertEquals(expectedCitiesMap, actualCitiesMap);
    }

    @Test
    void solve() {
        prepareGraphProblem(new GraphProblemSolver());
        try {
            assertEquals(2, graphProblem.solve());
        } catch (Exception e) {
            fail("Exception thrown!");
        }
    }

    @Test
    void unSolvable(){
        prepareGraphProblem(null);
        try {
            assertEquals(2, graphProblem.solve());
        } catch (Exception e) {
            assertEquals(ExceptionMessages.NO_SOLVER_DEFINED, e.getMessage());
        }
    }
}