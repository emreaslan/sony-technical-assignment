package validators;

public interface GraphProblemValidatorIfc<T> {
    void isValidGraphProblem(T roads) throws Exception;
}
