package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        loginNullCheck(user);
        passwordNullCheck(user);
        ageCheck(user);
        userRegistrationCheck(user);
        loginLengthCheck(user);
        passwordLengthCheck(user);
        return storageDao.add(user);
    }

    private void passwordLengthCheck(User user) {
        if (user.getPassword().length() < 6) {
            throw new RegistrationException("Password length ned to be 6 or more symbols");
        }
    }

    private void userRegistrationCheck(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User - " + user.getLogin() + " is already registered");
        }
    }

    private void loginLengthCheck(User user) {
        if (user.getLogin().length() < 6) {
            throw new RegistrationException("Login length ned to be 6 or more symbols");
        }
    }

    private void ageCheck(User user) {
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Not valid age: " + user.getAge()
                    + ". Min allowed age is " + MIN_AGE);
        }
    }

    private void passwordNullCheck(User user) {
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null");
        }
    }

    private void loginNullCheck(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
    }
}
