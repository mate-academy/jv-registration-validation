package core.basesyntax.service;

import core.basesyntax.RegistrationValidationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        loginValidation(user.getLogin());
        passwordValidation(user.getPassword());
        ageValidation(user.getAge());
        storageDao.add(user);
        return user;
    }

    private void loginValidation(String login) {
        if (login == null) {
            throw new RegistrationValidationException("Login field is empty");
        }
        if (storageDao.get(login) != null) {
            throw new RegistrationValidationException("This login " + login + " already exists");
        }
    }

    private void passwordValidation(String password) {
        if (password == null) {
            throw new RegistrationValidationException("Password field is empty");
        }
        if (password.length() < MINIMUM_PASSWORD_LENGTH) {
            throw new RegistrationValidationException(
                    "Password must be at least 6 characters length");
        }
    }

    private void ageValidation(Integer age) {
        if (age == null) {
            throw new RegistrationValidationException("Age field is empty");
        }
        if (age < 0) {
            throw new RegistrationValidationException("Age value " + age + " is invalid");
        }
        if (age < 18) {
            throw new RegistrationValidationException("You must be of legal age");
        }
    }
}
