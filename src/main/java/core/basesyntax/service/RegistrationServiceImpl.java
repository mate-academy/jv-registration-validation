package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIUM_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 120;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        getUser(user);
        getLogin(user);
        getPassword(user);
        getAge(user);
        return storageDao.add(user);
    }

    private static void getAge(User user) {
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("user must be at least 18 years old");
        }
        if (user.getAge() > MAX_AGE) {
            throw new RuntimeException("Incorrect age");
        }
    }

    private static void getPassword(User user) {
        if (user.getPassword() == null
                || user.getPassword().isEmpty()) {
            throw new RuntimeException("password cannot be empty");
        }
        if (user.getPassword().length() < MINIUM_PASSWORD_LENGTH) {
            throw new RuntimeException("password must contain at least 8 characters");
        }
    }

    private void getLogin(User user) {
        if (user.getLogin() == null
                || user.getLogin().isEmpty()
                || !Character.isLetter(user.getLogin().charAt(0))
                || user.getLogin().contains("\\W")) {
            throw new RuntimeException("incorrect login");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("login already exists");
        }
    }

    private static void getUser(User user) {
        if (user == null) {
            throw new RuntimeException("user is null");
        }
    }
}
