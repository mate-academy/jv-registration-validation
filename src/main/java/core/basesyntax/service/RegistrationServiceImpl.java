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
        if (user.getAge() == null || user.getAge() < MIN_USER_AGE) {
            String message = user.getAge() == null ? "Age can't be null."
                    : "User must be 18 or older.";
            throw new InvalidAgeException("Incorrect age value. " + message);
        }
    }

    private void checkUserLogin(User user) {
        if (user.getLogin() == null || user.getLogin().length() < MIN_PASSWORD_LOGIN_LENGTH) {
            String message = user.getLogin() == null ? "Login can't be null."
                    : "Login length less than 6 letters.";
            throw new InvalidLoginException("Incorrect login value. " + message);
        }
    }

    private void checkUserPassword(User user) {
        if (user.getPassword() == null || user.getPassword().length() < MIN_PASSWORD_LOGIN_LENGTH) {
            String message = user.getPassword() == null ? "Password can't be null."
                    : "Password length less than 6 letters.";
            throw new InvalidPasswordException("Incorrect password value. " + message);
        }
    }

    private void checkIfUserExist(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new UserAlreadyExistException("Such user already exist");
        }
    }
}
