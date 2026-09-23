package core.basesyntax.exeptions;

public class AgeValidationExeption extends RuntimeException {
    public AgeValidationExeption(String message) {
        super(message);
    }
}
