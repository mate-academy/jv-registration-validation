package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final String WEAK_PASSWORD = "123456";
    private static final int MAX_PASSWORD_LENGTH = 255;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MAX_LOGIN_LENGTH = 30;
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkIfUserExists(user);
        validateUser(user);
        checkLogin(user);
        checkPassword(user);

        storageDao.add(user);
        return user;
    }

    private void checkIfUserExists(User user) {
        User existingUser = storageDao.get(user.getLogin());
        if (existingUser != null) {
            throw new RegistrationException("There is a user with that login");
        }
    }

    private void validateUser(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("User login cannot be null");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("User age cannot be null");
        }
        if (user.getAge() == 0) {
            throw new RegistrationException("User age cannot be zero");
        }
        if (user.getAge() < 0) {
            throw new RegistrationException("User age cannot be less than zero");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("User must be 18 years old or older");
        }
        if (user.getId() >= Long.MAX_VALUE) {
            throw new RegistrationException("User id is greater or equal long.MAX_VALUE");
        }
    }

    private void checkLogin(User user) {
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("Users login must contain at least 6 characters");
        }
        if (user.getLogin().length() > MAX_LOGIN_LENGTH) {
            throw new RegistrationException("User login is not within the allowed range");
        }
    }

    private void checkPassword(User user) {
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new RegistrationException("User password cannot be null or empty");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Users password must contain at least 6 characters");
        }
        if (user.getPassword().length() > MAX_PASSWORD_LENGTH) {
            throw new RegistrationException("User password is not within the allowed range");
        }
        if (user.getPassword().equals(WEAK_PASSWORD)) {
            throw new RegistrationException("User password is too weak. "
                    + "Please choose a stronger password.");
        }
    }
}

