package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int PASSWORD_LENGTH = 6;
    private static final int LOGIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        isUserNull(user);
        isLoginValid(user);
        isPasswordValid(user);
        isAgeValid(user);
        return storageDao.add(user);
    }

    private void isUserNull(User user) {
        if (user == null) {
            throw new InvalidDataException("User shouldn't be null!");
        }
    }

    private void isLoginValid(User user) {
        if (user.getLogin() == null) {
            throw new InvalidDataException("Login shouldn't be null!");
        }
        if (user.getLogin().isEmpty()) {
            throw new InvalidDataException("Login shouldn't be empty!");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("User: " + user.getLogin() + " already exists!");
        }
        if (user.getLogin().length() < LOGIN_LENGTH) {
            throw new InvalidDataException("Login must be longer then "
                    + LOGIN_LENGTH + " character, but was: "
                    + user.getLogin().length());
        }
    }

    private void isPasswordValid(User user) {
        if (user.getPassword() == null) {
            throw new InvalidDataException("Password shouldn't be null!");
        }
        if (user.getPassword().length() < PASSWORD_LENGTH) {
            throw new InvalidDataException("Password must be longer than "
                    + PASSWORD_LENGTH + " characters!");
        }
    }

    private void isAgeValid(User user) {
        if (user.getAge() == null) {
            throw new InvalidDataException("Age shouldn't be null!");
        }
        if (user.getAge() < MIN_AGE) {
            throw new InvalidDataException("Age shouldn't be less than " + MIN_AGE + " years!");
        }
    }

}
