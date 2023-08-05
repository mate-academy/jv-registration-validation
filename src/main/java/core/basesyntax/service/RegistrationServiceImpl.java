package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.UserInvalidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        verifyData(user);
        storageDao.add(user);
        return user;
    }

    private void verifyData(User user) {
        if (user == null) {
            throw new UserInvalidDataException(
                    "You cannot pass a null value to a method");
        }
        if (user.getLogin() == null) {
            throw new UserInvalidDataException(
                    "User login cannot be null");
        }
        if (user.getPassword() == null) {
            throw new UserInvalidDataException(
                    "User password cannot be null");
        }
        if (user.getLogin().length() < MIN_LENGTH) {
            throw new UserInvalidDataException(
                    "User login must be at least " + MIN_LENGTH
                            + " characters long. Actual login length "
                            + user.getLogin().length());
        }
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new UserInvalidDataException(
                    "User password must be at least " + MIN_LENGTH
                            + " characters long. Actual password length "
                            + user.getPassword().length());
        }
        if (user.getAge() < MIN_AGE) {
            throw new UserInvalidDataException(
                    "User must be " + MIN_AGE + " years of age or older");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new UserInvalidDataException(
                    "User already present in database");
        }
    }
}
