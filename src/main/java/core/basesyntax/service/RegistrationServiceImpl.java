package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int AGE_MIN = 18;
    private static final int MIN_PASSWORD = 6;
    private static final String DO_NOT_USE_NAME = "Maksym";
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUser(user);
        validateAge(user);
        validateLogin(user);
        validatePassword(user);
        validateName(user);
        return storageDao.add(user);
    }

    private void validateName(User user) {
        if (user.getName() == null) {
            throw new RuntimeException("Name is 'null' it's bad. You should have name");
        }
        if (user.getName().equals(DO_NOT_USE_NAME)) {
            throw new RuntimeException("Please change name because my boss doesn't like this name");
        }
    }

    private void validateAge(User user) {
        if (user.getAge() == null) {
            throw new RuntimeException("Age is 'null' it's bad");
        }
        if (user.getAge() < AGE_MIN) {
            throw new RuntimeException("You are still a child");
        }
    }

    private void validateLogin(User user) {
        if (user.getLogin() == null) {
            throw new RuntimeException("Login is 'null' it's bad");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("You are late, this login already exists");
        }
    }

    private void validatePassword(User user) {
        if (user.getPassword() == null) {
            throw new RuntimeException("Password is 'null' it's bad");
        }
        if (user.getPassword().length() < MIN_PASSWORD) {
            throw new RuntimeException("Password is short");
        }
    }

    private void validateUser(User user) {
        if (user == null) {
            throw new RuntimeException("User can't be null");

        }
    }
}
