package core.basesyntax.service;

import core.basesyntax.RegistrationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 100;
    private static final int PASSWORD_MIN_LENGTH = 6;
    private static final int LOGIN_MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUser(user);
        validatePassword(user);
        validateLogin(user);
        validateAge(user);
        return storageDao.add(user);
    }

    private void validateUser(User user) {
        if (user == null) {
            throw new RegistrationException("User cannot be null!");
        }
    }

    private void validatePassword(User user) {
        if (user.getPassword() == null) {
            throw new RegistrationException("Your password should contain 6 or more symbols!");
        }
        if (user.getPassword().length() < PASSWORD_MIN_LENGTH) {
            throw new RegistrationException("Your password should contain %s or more symbols!"
                    .formatted(PASSWORD_MIN_LENGTH));
        }
    }

    private void validateLogin(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("Login cannot be null!");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with login %s already exists!"
                    .formatted(user.getLogin()));
        }
        if (user.getLogin().length() < LOGIN_MIN_LENGTH) {
            throw new RegistrationException("Your Login should contain %s or more symbols!");
        }
    }

    private void validateAge(User user) {
        if (user.getAge() == null) {
            throw new RegistrationException("Age cannot be null!");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("You age should be at least %d y.o."
                    .formatted(MIN_AGE));
        }
    }
}
