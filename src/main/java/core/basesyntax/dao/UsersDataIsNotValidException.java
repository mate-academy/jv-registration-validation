package core.basesyntax.dao;

public class UsersDataIsNotValidException extends RuntimeException {
    public UsersDataIsNotValidException(String massage) {
        super(massage);
    }
}
