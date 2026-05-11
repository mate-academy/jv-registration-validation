package core.basesyntax.service;

public class LoginValidator {
    public boolean isValid(String login) {
        if (login == null || login.length() < 6) {
            return false;
        } else {
            return true;
        }
    }
}
