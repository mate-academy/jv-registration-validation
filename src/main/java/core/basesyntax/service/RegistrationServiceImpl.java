package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exeption.InputDataException;
import core.basesyntax.exeption.LoginAlreadyExistsException;
import core.basesyntax.exeption.UserIsNullException;
import core.basesyntax.exeption.UsersAgeException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new UserIsNullException("User must be not null");
        }
        if (user.getLogin() == null) {
            throw new InputDataException("Login must be not null");
        }
        if (user.getPassword() == null) {
            throw new InputDataException("Password must be not null");
        }
        if (user.getLogin().length() < MIN_LENGTH) {
            throw new InputDataException("Login must be at least 6 characters");
        }
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new InputDataException("Password must be at least 6 characters");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new LoginAlreadyExistsException("This login is already exists");
        }
        if (user.getAge() == null || user.getAge() < MIN_AGE) {
            throw new UsersAgeException("Your age must be at least 18 years old");
        }
        storageDao.add(user);
        return user;
    }
}
