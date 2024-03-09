package core.basesyntax.exception;

public class PasswordValidateException extends RuntimeException {
    public PasswordValidateException(String massage) {
        super(massage);
    }
}
