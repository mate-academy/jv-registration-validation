package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("User can't be null");
        }
        loginValidation(user.getLogin());
        passwordValidation(user.getPassword());
        ageValidation(user.getAge());
        return storageDao.add(user);
    }

    private void loginValidation(String login) {
        if (login == null) {
            throw new RuntimeException("Login can't be null");
        }
        if (storageDao.get(login) != null) {
            throw new RuntimeException("User with this login: "
                    + login + " already exists");
        }
    }

    private void passwordValidation(String password) {
        if (password == null) {
            throw new RuntimeException("Password can't be null");
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Password`s length should be " + MIN_PASSWORD_LENGTH
                    + " or more, but is: " + password.length());
        }
    }

    private void ageValidation(Integer age) {
        if (age == null) {
            throw new RuntimeException("Age can't be null");
        }
        if (age < MIN_AGE) {
            throw new RuntimeException("Age should be 18 and older, but is: " + age);
        }
    }
}
