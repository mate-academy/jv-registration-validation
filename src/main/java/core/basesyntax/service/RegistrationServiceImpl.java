package core.basesyntax.service;

import core.basesyntax.RegistrationIsFailedException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_USER_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws RegistrationIsFailedException {
        if (user == null) {
            throw new RegistrationIsFailedException("User can`t be null");
        }
        validateLogin(user.getLogin());
        validateAge(user.getAge());
        validatePassword(user.getPassword());
        validateLoginUniqueness(user);
        return storageDao.add(user);
    }

    private void validateLogin(String login) throws RegistrationIsFailedException {
        if (login == null) {
            throw new RegistrationIsFailedException("Login can`t be null");
        }
        if (login.isEmpty()) {
            throw new RegistrationIsFailedException("Login can`t be empty");
        }
        if (login.charAt(0) == Character.MIN_VALUE
                || login.charAt(login.length() - 1) == Character.MIN_VALUE) {
            throw new RegistrationIsFailedException("Login can`t contain blank symbols "
                    + "at the start or end of string");
        }
    }

    private void validateLoginUniqueness(User user) throws RegistrationIsFailedException {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationIsFailedException("User with this login is already registered");
        }
    }

    private void validatePassword(String password) throws RegistrationIsFailedException {
        if (password == null) {
            throw new RegistrationIsFailedException("Password can`t be null");
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationIsFailedException("Password should have at least "
                    + MIN_PASSWORD_LENGTH + " characters");
        }
    }

    private void validateAge(Integer age) throws RegistrationIsFailedException {
        if (age == null) {
            throw new RegistrationIsFailedException("Age can`t be null");
        }
        if (age <= 0) {
            throw new RegistrationIsFailedException("You might be kidding me");
        }
        if (age < MIN_USER_AGE) {
            throw new RegistrationIsFailedException("Registration is only for persons over "
                    + MIN_USER_AGE + " years old");
        }
    }
}
