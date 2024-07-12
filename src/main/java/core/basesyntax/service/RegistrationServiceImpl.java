package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final StorageDao storageDao = new StorageDaoImpl();
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_LOGIN_LENGTH = 6;

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
        if (login.isEmpty()) {
            throw new RegistrationException("Login cannot be empty");
        }
        if (login.length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("Login length cannot be less than "
                    + MIN_LOGIN_LENGTH);
        }
    }

    private void validatePassword(String password) {
        if (password == null) {
            throw new RegistrationException("Password cannot be null");
        }
        if (password.isEmpty()) {
            throw new RegistrationException("Password cannot be empty");
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Password length cannot be less than "
                    + MIN_PASSWORD_LENGTH);
        }
    }

    private void validateAge(Integer age) {
        if (age == null) {
            throw new RegistrationException("Age cannot be null");
        }
        if (age < MIN_AGE) {
            throw new RegistrationException("User cannot be under " + MIN_AGE);
        }
    }
}
