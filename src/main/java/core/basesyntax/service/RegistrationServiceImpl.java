package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new ValidationException("User is null");
        }

        if (user.getLogin() == null) {
            throw new ValidationException("Login can't be null");
        }

        if (user.getPassword() == null) {
            throw new ValidationException("Password can't be null");
        }

        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new ValidationException("password should be at least 6 characters long");
        }

        if (user.getAge() < MIN_AGE) {
            throw new ValidationException("user must be at least 18 years old");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new ValidationException("user with such login exists");
        }

        return storageDao.add(user);
    }
}
