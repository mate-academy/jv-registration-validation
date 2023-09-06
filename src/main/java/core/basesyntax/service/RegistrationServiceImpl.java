package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.service.exception.InvalidInputDataException;
import core.basesyntax.service.exception.UserAlreadyExistsException;
import core.basesyntax.service.exception.UserIsNullException;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_VALID_LENGTH = 6;
    private static final int MIN_VALID_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new UserIsNullException("Null-user can't be registered.");
        }
        if (!userExists(user.getLogin())
                && loginIsValid(user.getLogin())
                && passwordIsValid(user.getPassword())
                && ageIsValid(user.getAge())) {
            storageDao.add(user);
            return user;
        }
        return null;
    }

    public boolean userExists(String login) {
        if (login == null) {
            return false;
        }
        if (storageDao.get(login) != null) {
            throw new UserAlreadyExistsException("User with login ["
                    + login + "] already exists");
        }
        return false;
    }

    public boolean loginIsValid(String login) {
        if (login == null) {
            throw new InvalidInputDataException("Login can't be null.");
        }
        if (login.length() < MIN_VALID_LENGTH) {
            throw new InvalidInputDataException("Login must have more then "
                    + MIN_VALID_LENGTH + " characters.");
        }
        return true;
    }

    public boolean passwordIsValid(String password) {
        if (password == null) {
            throw new InvalidInputDataException("Password can't be null.");
        }
        if (password.length() < MIN_VALID_LENGTH) {
            throw new InvalidInputDataException("Password must have more then "
                    + MIN_VALID_LENGTH + " characters.");
        }
        return true;
    }

    public boolean ageIsValid(Integer age) {
        if (age == null) {
            throw new InvalidInputDataException("Age can't be null.");
        }
        if (age < MIN_VALID_AGE) {
            throw new InvalidInputDataException("Age must be more " + MIN_VALID_AGE);
        }
        return true;
    }
}
