package core.basesyntax.service;

public class ValidationService {
    private static final String PATTERN = "^[a-zA-Z0-9]{6,}$";
    private static final int AGE_LIMIT = 18;

    public boolean isValidLogin(String login) {
        return matchesPattern(login);
    }

    public boolean isValidPassword(String password) {
        return matchesPattern(password);
    }

    public boolean isValidAge(Integer age) {
        return age != null && age >= AGE_LIMIT;
    }

    private boolean matchesPattern(String input) {
        return input != null && input.matches(PATTERN);
    }
}
