package core.basesyntax.exeptions;

public class IncorrectDataException extends RuntimeException {
    public IncorrectDataException(String errorMessage) {
        super(errorMessage);
    }
}
