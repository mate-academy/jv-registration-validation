package core.basesyntax.service;

import core.basesyntax.exception.RegistrationException;
import core.basesyntax.exception.RegistrationExceptionMessage;
import core.basesyntax.service.interfaces.PasswordValidator;

public class PasswordValidatorIml implements PasswordValidator {
    private static final String PATTERN = "^[a-zA-Z0-9]{6,}$";

    @Override
    public void isValid(String password) {
        if (password == null) {
            throw new RegistrationException(RegistrationExceptionMessage.PASSWORD_IS_NOT_NULL);
        }
        if (!password.matches(PATTERN)) {
            throw new RegistrationException(RegistrationExceptionMessage.WRONG_PASSWORD_MESSAGE);
        }
    }
}
