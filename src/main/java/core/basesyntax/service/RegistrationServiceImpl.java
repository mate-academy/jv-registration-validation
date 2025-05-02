package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidDataException("User cannot be null");
        }
        if (user.getLogin() == null || user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new InvalidDataException("Login should be at least "
                    + MIN_LOGIN_LENGTH + " charcters long");
        }
        if (user.getPassword() == null || user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidDataException("Password should be at least "
                    + MIN_PASSWORD_LENGTH + " characters long");
        }
        if (user.getAge() == null || user.getAge() < MIN_AGE) {
            throw new InvalidDataException("Age should be at least "
                    + MIN_AGE + " years");
        }

        User existingUser = storageDao.get(user.getLogin());
        if (existingUser != null) {
            throw new InvalidDataException("User with login "
                    + user.getLogin() + " is already exist");
        }
        return storageDao.add(user);
    }
}
