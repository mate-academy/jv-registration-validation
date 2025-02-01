package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.service.exception.AlreadyExistsException;
import core.basesyntax.service.exception.InvalidAgeException;
import core.basesyntax.service.exception.InvalidLoginException;
import core.basesyntax.service.exception.InvalidPasswordException;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new AlreadyExistsException("User already exists: "
                    + user.getLogin());
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new InvalidLoginException("Login must be at least "
                    + MIN_LOGIN_LENGTH
                    + " characters long.");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidPasswordException("Password must be at least "
                    + MIN_PASSWORD_LENGTH
                    + " characters long.");
        }
        if (user.getAge() < MIN_AGE) {
            throw new InvalidAgeException("User must be at least "
                    + MIN_AGE
                    + " years old.");
        }
        storageDao.add(user);
        return user;
    }
}
