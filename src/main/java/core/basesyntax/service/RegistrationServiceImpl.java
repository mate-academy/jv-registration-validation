package core.basesyntax.service;

import core.basesyntax.UserValidationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new UserValidationException("User is null");
        }
        if (user.getLogin() == null) {
            throw new UserValidationException("User login is null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new UserValidationException("User with this login already exist");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new UserValidationException("User login should be at least 6 characters");
        }

        if (user.getPassword() == null) {
            throw new UserValidationException("User password is null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new UserValidationException("User password should be at least 6 characters");
        }

        if (user.getAge() == null) {
            throw new UserValidationException("User age is null");
        }

        if (user.getAge() < MIN_AGE) {
            throw new UserValidationException("User age should be at least 18");
        }
        return storageDao.add(user);
    }
}
