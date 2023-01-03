package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASS_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new ValidationException("User has not to be null");
        }
        checkLogin(user);
        checkAge(user);
        checkPassword(user);
        return storageDao.add(user);
    }

    public void checkLogin(User user) {
        if (user.getLogin() == null) {
            throw new ValidationException("Login has not to be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new ValidationException("User is already in base");
        }
    }

    public void checkAge(User user) {
        if (user.getAge() == null) {
            throw new ValidationException("Age have not to be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new ValidationException("Age has to be 18 or more");
        }
    }

    public void checkPassword(User user) {
        if (user.getPassword() == null) {
            throw new ValidationException("Password has not to be null");
        }
        if (user.getPassword().length() < MIN_PASS_LENGTH) {
            throw new ValidationException("Password length has to be 6 or more symbols");
        }
    }
}
