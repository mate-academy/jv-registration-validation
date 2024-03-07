package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final String NULL_MSG = "%s can`t be null";
    public static final String LESS_MSG = "%s less than minimum: expected %d but was %d!";
    public static final String EXIST_MSG = "A user with this email already exists!";
    public static final String LOGIN = "Login";
    public static final String PASSWORD = "Password";
    public static final String AGE = "Age";
    public static final int USER_DATA_MIN_LENGTH = 6;
    public static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User can`t be null!");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException(String.format(NULL_MSG, LOGIN));
        }
        if (user.getPassword() == null) {
            throw new RegistrationException(String.format(NULL_MSG, PASSWORD));
        }
        if (user.getAge() == null) {
            throw new RegistrationException(String.format(NULL_MSG, AGE));
        }
        if (user.getLogin().length() < USER_DATA_MIN_LENGTH) {
            throw new RegistrationException(String.format(
                    LESS_MSG,
                    LOGIN,
                    USER_DATA_MIN_LENGTH,
                    user.getLogin().length()));
        }
        if (user.getPassword().length() < USER_DATA_MIN_LENGTH) {
            throw new RegistrationException(String.format(
                    LESS_MSG,
                    PASSWORD,
                    USER_DATA_MIN_LENGTH,
                    user.getPassword().length()));
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException(String.format(LESS_MSG, AGE, MIN_AGE, user.getAge()));
        }
        String currentUserLogin = user.getLogin();
        if (storageDao.get(currentUserLogin) != null) {
            throw new RegistrationException(EXIST_MSG);
        }
        storageDao.add(user);
        return user;
    }

    public static class RegistrationException extends RuntimeException {
        public RegistrationException(String message) {
            super(message);
        }
    }
}
