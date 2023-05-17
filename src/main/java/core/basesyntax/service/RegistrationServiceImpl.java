package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 100;
    private static final int PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkUser(user);
        checkLogin(user);
        checkAge(user);
        checkPassword(user);
        return storageDao.add(user);
    }

    private void checkUser(User user) {
        if (user == null) {
            throw new InvalidDataException("User shouldn't be null!");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("User: " + user.getLogin() + " already exists!");
        }
    }

    private void checkLogin(User user) {
        if (user.getLogin() == null) {
            throw new InvalidDataException("Login shouldn't be null!");
        }
        if (user.getLogin().isEmpty()) {
            throw new InvalidDataException("Login shouldn't be empty!");
        }
    }

    private void checkAge(User user) {
        if (user.getAge() == null) {
            throw new InvalidDataException("Age shouldn't be null!");
        }
        if (user.getAge() < MIN_AGE) {
            throw new InvalidDataException("Age shouldn't be less than " + MIN_AGE + " years!");
        }
        if (user.getAge() > MAX_AGE) {
            throw new InvalidDataException("Age shouldn't be more than " + MAX_AGE + " years!");
        }
    }

    private void checkPassword(User user) {
        if (user.getPassword() == null) {
            throw new InvalidDataException("Password shouldn't be null!");
        }
        if (user.getPassword().isEmpty()) {
            throw new InvalidDataException("Password shouldn't be empty!");
        }
        if (user.getPassword().length() < PASSWORD_LENGTH) {
            throw new InvalidDataException("Password shouldn't be less than "
                    + PASSWORD_LENGTH + " characters!");
        }
    }
}
