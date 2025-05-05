package core.basesyntax.service.validator;

public class PasswordValidatorImpl implements PasswordValidator {
    private static final int MIN_LENGTH = 6;

    @Override
    public boolean isValid(String password) {
        return password != null
                && password.length() >= MIN_LENGTH;
    }
}
