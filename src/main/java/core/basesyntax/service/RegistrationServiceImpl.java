package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int AGE_MIN_VALUE = 18;
    private static final int PASSWORD_MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        String password = user.getPassword();
        String login = user.getLogin();
        Integer age = user.getAge();

        verifyPassword(password);
        verifyAge(age);
        verifyLogin(login);
        return storageDao.add(user);
    }

    private boolean verifyPassword(String password) {
        if (!checkStringHasWhitespacesOnly(password)) {
            throw new RuntimeException("User's Password should be more than 6 characters,"
                    + " not whitespaces");
        }
        if (password == null || password.length() < PASSWORD_MIN_LENGTH) {
            throw new RuntimeException("Password should have 6 and more characters");
        }
        return true;
    }

    private boolean verifyAge(Integer age) {
        if (age < AGE_MIN_VALUE) {
            throw new RuntimeException("User should be at least 18 y.o.");
        }
        return true;
    }

    private boolean verifyLogin(String login) {
        if (!checkStringHasWhitespacesOnly(login)) {
            throw new RuntimeException("User's LOGIN should be more than 4 characters");
        }
        if (login != null) {
            for (User registeredUser : Storage.people) {
                if (registeredUser.getLogin().equals(login)) {
                    throw new RuntimeException("User with such login exist already");
                }
            }
        }
        return true;
    }

    private boolean checkStringHasWhitespacesOnly(String str) {
        return str.trim().length() > 0;
    }
}
