package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exeption.ValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH = 6;
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
        if (user.getLogin().length() < MIN_LENGTH) {
            throw new ValidationException("Login length is less than 6 characters");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new ValidationException("User with such login exists");
        }
        if (user.getAge() == null || user.getAge() < MIN_AGE) {
            throw new ValidationException("Age of user is less than 18 years");
        }
        if (user.getPassword() == null) {
            throw new ValidationException("Password can't be null");
        }
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new ValidationException("Password length is less than 6 characters");
        }
        return storageDao.add(user);
    }
}
