package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.UserRegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final String EMPTY_LOGIN = "";
    private static final int MINIMAL_AGE = 18;
    private static final int MINIMAL_PASSWORD_LENGTH = 6;
    private static final int MAXIMUM_USER_AGE = 125;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUserData(user);
        checkIfUserAlreadyExists(user);
        return storageDao.add(user);
    }

    private void checkIfUserAlreadyExists(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new UserRegistrationException("User with such login already exists");
        }
    }

    private void validateUserData(User user) {
        if (user == null) {
            throw new UserRegistrationException("User is null");
        }
        if (user.getLogin() == null) {
            throw new UserRegistrationException("User's login is null");
        }
        if (user.getLogin().equals(EMPTY_LOGIN)) {
            throw new UserRegistrationException("User's login is empty");
        }
        if (user.getPassword() == null) {
            throw new UserRegistrationException("User's password is null");
        }
        if (user.getAge() == null) {
            throw new UserRegistrationException("User's age is null");
        }
        if (user.getAge() < MINIMAL_AGE) {
            throw new UserRegistrationException("User's age should be at least 18");
        }
        if (user.getAge() > MAXIMUM_USER_AGE) {
            throw new UserRegistrationException("User's age is too big");
        }
        if (user.getPassword().length() < MINIMAL_PASSWORD_LENGTH) {
            throw new UserRegistrationException("User's password"
                   + " should contain at least 6 characters");
        }
    }
}
