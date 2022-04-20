package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LOGIN_LENGTH = 4;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {

        if (user == null) {
            throw new NullPointerException("User is null");
        }

        if (user.getLogin() == null) {
            throw new NullPointerException("User's login is null");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User with login " + user.getLogin() + " already exists");
        }

        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RuntimeException("Login length must be at least "
                    + MIN_LOGIN_LENGTH + " characters");
        }

        if (checkAge(user)) {
            return storageDao.add(user);
        }

        if (checkPassword(user)) {
            return storageDao.add(user);
        }
        return storageDao.add(user);
    }

    private boolean checkAge(User user) {
        if (user.getAge() == null) {
            throw new NullPointerException("User's age is null");
        }

        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("Age must be at least " + MIN_AGE + " years old");
        }
        return true;
    }

    private boolean checkPassword(User user) {
        if (user.getPassword() == null) {
            throw new NullPointerException("User's password is null");
        }

        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Password length must be at least "
                    + MIN_PASSWORD_LENGTH + " characters");
        }
        return true;
    }
}
