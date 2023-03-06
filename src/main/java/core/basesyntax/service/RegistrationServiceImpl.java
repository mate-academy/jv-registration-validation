package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 100;
    private static final int MIN_LOGIN_LENGTH = 5;
    private static final int MAX_LOGIN_LENGTH = 10;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MAX_PASSWORD_LENGTH = 30;
    private static final String EMPTY_LINE = "";
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkUserLogin(user.getLogin());
        checkUserPassword(user.getPassword());
        checkUserAge(user.getAge());
        return user;
    }

    private void checkUserLogin(String login) {
        if (login == null || login.equals(EMPTY_LINE)) {
            throw new RegistrationFailedException("You wrote an empty login!");
        }
        if (login.length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationFailedException("Your login must be at least " + MIN_LOGIN_LENGTH + " characters!");
        }
        if (login.length() > MAX_LOGIN_LENGTH) {
            throw new RegistrationFailedException("Your login must be lower than " + MAX_LOGIN_LENGTH + " characters!");
        }
        if (storageDao.get(login) != null) {
            throw new RegistrationFailedException("Your login already exist!");
        }
    }

    private void checkUserPassword(String password) {
        if (password == null || password.equals(EMPTY_LINE)) {
            throw new RegistrationFailedException("You wrote an empty password!");
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationFailedException("Your password must be at least " + MIN_PASSWORD_LENGTH + " characters!");
        }
        if (password.length() > MAX_PASSWORD_LENGTH) {
            throw new RegistrationFailedException("Your password must be lower than " + MAX_PASSWORD_LENGTH + " characters!");
        }
    }

    private void checkUserAge(Integer age) {
        if (age == null) {
            throw new RegistrationFailedException("You wrote an empty age!");
        }
        if (age < MIN_AGE) {
            throw new RegistrationFailedException("Your age must be over " + MIN_AGE);
        }
        if (age > MAX_AGE) {
            throw new RegistrationFailedException("Your age must lower than " + MAX_AGE);
        }

    }
}
