package core.basesyntax.exeptions;

public class PasswordShorterThanSixException extends RuntimeException {
    public PasswordShorterThanSixException(String message) {
        super(message);
    }
}
