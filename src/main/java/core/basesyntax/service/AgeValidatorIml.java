package core.basesyntax.service;

import core.basesyntax.exception.RegistrationException;
import core.basesyntax.exception.RegistrationExceptionMessage;
import core.basesyntax.service.interfaces.AgeValidator;

public class AgeValidatorIml implements AgeValidator {
    private static final int AGE_LIMIT = 18;

    @Override
    public void isValid(Integer age) {
        if (age == null) {
            throw new RegistrationException(RegistrationExceptionMessage.AGE_IS_NOT_NULL);
        }
        if (age < AGE_LIMIT) {
            throw new RegistrationException(RegistrationExceptionMessage.WRONG_AGE_MESSAGE);
        }
    }
}
