package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.excepction.UserRegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int VALID_AGE = 18;
    private static final int MIN_LOGIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    public User register(User user) {
        validateNotNull(user);
        validateAge(user);
        validateLogin(user);
        validatePassword(user);
        validateUserDoesNotExist(user);
        return storageDao.add(user);
    }

    private void validateNotNull(User user) {
        if (user == null) {
            throw new UserRegistrationException("User can't be null");
        }
    }

    private void validateAge(User user) {
        if (user.getAge() == null) {
            throw new UserRegistrationException("Age can't be null");
        }
        if (user.getAge() < VALID_AGE) {
            throw new UserRegistrationException("Age is not valid,"
                    + "must be equal to or greater than 18");
        }
    }

    private void validateLogin(User user) {
        if (user.getLogin() == null) {
            throw new UserRegistrationException("Login can't be null");
        }
        if (user.getLogin().length() < MIN_LOGIN_PASSWORD_LENGTH) {
            throw new UserRegistrationException("Login length must be equal to or longer than 6");
        }
    }

    private void validatePassword(User user) {
        if (user.getPassword() == null) {
            throw new UserRegistrationException("Password can't be null");
        }
        if (user.getPassword().length() < MIN_LOGIN_PASSWORD_LENGTH) {
            throw new UserRegistrationException("Password length must "
                    + "be equal to or longer than 6");
        }
    }

    private void validateUserDoesNotExist(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new UserRegistrationException("User already exists");
        }
    }
}
