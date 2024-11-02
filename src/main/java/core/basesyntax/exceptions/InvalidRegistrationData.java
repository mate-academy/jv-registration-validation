package core.basesyntax.exceptions;

public class InvalidRegistrationData extends RuntimeException {
    public InvalidRegistrationData() {
    }

    public InvalidRegistrationData(String message) {
        super(message);
    }
}
