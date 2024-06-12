package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.ValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int REQUIRED_MINIMUM_AGE = 18;
    private static final int MINIMUM_POSSIBLE_AGE = 1;
    private static final int REQUIRED_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        loginCheck(user);
        ageCheck(user);
        passwordCheck(user);
        storageDao.add(user);
        return user;
    }

    private void passwordCheck(User user) throws ValidationException {
        if (user.getPassword() == null || user.getPassword().length() < REQUIRED_PASSWORD_LENGTH) {
            throw new ValidationException("Password is too short");
        }
    }

    private void ageCheck(User user) throws ValidationException {
        if (user.getAge() < MINIMUM_POSSIBLE_AGE) {
            throw new ValidationException("Impossible age");
        }
        if (user.getAge() < REQUIRED_MINIMUM_AGE) {
            throw new ValidationException("User is too young to be registered");
        }
    }

    private void loginCheck(User user) throws ValidationException {
        if (user.getLogin() == null) {
            throw new ValidationException("Login can`t be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new ValidationException("User with such login already exists");
        }
    }
}
