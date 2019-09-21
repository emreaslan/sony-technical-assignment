package models;

import java.util.*;

public class Node {
    private int id;
    private Set<Node> neighbours;
    private Set<Integer> mergedNodes = new HashSet<>();

    public Node(int id){
        this.id = id;
        this.neighbours = new HashSet<>();
    }

    public void merge(Node node){
        this.mergedNodes.add(node.id);
        this.neighbours.remove(node.id);
        node.neighbours.stream()
                .filter(n-> n.id != this.id)
                .forEach(n->{
                    this.neighbours.add(n);
                    n.neighbours.add(this);
                });
        node.mergedNodes.forEach(n-> this.mergedNodes.add(n));
        node.clearAllNeighbours();
    }

    public int getDegree(){
        return neighbours.size();
    }

    public Set<Integer> getMergedNodes() {
        return mergedNodes;
    }

    public void clearAllNeighbours(){
        neighbours.stream().forEach(n -> n.neighbours.remove(this));
        neighbours.clear();
    }

    public void addNeighbour(Node neighbour){
        neighbours.add(neighbour);
    }

    public int getId() {
        return id;
    }

    public Set<Node> getNeighbours() {
        return neighbours;
    }

    @Override
    public String toString() {
        return "{id = " + id + ", merged = "
                + Arrays.toString(mergedNodes.stream().mapToInt(Integer::intValue).toArray())
                + ", neighbours = "
                + Arrays.toString(neighbours.stream().mapToInt(node -> node.getId()).toArray())
                + ", degree = "+ getDegree() + " }";
    }
}
