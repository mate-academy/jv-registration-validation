package core.basesyntax.service;

import core.basesyntax.InvalidUserDataException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int LOGIN_LENGTH = 6;
    private static final int PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkUserNotNull(user);
        checkUserAlreadyExists(user);
        validateUserData(user);

        try {
            storageDao.add(user);
            return user;
        } catch (RuntimeException e) {
            throw new InvalidUserDataException("Error during user registration");
        }
    }

    private void checkUserNotNull(User user) {
        if (user == null) {
            throw new InvalidUserDataException("User cannot be null");
        }
    }

    private void checkUserAlreadyExists(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidUserDataException("User already exists");
        }
    }

    private void validateUserData(User user) {
        if (!(user.getLogin().length() >= LOGIN_LENGTH
                && user.getPassword().length() >= PASSWORD_LENGTH && user.getAge() >= MIN_AGE)) {
            throw new InvalidUserDataException("Invalid user data");
        }
    }
}
