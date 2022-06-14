package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int VALID_AGE = 18;
    private static final int VALID_PASSWORD_LENGTH = 6;
    private final StorageDao storageUser = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkUser(user);
        checkLogin(user);
        checkAge(user);
        checkPassword(user);
        return storageUser.add(user);
    }

    private void checkUser(User user) {
        if (user == null) {
            throw new RuntimeException("User null");
        }
    }

    private void checkLogin(User user) {
        if (user.getLogin() == null || user.getLogin().isBlank()) {
            throw new RuntimeException("Login is empty. Input your login");
        }
        if (storageUser.get(user.getLogin()) != null) {
            throw new RuntimeException("Login used");
        }
    }

    private void checkAge(User user) {
        if (user.getAge() == null) {
            throw new RuntimeException("Age is empty. Input your age");
        }
        if (user.getAge() < VALID_AGE) {
            throw new RuntimeException("Invalid age. Your can`t be registered with this age");
        }
    }

    private void checkPassword(User user) {
        if (user.getPassword() == null) {
            throw new RuntimeException("Password is empty. Input your password");
        }
        if (user.getPassword().length() < VALID_PASSWORD_LENGTH) {
            throw new RuntimeException("Password length should be more 6 character");
        }
    }
}
