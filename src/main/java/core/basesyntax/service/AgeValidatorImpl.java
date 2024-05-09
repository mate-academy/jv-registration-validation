package core.basesyntax.service;

import core.basesyntax.service.interfaces.AgeValidator;

public class AgeValidatorImpl implements AgeValidator {
    private static final int AGE_LIMIT = 18;

    @Override
    public boolean isValid(Integer age) {
        return age != null && age >= AGE_LIMIT;
    }
}
