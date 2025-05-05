package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int AGE_MINIMUM_FOR_REGISTRATION = 18;
    public static final int PASSWORD_MINIMUM_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkLoginUnique(user.getLogin());
        checkUserAgeRestriction(user.getAge());
        checkPasswordLengthRestriction(user.getPassword());
        return storageDao.add(user);
    }

    private void checkLoginUnique(String login) {
        if (login == null) {
            throw new NullPointerException();
        }
        if (storageDao.get(login) != null) {
            throw new RuntimeException(String.format("User %s already exists in storage", login));
        }
        if (login.isEmpty()) {
            throw new RuntimeException("User login is empty");
        }
    }

    private void checkUserAgeRestriction(Integer age) {
        if (age == null) {
            throw new NullPointerException();
        }
        if (age < AGE_MINIMUM_FOR_REGISTRATION) {
            throw new RuntimeException(String.format("User is under age %d (minimum %d)",
                    age, AGE_MINIMUM_FOR_REGISTRATION));
        }
    }

    private void checkPasswordLengthRestriction(String password) {
        if (password == null) {
            throw new NullPointerException();
        }
        if (password.length() < PASSWORD_MINIMUM_LENGTH) {
            throw new RuntimeException("Password is too shor (must be at least)");
        }
    }
}
