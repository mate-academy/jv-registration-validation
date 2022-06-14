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
        checkUserLogin(user.getLogin());
        checkUserPassword(user.getPassword());
        checkUserAge(user.getAge());
        return storageDao.add(user);
    }

    private void checkUserLogin(String login) {
        if (login == null || login.isEmpty()) {
            throw new RuntimeException("Login is invalid!");
        }
        if (storageDao.get(login) != null) {
            throw new RuntimeException("This login is already taken!");
        }
        if (!login.contains("@")) {
            throw new RuntimeException("Login should contains @!");
        }
        if (!Character.isLetter(login.charAt(0))) {
            throw new RuntimeException("First symbol should be letter!");
        }
    }

    private void checkUserPassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new RuntimeException("You should enter a password!");
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("The length password should be more 5 symbols!");
        }
    }

    private void checkUserAge(Integer age) {
        if (age == null) {
            throw new RuntimeException("You should write your age!");
        }
        if (age < MIN_USER_AGE) {
            throw new RuntimeException("Your age less than 18!");
        }
    }
}
