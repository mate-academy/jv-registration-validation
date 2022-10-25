package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMAL_PASSWORD_LENGTH = 6;
    private static final int MINIMAL_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUser(user);
        validateLogin(user);
        validatePassword(user);
        validateAge(user);
        return storageDao.add(user);
    }

    private void validateUser(User user) {
        if (user == null) {
            throw new RuntimeException("User can`t be null");
        }
    }

    private void validateLogin(User user) {
        if (user.getLogin() == null) {
            throw new RuntimeException("Login can`t be null");
        }
        if (user.getLogin().isEmpty()) {
            throw new RuntimeException("Login can`t be enpty");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User whit this login exist already");
        }
    }

    private void validatePassword(User user) {
        if (user.getPassword() == null) {
            throw new RuntimeException("Password can`t be null");
        }
        if (user.getPassword().length() < MINIMAL_PASSWORD_LENGTH) {
            throw new RuntimeException("Password short that 6");
        }
    }

    private void validateAge(User user) {
        if (user.getAge() < MINIMAL_AGE) {
            throw new RuntimeException("You are too young, grow up and come back again");
        }
    }
}
