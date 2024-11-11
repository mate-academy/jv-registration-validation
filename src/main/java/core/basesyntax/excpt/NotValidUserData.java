package core.basesyntax.excpt;

public class NotValidUserData extends RuntimeException {
    public NotValidUserData(String message) {
        super(message);
    }
}
