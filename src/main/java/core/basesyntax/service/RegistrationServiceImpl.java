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
            throw new AuthenticationException("Login less than "
                    + MIN_NUMBER_OF_CHARACTERS + " characters");
        }
        if (user.getPassword().length() < MIN_NUMBER_OF_CHARACTERS) {
            throw new AuthenticationException("Password less than "
                    + MIN_NUMBER_OF_CHARACTERS + " characters");
        }
        if (user.getAge() < MINIMUM_AGE) {
            throw new AuthenticationException("Age less than " + MINIMUM_AGE + " years");
        }
    }

    private void checkNullPointerException(User user) {
        if (user == null) {
            throw new AuthenticationException("User == null");
        }
        if (user.getAge() == null) {
            throw new AuthenticationException("Age == null");
        }
        if (user.getLogin() == null) {
            throw new AuthenticationException("Login == null");
        }
        if (user.getPassword() == null) {
            throw new AuthenticationException("Password == null");
        }
    }
}
