package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int AGE = 18;
    private static final int PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkAge(user);
        checkPassword(user);
        checkLogin(user);
        storageDao.add(user);
        return user;
    }

    private void checkAge(User user) {
        if (user.getAge() == null) {
            throw new NullPointerException("Age can't be null");
        }
        if (user.getAge() < AGE) {
            throw new RuntimeException("Age user must be over 18 years");
        }
    }

    private void checkPassword(User user) {
        if (user.getPassword().isEmpty()) {
            throw new RuntimeException("Password can't be empty");
        }
        if (user.getPassword() == null) {
            throw new NullPointerException("Password can't be null");
        }
        if (user.getPassword().length() < PASSWORD_LENGTH) {
            throw new RuntimeException("User password must be over 6 characters");
        }
    }

    private void checkLogin(User user) {
        if (user.getLogin() == null) {
            throw new NullPointerException("Login can't be null");
        }
        if (user.getLogin().isEmpty()) {
            throw new RuntimeException("User login can't be empty");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("The user already exists");
        }
    }
}
