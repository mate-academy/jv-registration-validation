package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_AGE = 18;
    private static final int MINIMUM_PASSWORD_LENGTH = 6;
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

    private void ageValidation(Integer age) {
        if (age == null) {
            throw new RuntimeException("Age can't be null");
        }
        if (age < MINIMUM_AGE) {
            throw new RuntimeException("Age must be 18 or more, but was: " + age);
        }
    }

    private void passwordValidation(String password) {
        if (password == null) {
            throw new RuntimeException("Password can't be null");
        }
        if (password.isEmpty()) {
            throw new RuntimeException("Password can't be empty");
        }
        if (password.length() < MINIMUM_PASSWORD_LENGTH) {
            throw new RuntimeException("Password length must be " + MINIMUM_PASSWORD_LENGTH
            + " or more, but was: " + password.length());
        }
    }

    private void loginValidation(String login) {
        if (login == null) {
            throw new RuntimeException("Login can't be null");
        }
        if (login.isEmpty()) {
            throw new RuntimeException("Login can't be empty");
        }
        if (storageDao.get(login) != null) {
            throw new RuntimeException("User with this login " + login
            + " already exist");
        }
    }

}
