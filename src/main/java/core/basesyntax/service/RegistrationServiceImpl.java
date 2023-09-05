package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_VALID_LENGTH = 6;
    private static final int MINIMAL_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkUserRegistration(user);
        validateLogin(user);
        validatePassword(user);
        validateAge(user);
        return storageDao.add(user);
    }

    private void checkUserRegistration(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("User with current login is already registered");
        }
    }

    private void validateLogin(User user) {
        if (user.getLogin() == null) {
            throw new InvalidDataException("Login must be filled");
        }
        if (user.getLogin().length() < MIN_VALID_LENGTH) {
            throw new InvalidDataException(
                    String.format("Incorrect login. It should be at least %d symbols in you login",
                            MIN_VALID_LENGTH));
        }
    }

    private void validatePassword(User user) {
        if (user.getPassword() == null) {
            throw new InvalidDataException("Password must be filled");
        }
        if (user.getPassword().length() < MIN_VALID_LENGTH) {
            throw new InvalidDataException(
                    String.format("Incorrect password. It should be at least %d symbols in you password",
                            MIN_VALID_LENGTH));
        }
    }

    private void validateAge(User user) {
        if (user.getAge() == null || user.getAge() < MINIMAL_AGE) {
            throw new InvalidDataException(
                    String.format("Your should be at least %d years old", MINIMAL_AGE));
        }
    }
}
