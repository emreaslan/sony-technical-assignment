package models.graph;

public interface SolverIfc<P, R> {
    R solve(P problem) throws Exception;
}
