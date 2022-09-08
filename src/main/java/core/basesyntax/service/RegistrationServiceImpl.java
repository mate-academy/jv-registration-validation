package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_USER_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("User data entered incorrectly");
        }
        checkLogin(user.getLogin());
        checkAge(user.getAge());
        checkPassword(user.getPassword());
        return storageDao.add(user);
    }

    private void checkLogin(String login) {
        if (login == null || login.isEmpty()) {
            throw new RuntimeException("login can not be null or empty");
        }
        if (!login.contains("@")) {
            throw new RuntimeException("login entered with error");
        }
        if (storageDao.get(login) != null) {
            throw new RuntimeException("This login is already taken");
        }
    }

    private void checkPassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new RuntimeException("Password entered with error");
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Your password must be at least 6 characters");
        }
    }

    private void checkAge(Integer age) {
        if (age == null) {
            throw new RuntimeException("You need to enter your age");
        }
        if (age < MIN_USER_AGE) {
            throw new RuntimeException("Your age must be not less 18");
        }
    }
}
