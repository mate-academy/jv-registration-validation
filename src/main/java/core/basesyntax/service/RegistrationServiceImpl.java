package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MIN_LENGTH = 6;
    public static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User can't be null");
        }
        validateLogin(user.getLogin());
        validatePassword(user.getPassword());
        validateAge(user.getAge());
        return storageDao.add(user);
    }

    private void validateAge(Integer age) {
        if (age == null) {
            throw new RegistrationException("age must be here");
        }
        if (age < MIN_AGE) {
            throw new RegistrationException(
                    "age should be at least %d, but was: %d"
                            .formatted(MIN_AGE, age));
        }
    }

    private void validatePassword(String password) {
        if (password == null) {
            throw new RegistrationException("password must be here");
        }
        if (password.length() < MIN_LENGTH) {
            throw new RegistrationException(
                    "password expected longer than: %d, but was: %d"
                            .formatted(MIN_LENGTH, password.length()));
        }
        if (!password.trim().equals(password)) {
            throw new RegistrationException(
                    "password should be without whitespaces arround");
        }
    }

    private void validateLogin(String login) {
        if (login == null) {
            throw new RegistrationException("login must be here");
        }
        if (login.length() < MIN_LENGTH) {
            throw new RegistrationException(
                    "login expected longer than: %d, but was: %d"
                            .formatted(MIN_LENGTH, login.length()));
        }
        if (!login.trim().equals(login)) {
            throw new RegistrationException("login should be without whitespaces arround");
        }
        if (storageDao.get(login) != null) {
            throw new RegistrationException(
                    "user with such login already exists: %s"
                            .formatted(login));
        }
    }
}
