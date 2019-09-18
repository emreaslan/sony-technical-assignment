package models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class Node {
    private int id;
    private Collection<Node> neighbours;
    private Color color;

    public Node(int id){
        this.id = id;
        this.neighbours = new ArrayList<>();
        this.color = Color.NONE;
    }

    public Node(int id, Collection<Node> neighbours, Color color) {
        this.id = id;
        this.neighbours = neighbours;
        this.color = color;
    }

    public int getDegree(){
        return neighbours.size();
    }

    public void addNeighbour(Node neighbour){
        neighbours.add(neighbour);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Collection<Node> getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(Collection<Node> neighbours) {
        this.neighbours = neighbours;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "{id = " + id + ", neighbours = "
                + Arrays.toString(neighbours.stream().mapToInt(node -> node.getId()).toArray())
                + ", color = " + color.name() + ", degree = "+ getDegree() + " }";
    }
}
