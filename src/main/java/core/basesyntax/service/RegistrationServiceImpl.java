package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.InvalidRegistrationData;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int LOGIN_MIN_LENGTH = 6;
    private static final int PASSWORD_MIN_LENGTH = 6;
    private static final int USER_MIN_AGE = 18;
    private static final String USER = "User";
    private static final String LOGIN = "Login";
    private static final String PASSWORD = "Password";
    private static final String AGE = "Age";
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkIfValueNotNull(user, USER);
        String login = user.getLogin();
        checkIfValueNotNull(login, LOGIN);
        String password = user.getPassword();
        checkIfValueNotNull(password, PASSWORD);
        Integer age = user.getAge();
        checkIfValueNotNull(age, AGE);
        validateUserData(login, password, age);
        User existingUser = storageDao.get(login);
        if (existingUser != null) {
            throw new InvalidRegistrationData(
                    "User with such login already exists. Login: " + login);
        }
        return storageDao.add(user);
    }

    private <T> void checkIfValueNotNull(T value, String name) {
        if (value == null) {
            throw new InvalidRegistrationData(name
                    + " cannot be null");
        }
    }

    private void validateUserData(String login, String password, Integer age) {
        if (login.length() < LOGIN_MIN_LENGTH) {
            throw new InvalidRegistrationData(
                    "Login must be at least " + LOGIN_MIN_LENGTH
                            + " characters long. Login: " + login);
        }
        if (password.length() < PASSWORD_MIN_LENGTH) {
            throw new InvalidRegistrationData(
                    "Password must be at least " + PASSWORD_MIN_LENGTH
                            + " characters long. Password: " + password);
        }
        if (age < USER_MIN_AGE) {
            throw new InvalidRegistrationData(
                    "Age must be at least " + USER_MIN_AGE
                            + "Age: " + age);
        }
    }
}
