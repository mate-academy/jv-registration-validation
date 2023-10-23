package core.basesyntax.service;

import core.basesyntax.RegistrationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final String INVALID_PASSWORD_MESSAGE = "The provided password is invalid. "
            + "It should include at least 6 characters!";
    private static final String INVALID_LOGIN_MESSAGE = "The provided login is invalid. "
            + "It should include at least 6 characters!";
    private static final String LOGIN_EXISTS_MESSAGE = "The user with this login already exists.";
    private static final String USER_IS_NULL_MESSAGE = "User cannot be null!";
    private static final String AGE_INVALID_MESSAGE = "The age should be over 18.";
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH = 6;
    private StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (checkIfUserIsNotNull(user)
                && validateAge(user.getAge())
                && validateLogin(user.getLogin())
                && validatePassword(user.getPassword())
                && checkIfLoginExists(user.getLogin())) {
            storageDao.add(user);
            return user;
        }
        return null;
    }

    private boolean validatePassword(String password) {
        if (password == null || password.length() < MIN_LENGTH) {
            throw new RegistrationException(INVALID_PASSWORD_MESSAGE);
        }
        return true;
    }

    private boolean validateLogin(String login) {
        if (login == null || login.length() < MIN_LENGTH
                || login.replace(" ", "").length() < login.length()) {
            throw new RegistrationException(INVALID_LOGIN_MESSAGE);
        }
        return true;
    }

    private boolean checkIfLoginExists(String login) {
        if (storageDao.get(login) != null) {
            throw new RegistrationException(LOGIN_EXISTS_MESSAGE);
        }
        return true;
    }

    private boolean validateAge(Integer age) {
        if (age == null || age < MIN_AGE) {
            throw new RegistrationException(AGE_INVALID_MESSAGE);
        }
        return true;
    }

    private boolean checkIfUserIsNotNull(User user) {
        if (user == null) {
            throw new RegistrationException(USER_IS_NULL_MESSAGE);
        }
        return true;
    }
}
