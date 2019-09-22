package validators;

public class IntegerRangeValidator implements IntegerRangeValidatorIfc {

    @Override
    public Boolean isValidRange(Integer object, Integer min, Integer max) {
        return (object >= min && object <= max);
    }

    @Override
    public Boolean isValidRange(Integer object, Integer min, Integer max, String exceptionMessage) throws Exception {
        if (object >= min && object <= max)
            return true;
        throw new Exception(exceptionMessage);
    }
}
