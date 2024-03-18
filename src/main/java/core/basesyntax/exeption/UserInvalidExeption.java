package core.basesyntax.exeption;

public class UserInvalidExeption extends RuntimeException {
    public UserInvalidExeption(String message) {
        super(message);
    }
}
