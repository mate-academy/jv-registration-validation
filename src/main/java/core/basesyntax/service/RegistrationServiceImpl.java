package core.basesyntax.service;

import core.basesyntax.InvalidUserException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int AGE_THRESHOLD = 18;
    private static final int MIN_NUMBER_OF_CHARS = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkForNulls(user);
        checkIfUserExists(user);
        checkForValidParameters(user);
        storageDao.add(user);
        return user;
    }

    private void checkIfUserExists(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidUserException("User with such login already exists");
        }
    }

    private static void checkForValidParameters(User user) {
        if (user.getAge() < AGE_THRESHOLD) {
            throw new InvalidUserException("User is too young");
        }
        if (user.getLogin().length() < MIN_NUMBER_OF_CHARS) {
            throw new InvalidUserException("User's login is too short");
        }
        if (user.getPassword().length() < MIN_NUMBER_OF_CHARS) {
            throw new InvalidUserException("User's password is too short");
        }
    }

    private static void checkForNulls(User user) {
        if (user == null) {
            throw new InvalidUserException("User can't be null");
        }
        if (user.getAge() == null) {
            throw new InvalidUserException("User's age can't be null");
        }
        if (user.getLogin() == null) {
            throw new InvalidUserException("User's login can't be null");
        }
        if (user.getPassword() == null) {
            throw new InvalidUserException("User's password can't be null");
        }
    }
}
