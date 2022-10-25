package core.basesyntax.exseption;

public class LoginIsTakenException extends RuntimeException {
    public LoginIsTakenException(String massage) {
        super(massage);
    }
}
