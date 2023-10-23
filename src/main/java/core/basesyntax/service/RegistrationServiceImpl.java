package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidAgeException;
import core.basesyntax.exception.InvalidArgumentException;
import core.basesyntax.exception.InvalidLoginException;
import core.basesyntax.exception.InvalidPasswordException;
import core.basesyntax.exception.UserAlreadyExistException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_USER_AGE = 18;
    private static final int MIN_PASSWORD_LOGIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkUserIsNull(user);
        checkUserAge(user);
        checkUserLogin(user);
        checkUserPassword(user);
        checkIfUserExist(user);
        return storageDao.add(user);
    }

    private void checkUserIsNull(User user) {
        if (user == null) {
            throw new InvalidArgumentException("Incorrect argument value. User can't be null.");
        }
    }

    private void checkUserAge(User user) {
        if (user.getAge() == null) {
            throw new InvalidAgeException("Incorrect age value. Age can't be null.");
        }
        if (user.getAge() < MIN_USER_AGE) {
            throw new InvalidAgeException("Incorrect age value. User must be 18 or older.");
        }
    }

    private void checkUserLogin(User user) {
        if (user.getLogin() == null) {
            throw new InvalidLoginException("Incorrect login value. Login can't be null.");
        }
        if (user.getLogin().length() < MIN_PASSWORD_LOGIN_LENGTH) {
            throw new InvalidLoginException("Incorrect login value. "
                    + "Login length less than 6 letters.");
        }
    }

    private void checkUserPassword(User user) {
        if (user.getPassword() == null) {
            throw new InvalidPasswordException("Incorrect password value. Password can't be null.");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LOGIN_LENGTH) {
            throw new InvalidPasswordException("Incorrect password value. "
                    + "Password length less than 6 letters.");
        }
    }

    private void checkIfUserExist(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new UserAlreadyExistException("Such user already exist");
        }
    }
}
