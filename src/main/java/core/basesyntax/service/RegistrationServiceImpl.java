package core.basesyntax.service;

import core.basesyntax.customexception.InvalidDataException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkAge(user);
        checkLogin(user.getLogin());
        checkPassword(user.getPassword());
        return storageDao.add(user);
    }

    private void checkAge(User user) {
        if (user.getAge() == null) {
            throw new InvalidDataException("Age can`t be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new InvalidDataException("Can`t register user with age lower then " + MIN_AGE);
        }
    }

    private void checkLogin(String login) {
        if (login == null) {
            throw new InvalidDataException("Login can`t be null");
        }
        if (login.isEmpty()) {
            throw new InvalidDataException("Login can`t be empty");
        }
        if (storageDao.get(login) != null) {
            throw new InvalidDataException("This login already exists");
        }
    }

    private void checkPassword(String password) {
        if (password == null) {
            throw new InvalidDataException("Password can`t be null");
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidDataException("Password length can`t be less than "
                    + MIN_PASSWORD_LENGTH);
        }
    }
}
