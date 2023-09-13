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
        String login = user.getLogin();
        if (login == null) {
            throw new InvalidDataException("Login can't be null");
        }
        if (login.length() < MIN_LENGTH || login.isBlank()) {
            throw new InvalidDataException("Login must be at least 6 characters long");
        }
        if (storageDao.get(login) != null) {
            throw new InvalidDataException("User with this login already exists");
        }
    }

    private void validatePassword(User user) {
        String password = user.getPassword();
        if (password == null) {
            throw new InvalidDataException("Password can't be null");
        }
        if (password.length() < MIN_LENGTH || password.isBlank()) {
            throw new InvalidDataException("Password must be at least 6 characters long");
        }
    }

    private void validateAge(User user) {
        Integer age = user.getAge();
        if (age == null) {
            throw new InvalidDataException("Age can't be null");
        }
        if (age < MIN_AGE) {
            throw new InvalidDataException("Not valid age: "
                    + user.getAge()
                    + ". Min allowed age is " + MIN_AGE);
        }
    }
}
