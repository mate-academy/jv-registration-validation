package core.basesyntax.exeptions;

public class LoginShorterThanSixException extends RuntimeException {
    public LoginShorterThanSixException(String message) {
        super(message);
    }
}
