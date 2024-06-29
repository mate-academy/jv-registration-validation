package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;
import java.util.Objects;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int DEFAULT_LENGTH = 6;
    private static final int DEFAULT_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws InvalidDataException {
        if (user == null) {
            throw new InvalidDataException("User is null");
        }
        validateLogin(user);
        validatePassword(user);
        validateAge(user);
        return storageDao.add(user);
    }

    private void validateLogin(User user) throws InvalidDataException {
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("User with this login already exists in the system");
        } else if (user.getLogin() == null || Objects.equals(user.getLogin(), "")) {
            throw new InvalidDataException("Login is empty or null");
        } else if (user.getLogin().length() < DEFAULT_LENGTH) {
            throw new InvalidDataException("Login too short");
        }
    }

    private void validatePassword(User user) throws InvalidDataException {
        if (user.getPassword() == null || Objects.equals(user.getPassword(), "")) {
            throw new InvalidDataException("Password is empty or null");
        } else if (user.getPassword().length() < DEFAULT_LENGTH) {
            throw new InvalidDataException("Password too short");
        }
    }

    private void validateAge(User user) throws InvalidDataException {
        if (user.getAge() == null) {
            throw new InvalidDataException("User's age is null");
        } else if (user.getAge() < DEFAULT_AGE) {
            throw new InvalidDataException("User's age under 18");
        }
    }
}
