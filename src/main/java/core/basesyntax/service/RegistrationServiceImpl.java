package core.basesyntax.service;

import core.basesyntax.InvalidDataException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MIN_AGE = 18;
    public static final int MIN_LOGIN_LENGTH = 6;
    public static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getPassword() == null) {
            throw new InvalidDataException("Password is null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidDataException("Password is too small,"
                    + " minimal password length is " + MIN_PASSWORD_LENGTH + " characters");
        }
        if (user.getLogin() == null) {
            throw new InvalidDataException("Login is null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("User is already exists");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new InvalidDataException("Login is too small,"
                    + " minimal login length is " + MIN_LOGIN_LENGTH + " characters");
        }
        if (user.getAge() == null) {
            throw new InvalidDataException("Age is null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new InvalidDataException("Age is too small, minimal age is " + MIN_AGE);
        }
        storageDao.add(user);
        return user;
    }
}
