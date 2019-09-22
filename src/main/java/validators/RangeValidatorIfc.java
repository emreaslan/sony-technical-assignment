package validators;

public interface RangeValidatorIfc<T, M> {
    Boolean isValidRange(T object, T min, T max);
    Boolean isValidRange(T object, T min, T max, M exceptionMessage) throws Exception;
}
