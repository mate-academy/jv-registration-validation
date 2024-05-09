package core.basesyntax.service;

import core.basesyntax.exception.RegistrationException;
import core.basesyntax.exception.RegistrationExceptionMessage;
import core.basesyntax.service.interfaces.LoginValidator;

public class LoginValidatorIml implements LoginValidator {
    private static final String PATTERN = "^[a-zA-Z0-9]{6,}$";

    @Override
    public void isValid(String login) {
        if (login == null) {
            throw new RegistrationException(RegistrationExceptionMessage.LOGIN_IS_NOT_NULL);
        }
        if (!login.matches(PATTERN)) {
            throw new RegistrationException(RegistrationExceptionMessage.WRONG_LOGIN_MESSAGE);
        }
    }
}
