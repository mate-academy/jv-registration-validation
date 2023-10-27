package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final String INVALID_DATA = "Invalid user data."
            + " Please check login, password, and age.";
    private static final String EXISTS_MSG = "User with this login already exists.";
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUserData(user);
        return storageDao.add(user);
    }

    private void validateUserData(User user) {
        if (user == null) {
            throw new InvalidUserException(INVALID_DATA);
        }

        if (user.getLogin() == null) {
            throw new InvalidUserException(INVALID_DATA);
        }

        if (user.getPassword() == null) {
            throw new InvalidUserException(INVALID_DATA);
        }

        if (user.getAge() == null) {
            throw new InvalidUserException(INVALID_DATA);
        }

        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new InvalidUserException(INVALID_DATA);
        }

        if (user.getPassword().length() < MIN_LOGIN_LENGTH) {
            throw new InvalidUserException(INVALID_DATA);
        }

        if (user.getAge() < MIN_AGE) {
            throw new InvalidUserException(INVALID_DATA);
        }

        User existingUser = storageDao.get(user.getLogin());
        if (existingUser != null) {
            throw new InvalidUserException(EXISTS_MSG);
        }
    }

}
