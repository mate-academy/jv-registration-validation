package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exeption.InvalidValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int VALID_LOGIN_LENGTH = 6;
    private static final int VALID_PASSWORD_LENGTH = 6;
    private static final int VALID_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUser(user);
        return storageDao.add(user);
    }

    private void validateUser(User user) {
        checkIfUserNull(user);
        checkLogin(user);
        checkPassword(user);
        checkAge(user);
        checkIfUserPresentInDataBase(user);

    }

    private void checkIfUserNull(User user) {
        if (user == null) {
            throw new InvalidValidationException("User cannot be null");
        }
    }

    private void checkLogin(User user) {
        if (user.getLogin() == null) {
            throw new InvalidValidationException("User's login cannot be null");
        }
        if (user.getLogin().length() < VALID_LOGIN_LENGTH) {
            throw new InvalidValidationException("Incorrect user`s login. Login must be at least "
                    + VALID_LOGIN_LENGTH + " characters");
        }
    }

    private void checkPassword(User user) {
        if (user.getPassword() == null) {
            throw new InvalidValidationException("User's password cannot be null");
        }
        if (user.getPassword().length() < VALID_PASSWORD_LENGTH) {
            throw new InvalidValidationException("Incorrect user`s password. Password "
                    + "must be at least  " + VALID_PASSWORD_LENGTH + " characters");
        }
    }

    private void checkAge(User user) {
        if (user.getAge() == null) {
            throw new InvalidValidationException("User's age cannot be null");
        }
        if (user.getAge() < VALID_AGE) {
            throw new InvalidValidationException("User must be older than " + VALID_AGE);
        }
    }

    private void checkIfUserPresentInDataBase(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidValidationException("The user was previously registered");
        }
    }

}
