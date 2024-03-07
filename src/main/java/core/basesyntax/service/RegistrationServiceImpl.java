package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final String NULL_MESSAGE = "%s can`t be null";
    public static final String LESS_MESSAGE = "%s less than minimum: expected %d but was %d!";
    public static final String EXIST_MESSAGE = "A user with this email already exists!";
    public static final String LOGIN = "Login";
    public static final String PASSWORD = "Password";
    public static final String AGE = "Age";
    public static final int LOGIN_PASSWORD_MIN_LENGTH = 6;
    public static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User can`t be null!");
        }
        validateLogin(user);
        validatePassword(user);
        validateAge(user);
        storageDao.add(user);
        return user;
    }

    private void validateLogin(User user) {
        String login = user.getLogin();
        if (login == null) {
            throw new RegistrationException(String.format(NULL_MESSAGE, LOGIN));
        }
        if (login.length() < LOGIN_PASSWORD_MIN_LENGTH) {
            throw new RegistrationException(String.format(
                    LESS_MESSAGE,
                    LOGIN,
                    LOGIN_PASSWORD_MIN_LENGTH,
                    user.getLogin().length()));
        }
        if (storageDao.get(login) != null) {
            throw new RegistrationException(EXIST_MESSAGE);
        }
    }

    private void validatePassword(User user) {
        if (user.getPassword() == null) {
            throw new RegistrationException(String.format(NULL_MESSAGE, PASSWORD));
        }
        if (user.getPassword().length() < LOGIN_PASSWORD_MIN_LENGTH) {
            throw new RegistrationException(String.format(
                    LESS_MESSAGE,
                    PASSWORD,
                    LOGIN_PASSWORD_MIN_LENGTH,
                    user.getPassword().length()));
        }
    }

    private void validateAge(User user) {
        if (user.getAge() == null) {
            throw new RegistrationException(String.format(NULL_MESSAGE, AGE));
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException(
                    String.format(LESS_MESSAGE, AGE, MIN_AGE, user.getAge()));
        }
    }

    public static class RegistrationException extends RuntimeException {
        public RegistrationException(String message) {
            super(message);
        }
    }
}
