package core.basesyntax.service;

import core.basesyntax.dao.AuthenticationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_NUMBER_OF_CHARACTERS = 6;
    private static final int MINIMUM_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkNullPointerException(user);
        checkAuthenticationException(user);
        storageDao.add(user);
        return user;
    }

    private void checkAuthenticationException(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new AuthenticationException("A user with this login already exists");
        }
        if (user.getLogin().length() < MIN_NUMBER_OF_CHARACTERS) {
            throw new AuthenticationException("Login less than 6 characters");
        }
        if (user.getPassword().length() < MIN_NUMBER_OF_CHARACTERS) {
            throw new AuthenticationException("Password less than 6 characters");
        }
        if (user.getAge() < MINIMUM_AGE) {
            throw new AuthenticationException("Age less than 18 years");
        }
    }

    private void checkNullPointerException(User user) {
        if (user == null) {
            throw new NullPointerException("User == null");
        }
        if (user.getAge() == null) {
            throw new NullPointerException("Age == null");
        }
        if (user.getLogin() == null) {
            throw new NullPointerException("Login == null");
        }
        if (user.getPassword() == null) {
            throw new NullPointerException("Password == null");
        }
    }
}
