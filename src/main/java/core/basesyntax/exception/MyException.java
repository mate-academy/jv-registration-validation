package core.basesyntax.exception;

public class MyException extends RuntimeException {
    public MyException(String message) {
        super("Invalid data!");
    }
}
