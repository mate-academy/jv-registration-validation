package core.basesyntax.service;

public class PasswordLengthException extends Exception {
    private int minLength;

    public PasswordLengthException(int minLength) {
        this.minLength = minLength;
    }

    @Override
    public String toString() {
        return "Password should be at least " + minLength + " characters long.";
    }
}
