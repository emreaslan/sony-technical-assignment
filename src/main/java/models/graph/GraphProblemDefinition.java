package models.graph;

import messages.ExceptionMessages;

public class GraphProblemDefinition implements GraphProblemDefinitionIfc {
    private final int minNumOfCity;
    private final int maxNumOfCity;
    private final int minNumOfProblems;
    private final int maxNumOfProblems;

    public GraphProblemDefinition(int minNumOfCity, int maxNumOfCity, int minNumOfProblems, int maxNumOfProblems) throws Exception {
        if (minNumOfCity > maxNumOfCity || maxNumOfProblems<1)
            throw new Exception(ExceptionMessages.GRAPH_PROBLEM_DEFINITION_ERR);
        this.minNumOfCity = minNumOfCity;
        this.maxNumOfCity = maxNumOfCity;
        this.minNumOfProblems = minNumOfProblems;
        this.maxNumOfProblems = maxNumOfProblems;
    }

    @Override
    public int getMinNumOfCity() {
        return minNumOfCity;
    }

    @Override
    public int getMaxNumOfCity() {
        return maxNumOfCity;
    }

    @Override
    public int minNumOfProblems() {
        return minNumOfProblems;
    }

    @Override
    public int maxNumOfProblems() {
        return maxNumOfProblems;
    }
}
