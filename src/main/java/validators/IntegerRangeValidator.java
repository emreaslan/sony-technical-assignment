package validators;

public class IntegerRangeValidator implements IntegerRangeValidatorIfc {
    @Override
    public Boolean isValidRange(Integer object, Integer min, Integer max) {
        return  (object >= min && object <= max);
    }
}
