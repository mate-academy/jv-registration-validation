package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exeption.InvalidAgeException;
import core.basesyntax.exeption.InvalidLoginException;
import core.basesyntax.exeption.InvalidPasswordException;
import core.basesyntax.exeption.InvalidUserException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_USER_AGE = 18;
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MAX_LOGIN_LENGTH = 20;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MAX_PASSWORD_LENGTH = 20;
    private StorageDao storageDao;

    public RegistrationServiceImpl(StorageDaoImpl storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidUserException("This user is null");
        }
        validateAge(user);
        validateLogin(user);
        validatePassword(user);
        return storageDao.add(user);
    }

    private void validateAge(User user) {
        if (user.getAge() < MIN_USER_AGE) {
            throw new InvalidAgeException("Registration age must be over 18");
        }
    }

    private void validateLogin(User user) {
        validateLoginNotNull(user);
        validateLoginLength(user);
        checkLoginAvailability(user);
    }

    private void validateLoginNotNull(User user) {
        if (user.getLogin() == null) {
            throw new InvalidLoginException("Login cannot be null");
        }
    }

    private void validateLoginLength(User user) {
        int currentLoginLength = user.getLogin().length();
        if (currentLoginLength < MIN_LOGIN_LENGTH || currentLoginLength > MAX_LOGIN_LENGTH) {
            throw new InvalidLoginException("The login length must be at least 6 characters"
                    + " and no more than 20. Your length: " + currentLoginLength);
        }
    }

    private void checkLoginAvailability(User user) {
        String currentLogin = user.getLogin();
        if (storageDao.get(currentLogin) != null) {
            throw new InvalidLoginException("This login is already taken");
        }
    }

    private void validatePassword(User user) {
        validatePasswordNotNull(user);
        validatePasswordLength(user);
    }

    private void validatePasswordNotNull(User user) {
        if (user.getPassword() == null) {
            throw new InvalidPasswordException("Password cannot be null");
        }
    }

    private void validatePasswordLength(User user) {
        int currentPasswordLength = user.getPassword().length();
        if (currentPasswordLength < MIN_PASSWORD_LENGTH
                || currentPasswordLength > MAX_PASSWORD_LENGTH) {
            throw new InvalidPasswordException("The password length must be at least 6 characters"
                    + " and no more than 20. Your length: " + currentPasswordLength);
        }
    }
}
