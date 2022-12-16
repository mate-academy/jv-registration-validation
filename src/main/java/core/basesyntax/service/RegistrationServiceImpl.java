package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidInputDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int VALID_AGE = 18;
    private static final int VALID_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidInputDataException("User can't be null");
        }
        checkAge(user);
        checkPassword(user);
        checkLogin(user);
        return storageDao.add(user);
    }

    private void checkAge(User user) {
        if (user.getAge() == null) {
            throw new InvalidInputDataException("Age can't be null");
        }
        if (user.getAge() < VALID_AGE) {
            throw new InvalidInputDataException(String.format("Age should be %d or higher",
                    VALID_AGE));
        }
    }

    private void checkPassword(User user) {
        if (user.getPassword() == null) {
            throw new InvalidInputDataException("Password can't be null");
        }
        if (user.getPassword().length() < VALID_PASSWORD_LENGTH) {
            throw new InvalidInputDataException(
                    String.format("Password length should be at least %d characters long",
                            VALID_PASSWORD_LENGTH));
        }
    }

    private void checkLogin(User user) {
        if (user.getLogin() == null) {
            throw new InvalidInputDataException("Login can't be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidInputDataException("User with such login already exists");
        }
    }
}
