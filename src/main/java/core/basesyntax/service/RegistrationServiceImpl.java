package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.InvalidAgeException;
import core.basesyntax.exceptions.InvalidLoginException;
import core.basesyntax.exceptions.InvalidPasswordException;
import core.basesyntax.exceptions.UserAlreadyExistException;
import core.basesyntax.exceptions.ValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MIN_LOGIN_LENGTH = 6;
    public static final int MIN_PASSWORD_LENGTH = 6;
    public static final int MIN_USER_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new ValidationException("User is null");
        }

        if (user.getLogin() == null
                || user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new InvalidLoginException("You have to pass at least 6 characters");
        }

        if (user.getPassword() == null
                || user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidPasswordException("You have to pass at least 6 characters");
        }

        if (user.getAge() == null
                || user.getAge() < MIN_USER_AGE) {
            throw new InvalidAgeException("Expected at least 18 y.o");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new UserAlreadyExistException("User with such login exists");
        }

        return storageDao.add(user);
    }
}
