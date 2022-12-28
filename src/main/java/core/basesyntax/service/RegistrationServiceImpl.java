package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidInputException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkUser(user);
        checkUserLogin(user.getLogin());
        checkUserAge(user.getAge());
        checkUserPassword(user.getPassword());
        return storageDao.add(user);
    }

    private void checkUser(User user) {
        if (user == null) {
            throw new InvalidInputException("User can not be null");
        }
    }

    private void checkUserPassword(String password) {
        if (password == null) {
            throw new InvalidInputException("User password can not be null");
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidInputException(
                    "User password length can not be less than "
                            + MIN_PASSWORD_LENGTH + " characters");
        }
    }

    private void checkUserAge(Integer age) {
        if (age == null) {
            throw new InvalidInputException("User age can not be null");
        }
        if (age < MIN_AGE) {
            throw new InvalidInputException("User age can not be less than " + MIN_AGE);
        }
    }

    private void checkUserLogin(String login) {
        if (login == null) {
            throw new InvalidInputException("User login can not be null");
        }
        if (storageDao.get(login) != null) {
            throw new InvalidInputException("User is already exists!");
        }
    }
}
