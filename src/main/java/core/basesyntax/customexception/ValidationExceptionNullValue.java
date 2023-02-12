package core.basesyntax.customexception;

public class ValidationExceptionNullValue extends RuntimeException {
    public ValidationExceptionNullValue(String errorMessage) {
        super(errorMessage);
    }
}
