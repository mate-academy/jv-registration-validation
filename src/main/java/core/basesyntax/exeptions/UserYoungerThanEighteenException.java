package core.basesyntax.exeptions;

public class UserYoungerThanEighteenException extends RuntimeException {
    public UserYoungerThanEighteenException(String message) {
        super(message);
    }
}
