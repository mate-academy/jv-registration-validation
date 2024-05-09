package core.basesyntax.service;

import core.basesyntax.service.interfaces.LoginValidator;

public class LoginValidatorImpl implements LoginValidator {
    private static final String PATTERN = "^[a-zA-Z0-9]{6,}$";

    @Override
    public boolean isValid(String login) {
        return login != null && login.matches(PATTERN);
    }
}
