package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("The user doesn't exist");
        }

        validateLogin(user);
        validatePassword(user);
        validateAge(user);

        return this.storageDao.add(user);
    }

    private void validateLogin(User user) {
        String login = user.getLogin();

        if (storageDao.get(login) != null) {
            throw new RegistrationException("User with login %s already exists".formatted(login));
        }

        if (login == null) {
            throw new RegistrationException("Login can't be null.");
        }

        if (login.isEmpty()) {
            throw new RegistrationException("Login can't be empty string.");
        }

        if (login.length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException(
                    "Login must be at least %s symbols long.".formatted(MIN_LOGIN_LENGTH)
            );
        }
    }

    private void validatePassword(User user) {
        String password = user.getPassword();

        if (password == null) {
            throw new RegistrationException("Password can't be null.");
        }

        if (password.isEmpty()) {
            throw new RegistrationException("Password can't be empty string.");
        }

        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException(
                    "Password must be at least %s symbols long.".formatted(MIN_PASSWORD_LENGTH)
            );
        }
    }

    private void validateAge(User user) {
        Integer age = user.getAge();

        if (age == null) {
            throw new RegistrationException("Age can't be null.");
        }

        if (age < 0) {
            throw new RegistrationException("Age can't be negative.");
        }

        if (age < MIN_AGE) {
            throw new RegistrationException("Age must be at least %s.".formatted(MIN_AGE));
        }
    }
}
