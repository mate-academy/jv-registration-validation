package core.basesyntax.userinvalidexeption;


public class UserInvalidExeption extends RuntimeException {
    public UserInvalidExeption(String message) {
        super(message);
    }
}
