package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.AuthenticationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null || user.getLogin() == null
                || user.getPassword() == null || user.getAge() == null) {
            throw new AuthenticationException("User is not authorized! Input data can't be null.");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new AuthenticationException("User with the same login is already exists.");
        }
        if (user.getAge() < MIN_AGE) {
            throw new AuthenticationException(
                    "User's age can't be negative and should be at least 18 years old.");
        }
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new AuthenticationException(
                    "User's password length should be at least 6 characters long.");
        }
        return storageDao.add(user);
    }
}
