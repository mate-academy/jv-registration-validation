package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.UserInvalidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws UserInvalidDataException {
        validateUserNonNullFields(user);
        verifyLogin(user);
        verifyAge(user);
        verifyPassword(user);
        return storageDao.add(user);
    }

    private void validateUserNonNullFields(User user) throws UserInvalidDataException {
        if (user == null) {
            throw new UserInvalidDataException("User data can't be null");
        }
        if (user.getLogin() == null) {
            throw new UserInvalidDataException("User login can't be null");
        }
        if (user.getAge() == null) {
            throw new UserInvalidDataException("User age can't be null");
        }
        if (user.getPassword() == null) {
            throw new UserInvalidDataException("User password can't be null");
        }
    }

    private void verifyLogin(User user) throws UserInvalidDataException {
        if (storageDao.get(user.getLogin()) != null) {
            throw new UserInvalidDataException(String.format("User with this login already "
                    + "exists: %s", user.getLogin()));
        }

        if (user.getLogin().length() < MIN_LENGTH) {
            throw new UserInvalidDataException(String.format("User login length must be at least "
                    + "%d symbols, but length was: %d",MIN_LENGTH, user.getLogin().length()));
        }
    }

    private void verifyPassword(User user) throws UserInvalidDataException {
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new UserInvalidDataException(String.format("User password length must be at "
                    + "least %d symbols, but length was: %d",
                    MIN_LENGTH, user.getPassword().length()));
        }
    }

    private void verifyAge(User user) throws UserInvalidDataException {
        if (user.getAge() < MIN_AGE) {
            throw new UserInvalidDataException(String.format("User age must be at least %d"
                    + ", but was: %d",MIN_AGE, user.getAge()));
        }
    }
}
