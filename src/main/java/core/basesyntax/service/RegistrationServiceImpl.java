package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.InvalidDataException;
import core.basesyntax.exceptions.UserAlreadyExistsException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();
    private final int MIN_AGE = 18;
    private final int MIN_PASSWORD_LENGTH = 6;

    @Override
    public User register(User user) {
        if (user == null || user.getClass() != User.class) {
            throw new InvalidDataException("Invalid data type provided.");
        }
        if (user.getLogin() == null) {
            throw new InvalidDataException("Login can't be null.");
        }
        if (user.getPassword() == null) {
            throw new InvalidDataException("Password can't be null.");
        }
        if (user.getAge() == null) {
            throw new InvalidDataException("Age can't be null.");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new UserAlreadyExistsException("User with the same login already exists.");
        }
        if (user.getAge() < MIN_AGE) {
            throw new InvalidDataException("User should be at least 18 years old.");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidDataException("Password should be at least 6 characters long.");
        }

        storageDao.add(user);
        return user;
    }
}
