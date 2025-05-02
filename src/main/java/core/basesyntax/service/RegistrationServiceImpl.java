package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationDataException("User cannot be null");
        }
        if (user.getLogin() == null) {
            throw new RegistrationDataException("Login cannot be null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationDataException("Password cannot be null");
        }
        if (user.getAge() == null) {
            throw new RegistrationDataException("Age cannot be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationDataException("User with this login already exists");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationDataException("Login must be at least "
                    + MIN_LOGIN_LENGTH + " characters");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationDataException("Password must be at least "
                    + MIN_PASSWORD_LENGTH + " characters");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationDataException("User must be at least "
                    + MIN_AGE + " years old");
        }
        return storageDao.add(user);
    }
}
