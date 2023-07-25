package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.AlreadyRegisteredException;
import core.basesyntax.exceptions.ValidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final byte MIN_AUTHORIZATION_LENGTH = 6;
    private static final byte MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkNulls(user);
        isLoginLengthValid(user);
        isPasswordLengthValid(user);
        isLoginNotRegistered(user);
        isPasswordValid(user);
        isAgeValid(user);
        isLoginValid(user);
        return storageDao.add(user);
    }

    private void checkNulls(User user) {
        // Null login check
        if (user.getLogin() == null) {
            throw new ValidDataException("You did`t fill login field!");
        }
        // Null password check
        if (user.getPassword() == null) {
            throw new ValidDataException("You did`t fill password field!");
        }
        // Null age check
        if (user.getAge() == null) {
            throw new ValidDataException("You did`t fill age field!");
        }
    }

    private void isLoginNotRegistered(User user) {
        // Same login check
        if (storageDao.get(user.getLogin()) != null) {
            throw new AlreadyRegisteredException("Same login already registered");
        }
    }

    private void isPasswordLengthValid(User user) {
        // Additional validation for password
        if (user.getPassword().length() < MIN_AUTHORIZATION_LENGTH) {
            throw new ValidDataException("Password must be at least 6 characters long.");
        }
    }

    private void isLoginLengthValid(User user) {
        // Additional validation for login
        if (user.getLogin().length() < MIN_AUTHORIZATION_LENGTH) {
            throw new ValidDataException("Login must be at least 6 characters long.");
        }
    }

    private void isPasswordValid(User user) {
        for (Character character : user.getPassword().toCharArray()) {
            if (Character.isLetterOrDigit(character)) {
                continue;
            }
            throw new ValidDataException("Password is not meets A-Z-a-z-1-9");
        }
    }

    private void isLoginValid(User user) {
        for (Character character : user.getLogin().toCharArray()) {
            if (Character.isLetter(character) || Character.isDigit(character)) {
                continue;
            }
            throw new ValidDataException("Login is not meets A-Z-a-z-1-9");
        }
    }

    private void isAgeValid(User user) {
        if (user.getAge() < MIN_AGE) {
            throw new ValidDataException("Your age is not acceptable. "
                    + "Come here again after " + (MIN_AGE - user.getAge())
                    + " year/s \n We will wait for you ;)");
        }
    }
}
