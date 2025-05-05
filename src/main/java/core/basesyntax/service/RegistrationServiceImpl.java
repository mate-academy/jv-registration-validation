package core.basesyntax.service;

import core.basesyntax.RegistrationValidationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_PASSWORD_LENGTH = 6;
    private static final int MINIMUM_VALID_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationValidationException("Your data can't be empty");
        }
        checkLogin(user.getLogin());
        checkPassword(user.getPassword());
        checkAge(user.getAge());
        return storageDao.add(user);
    }

    private void checkLogin(String login) {
        if (login == null) {
            throw new RegistrationValidationException("Login field is empty");
        }
        if (storageDao.get(login) != null) {
            throw new RegistrationValidationException("This login " + login + " already exists");
        }
    }

    private void checkPassword(String password) {
        if (password == null) {
            throw new RegistrationValidationException("Password field is empty");
        }
        if (password.length() < MINIMUM_PASSWORD_LENGTH) {
            throw new RegistrationValidationException(
                    "Password must be at least " + MINIMUM_PASSWORD_LENGTH + " characters length");
        }
    }

    private void checkAge(Integer age) {
        if (age == null) {
            throw new RegistrationValidationException("Age field is empty");
        }
        if (age < MINIMUM_VALID_AGE) {
            throw new RegistrationValidationException("You must be of legal age");
        }
    }
}
