package core.basesyntax.exceptions;

public class WhiteSpacedPasswordException extends RuntimeException {
    public WhiteSpacedPasswordException(String message) {
        super(message);
    }
}
