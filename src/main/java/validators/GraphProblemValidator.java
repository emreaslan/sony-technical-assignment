package validators;

import messages.ExceptionMessages;

import java.util.Arrays;

public class GraphProblemValidator implements GraphProblemValidatorIfc<Integer[]> {
    private void validateGraphIsAcyclic(Integer[] roads) throws Exception {
        AcyclicGraphValidatorIfc<Integer[]> validator = new AcyclicGraphValidator();

        if (!validator.isGraphAcyclic(roads)){
            throw new Exception(ExceptionMessages.GRAPH_IS_NOT_ACYCLIC);
        }
    }

    private void validateRoadPaths(Integer[] roads) throws Exception {
        IntegerRangeValidatorIfc rangeValidator = new IntegerRangeValidator();
        if (Arrays.stream(roads).anyMatch(i-> !rangeValidator.isValidRange(i, 0, roads.length))){
            throw new Exception(ExceptionMessages.ROAD_PATH_OUT_OF_RANGE_ERR);
        }
    }

    @Override
    public void isValidGraphProblem(Integer[] roads) throws Exception{
        validateRoadPaths(roads);
        validateGraphIsAcyclic(roads);
    }
}
