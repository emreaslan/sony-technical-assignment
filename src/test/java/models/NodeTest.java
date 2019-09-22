package models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class NodeTest {
    Node nodeZero = new Node(0);
    Node nodeOne = new Node(1);
    Node nodeTwo = new Node(2);
    Node nodeThree = new Node(3);

    @BeforeEach
    void setUp() {
        prepareGraphOne();
    }

    @AfterEach
    void tearDown() {
    }

    void prepareGraphOne(){
        nodeZero.addNeighbour(nodeOne);
        nodeOne.addNeighbour(nodeZero);
        nodeOne.addNeighbour(nodeTwo);
        nodeTwo.addNeighbour(nodeOne);
        nodeTwo.addNeighbour(nodeThree);
        nodeThree.addNeighbour(nodeTwo);
    }

    @Test
    void mergeOneAndZero() {
        assertEquals(1, nodeZero.getDegree());
        assertEquals(2, nodeOne.getDegree());

        nodeOne.merge(nodeZero);

        assertEquals(0, nodeZero.getDegree());
        assertEquals(1, nodeOne.getDegree());

        Set<Integer> expectedMergedNodes = Stream.of(0).collect(Collectors.toSet());
        Set<Integer> actualMergedNodes = nodeOne.getMergedNodes();

        assertEquals(expectedMergedNodes, actualMergedNodes);
    }

    @Test
    void mergeTwo() {
        assertEquals(2, nodeTwo.getDegree());

        nodeOne.merge(nodeZero);
        assertEquals(2, nodeTwo.getDegree());

        nodeTwo.merge(nodeOne);
        assertEquals(1, nodeTwo.getDegree());

        assertEquals(0, nodeZero.getDegree());
        assertEquals(0, nodeOne.getDegree());

        Set<Integer> expectedMergedNodes = Stream.of(0, 1).collect(Collectors.toSet());
        Set<Integer> actualMergedNodes = nodeTwo.getMergedNodes();

        assertEquals(expectedMergedNodes, actualMergedNodes);
    }

    @Test
    void mergeThree(){
        assertEquals(1, nodeThree.getDegree());

        nodeOne.merge(nodeZero);
        nodeTwo.merge(nodeOne);

        nodeThree.merge(nodeTwo);
        assertEquals(0, nodeThree.getDegree());

        Set<Integer> expectedMergedNodes = Stream.of(0, 1, 2).collect(Collectors.toSet());
        Set<Integer> actualMergedNodes = nodeThree.getMergedNodes();

        assertEquals(expectedMergedNodes, actualMergedNodes);
    }

    @Test
    void getDegree() {
        Node node = new Node(0);
        assertEquals(node.getDegree(), 0);

        for(int i=1; i<600; ++i){
            node.addNeighbour(new Node(i));
            assertEquals(i, node.getDegree());
        }
    }

    @Test
    void getMergedNodes() {
        nodeOne.merge(nodeZero);
        nodeTwo.merge(nodeOne);
        nodeThree.merge(nodeTwo);
        Set<Integer> expectedMergedNodes = Stream.of(0, 1, 2).collect(Collectors.toSet());
        Set<Integer> actualMergedNodes = nodeThree.getMergedNodes();
        assertEquals(expectedMergedNodes, actualMergedNodes);
    }

    @Test
    void clearAllNeighbours() {
        Set<Node> nodeZeroExpectedNeighbours = Stream.of(nodeOne).collect(Collectors.toSet());
        assertEquals(nodeZeroExpectedNeighbours, nodeZero.getNeighbours());

        Set<Node> nodeOneExpectedNeighbours = Stream.of(nodeZero, nodeTwo).collect(Collectors.toSet());
        assertEquals(nodeOneExpectedNeighbours, nodeOne.getNeighbours());

        nodeZero.clearAllNeighbours();

        nodeZeroExpectedNeighbours = new HashSet<>();
        assertEquals(nodeZeroExpectedNeighbours, nodeZero.getNeighbours());

        nodeOneExpectedNeighbours = Stream.of(nodeTwo).collect(Collectors.toSet());
        assertEquals(nodeOneExpectedNeighbours, nodeOne.getNeighbours());
    }

    @Test
    void addAndGetNeighbour() {
        Set<Node> nodeZeroExpectedNeighbours = Stream.of(nodeOne).collect(Collectors.toSet());
        assertEquals(nodeZeroExpectedNeighbours, nodeZero.getNeighbours());

        Set<Node> nodeOneExpectedNeighbours = Stream.of(nodeZero, nodeTwo).collect(Collectors.toSet());
        assertEquals(nodeOneExpectedNeighbours, nodeOne.getNeighbours());
    }

    @Test
    void getId() {
        assertEquals(0, nodeZero.getId());
        assertEquals(1, nodeOne.getId());
        assertEquals(2, nodeTwo.getId());
        assertEquals(3, nodeThree.getId());
    }
}