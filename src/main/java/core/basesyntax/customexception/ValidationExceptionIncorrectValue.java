package core.basesyntax.customexception;

public class ValidationExceptionIncorrectValue extends RuntimeException {
    public ValidationExceptionIncorrectValue(String errorMessage) {
        super(errorMessage);
    }
}
