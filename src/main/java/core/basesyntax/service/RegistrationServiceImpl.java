package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateLogin(user);
        validatePassword(user);
        validateAge(user);
        return storageDao.add(user);
    }

    private void validateLogin(User user) {
        if (user.getLogin() == null) {
            throw new InvalidDataException("Login can't be null");
        }
        if (user.getLogin().length() < MIN_LENGTH || user.getLogin().isBlank()) {
            throw new InvalidDataException("Login must be at least 6 characters long");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("User with this login already exists");
        }
    }

    private void validatePassword(User user) {
        if (user.getPassword() == null) {
            throw new InvalidDataException("Password can't be null");
        }
        if (user.getPassword().length() < MIN_LENGTH || user.getPassword().isBlank()) {
            throw new InvalidDataException("Password must be at least 6 characters long");
        }
    }

    private void validateAge(User user) {
        if (user.getAge() == null) {
            throw new InvalidDataException("Age can't be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new InvalidDataException("Not valid age: "
                    + user.getAge()
                    + ". Min allowed age is " + MIN_AGE);
        }
    }
}
