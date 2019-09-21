package models.graph;

public interface Solver<P, R> {
    R solve(P problem) throws Exception;
}
