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
        //node.getNeighbours().stream().forEach(n->this.addNeighbour(n));
        for (Node n: node.neighbours) {
            neighbours.add(n);
        }
        //node.getMergedNodes().stream().forEach(i->this.mergedNodes.add(i));
        for(Integer i: node.mergedNodes){
            mergedNodes.add(i);
        }
        node.clearAllNeighbours();
        mergedNodes.add(node.getId());
        neighbours.remove(node);
    }

    public int getDegree(){
        return neighbours.size();
    }

    public Collection<Integer> getMergedNodes() {
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
