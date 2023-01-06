package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private static final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkUser(user);
        checkLogin(user);
        checkPassword(user);
        checkAge(user);
        return storageDao.add(user);
    }

    private void checkAge(User user) {
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("User must be at least 18 years old");
        }
    }

    private void checkPassword(User user) {
        if (user.getPassword() == null
                || user.getPassword().isEmpty()) {
            throw new RuntimeException("Password cannot be empty");
        }
        if (user.getPassword().length() < MINIMUM_PASSWORD_LENGTH) {
            throw new RuntimeException("Password must contain at least 6 characters");
        }
    }

    private void checkLogin(User user) {
        if (user.getLogin() == null
                || user.getLogin().isEmpty()
                || !Character.isLetter(user.getLogin().charAt(0))
                || user.getLogin().contains("\\W")) {
            throw new RuntimeException("Incorrect login");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Login already exists");
        }
    }

    private void checkUser(User user) {
        if (user == null) {
            throw new RuntimeException("User is null");
        }
    }
}
