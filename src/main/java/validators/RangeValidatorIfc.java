package validators;

public interface RangeValidatorIfc<T, R> {
    R isValidRange(T object, T min, T max);
}
