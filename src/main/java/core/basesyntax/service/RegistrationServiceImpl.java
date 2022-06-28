package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MAX_LENGTH_PASSWORD = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkLogin(user.getLogin());
        checkAge(user.getAge());
        checkPassword(user.getPassword());
        return storageDao.add(user);
    }

    private void checkLogin(String login) {
        if (login == null || login.isEmpty()) {
            throw new RuntimeException("Login is Invalid");
        }
        if (!login.contains("@")) {
            throw new RuntimeException("Login is Invalid");
        }
        if (storageDao.get(login) != null) {
            throw new RuntimeException("This login is already taken");
        }

    }

    private void checkPassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new RuntimeException("password is not correct");
        }
        if (password.length() < MAX_LENGTH_PASSWORD) {
            throw new RuntimeException("Your password must be not less 6");
        }

    }

    private void checkAge(Integer age) {
        if (age == null) {
            throw new RuntimeException("You can write your age");
        }
        if (age < MIN_AGE) {
            throw new RuntimeException("Your age must be not less 18");
        }
    }
}
