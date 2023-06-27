package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_SIZE_OF_STRING = 6;
    private static final int MIN_ALLOWED_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidDataException("User cannot be null");
        }
        if (user.getLogin() == null) {
            throw new InvalidDataException("Login cannot be null");
        }
        if (user.getPassword() == null) {
            throw new InvalidDataException("Password cannot be null");
        }
        if (user.getAge() == null) {
            throw new InvalidDataException("Age cannot be null");
        }
        if (storageDao.get(user.getLogin()) != null
                && storageDao.get(user.getLogin()).getLogin() == user.getLogin()) {
            throw new InvalidDataException("This login has already been created");
        }
        if (user.getLogin().length() < MIN_SIZE_OF_STRING
                || user.getPassword().length() < MIN_SIZE_OF_STRING) {
            throw new InvalidDataException("Your login or password is less than 6 characters");
        }
        if (user.getAge() < MIN_ALLOWED_AGE) {
            throw new InvalidDataException(
                    "Your are too young. You need to be at least 18 years old");
        }
        return storageDao.add(user);
    }
}
