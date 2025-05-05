package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MIN_LOGIN_LENGTH = 6;
    public static final int MIN_PASSWORD_LENGTH = 6;
    public static final int MIN_USER_AGE = 18;
    //public static final int MAX_USER_AGE = 120;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new ValidationException("User can't be null");
        }
        if (user.getLogin() == null) {
            throw new ValidationException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new ValidationException("Password can't be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new ValidationException("User with this login already exists");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new ValidationException("Login length must be greater than 6 characters");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new ValidationException("Password length must be greater than 6 characters");
        }
        if (user.getAge() < MIN_USER_AGE) {
            throw new ValidationException("Age must be over 18 years old");
        }
        return storageDao.add(user);
    }
}
