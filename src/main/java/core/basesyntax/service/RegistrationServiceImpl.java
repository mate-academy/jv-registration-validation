package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH_OF_PASSWORD = 6;
    private static final int MIN_USERS_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkUsersLogin(user.getLogin());
        checkUsersPassword(user.getPassword());
        checkAge(user.getAge());
        return storageDao.add(user);
    }

    private void checkUsersLogin(String login) {
        if (login.isEmpty() || login.isBlank()) {
            throw new RuntimeException("Logins field is empty!");
        } else if (storageDao.get(login) != null) {
            throw new RuntimeException("This login already exists!");
        }
    }

    private void checkUsersPassword(String password) {
        if (password.isEmpty() || password.isBlank()) {
            throw new RuntimeException("Passwords field is empty!");
        } else if (password.length() < MIN_LENGTH_OF_PASSWORD) {
            throw new RuntimeException("This password " + password
                    + "must bee more, then " + MIN_LENGTH_OF_PASSWORD + "characters");
        }
    }

    private void checkAge(Integer age) {
        if (age == null) {
            throw new RuntimeException("Ages field is empty!");
        } else if (age < MIN_USERS_AGE) {
            throw new RuntimeException("User must be older, then " + MIN_USERS_AGE);
        }
    }
}
