package core.basesyntax.service;

import core.basesyntax.InvalidUserDataException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        final int minimalLoginLength = 6;
        final int minimalAge = 18;
        boolean isLoginTaken = storageDao.get(user.getLogin()) != null;
        boolean isTooShortLength = user.getLogin().length() < minimalLoginLength;
        boolean isTooShortPassword = user.getPassword().length() < minimalLoginLength;
        boolean isTooYoungUser = user.getAge() < minimalAge;

        if (user.getLogin() == null) {
            throwError("User login not provided");
        }
        if (user.getPassword() == null) {
            throwError("User password not provided");
        }
        if (user.getAge() < 0) {
            throwError("User age must be positive number");
        }
        if (isLoginTaken) {
            throwError("User with this login already exist");
        }
        if (isTooShortLength) {
            throwError("User login should be at least 6 characters long");
        }
        if (isTooShortPassword) {
            throwError("User password is week - should be at least 6 characters long");
        }
        if (isTooYoungUser) {
            throwError("Minimal user age is 18 years");
        }
        storageDao.add(user);
        return user;
    }

    private void throwError(String errorMessage) {
        throw new InvalidUserDataException("User validation failed : " + errorMessage);
    }
}
