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
            throw new UserNotFoundException("User is null and  can't register");
        }
        checkLogin(user.getLogin());
        checkPassword(user.getPassword());
        checkAge(user.getAge());
        return storageDao.add(user);
    }

    private void checkLogin(String login) {
        if (login == null || login.isEmpty()) {
            throw new UserNotFoundException("This login is incorrect");
        }
        if (storageDao.get(login) != null) {
            throw new UserNotFoundException("This user is already registered");
        }
    }

    private void checkPassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new UserNotFoundException("This password is incorrect");
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new UserNotFoundException("User password: " + password
                    + " is less than expected: " + MIN_PASSWORD_LENGTH);
        }
    }

    private void checkAge(int age) {
        if (age <= 0) {
            throw new UserNotFoundException("User age can't be zero or negative");
        }
        if (age < MIN_AGE) {
            throw new UserNotFoundException("User age: " + age
                    + " is less than allowed: " + MIN_AGE);
        }
    }

}
