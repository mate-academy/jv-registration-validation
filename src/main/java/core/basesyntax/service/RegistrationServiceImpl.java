package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final short MIN_LOGIN_LENGTH = 6;
    private static final short MIN_PASSWORD_LENGTH = 6;
    private static final short MIN_AGE = 18;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUser(user);
        return storageDao.add(user);
    }

    private void validateUser(User user) {
        validateNull(user);
        validatePassword(user);
        validateAge(user);
        validateLogin(user);
    }

    private void validateLogin(User user) {
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new ValidationException("User login can't be less than 6 symbols!");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new ValidationException("User with such login is already exist!");
        }
    }

    private void validateAge(User user) {
        if (user.getAge() < MIN_AGE) {
            throw new ValidationException("User age can't be less than 18 years old!");
        }
    }

    private void validatePassword(User user) {
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new ValidationException("User password can't be less than 6 symbols!");
        }
    }

    private void validateNull(User user) {
        if (user == null
                || user.getLogin() == null
                || user.getPassword() == null
                || user.getAge() == null) {
            throw new ValidationException("User data can't be null!");
        }
    }
}
