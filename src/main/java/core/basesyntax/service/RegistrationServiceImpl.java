package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int USER_MINIMAL_AGE = 18;
    private static final int MINIMAL_PASSWORD_LENGTH = 6;
    private static final int MINIMAL_LOGIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validate(user);
        return storageDao.add(user);
    }

    private void validate(User user) throws RegistrationException {
        isUserNotNull(user);
        isUserLoginAlreadyExist(user.getLogin());
        validateUserAge(user.getAge());
        validateUserLogin(user.getLogin());
        validateUserPassword(user.getPassword());
    }

    private void isUserLoginAlreadyExist(String login) {
        if (storageDao.get(login) != null) {
            throw new RegistrationException("User with this login already exist");
        }
    }

    private void validateUserAge(int age) {
        if (age < USER_MINIMAL_AGE) {
            throw new RegistrationException("Minimal age requirement isn't met");
        }
    }

    private void validateUserPassword(String userPassword) {
        validateInputStringNotNull(userPassword);
        if (userPassword.length() < MINIMAL_PASSWORD_LENGTH) {
            throw new RegistrationException("User password is too short");
        }
    }

    private void validateUserLogin(String userLogin) {
        validateInputStringNotNull(userLogin);
        if (userLogin.length() < MINIMAL_LOGIN_LENGTH) {
            throw new RegistrationException("User login is too short");
        }
    }

    private void validateInputStringNotNull(String stringToValidate) {
        if (stringToValidate == null) {
            throw new RegistrationException("Can't be null");
        }
    }

    private void isUserNotNull(User user) {
        if (user == null) {
            throw new RegistrationException("User can't be null");
        }
    }
}
