package core.basesyntax.service.validator;

public class AgeValidatorImpl implements AgeValidator {
    private static final int MIN_AGE = 18;

    @Override
    public boolean isValid(Integer age) {
        return age != null
                && age >= MIN_AGE;
    }
}
