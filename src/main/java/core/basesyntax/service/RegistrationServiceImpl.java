package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMAL_AGE = 18;
    private static final int MINIMAL_PASSWORD_LENGTH = 6;
    private static final int MINIMAL_LOGIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateLogin(user.getLogin());
        validatePassword(user.getPassword());
        validateAge(user.getAge());

        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User already exists");
        }

        return storageDao.add(user);
    }

    private void validateLogin(String login) {
        if (login == null) {
            throw new RegistrationException("Login cannot be null");
        }
        if (login.length() < MINIMAL_LOGIN_LENGTH) {
            throw new RegistrationException("Invalid login format "
                    + login + ". Min number of characters: " + MINIMAL_LOGIN_LENGTH);
        }

    }

    private void validatePassword(String password) {
        if (password == null) {
            throw new RegistrationException("Password cannot be null");
        }
        if (password.length() < MINIMAL_PASSWORD_LENGTH) {
            throw new RegistrationException("Invalid login format "
                    + password + ". Min number of characters: " + MINIMAL_PASSWORD_LENGTH);
        }
    }

    private void validateAge(int age) {
        if (age < MINIMAL_AGE) {
            throw new RegistrationException("Invalid login format "
                    + age + ". Min allowed age is " + MINIMAL_AGE);
        }
    }

}
