package core.basesyntax.service;

import core.basesyntax.service.interfaces.PasswordValidator;

public class PasswordValidatorImpl implements PasswordValidator {
    private static final String PATTERN = "^[a-zA-Z0-9]{6,}$";

    @Override
    public boolean isValid(String password) {
        return password != null && password.matches(PATTERN);
    }
}
