package core.basesyntax.exeptions;

public class NullUserValidationExeption extends RuntimeException {
    public NullUserValidationExeption(String message) {
        super(message);
    }
}
