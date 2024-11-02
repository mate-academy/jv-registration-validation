package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.InvalidRegistrationData;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_REGISTRATION_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidRegistrationData("Input user is null");
        }
        if (user.getLogin() == null || user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new InvalidRegistrationData("Login must be at least "
                    + MIN_LOGIN_LENGTH
                    + " characters long");
        }
        if (user.getPassword() == null || user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidRegistrationData("Password must be at least "
                    + MIN_PASSWORD_LENGTH
                    + " characters long");
        }
        if (user.getAge() == null || user.getAge() < MIN_REGISTRATION_AGE) {
            throw new InvalidRegistrationData("User must be at least "
                    + MIN_REGISTRATION_AGE
                    + " years old to register");
        }

        User userFromDb = storageDao.get(user.getLogin());
        if (userFromDb != null) {
            throw new InvalidRegistrationData("User with such login already exists");
        }

        return storageDao.add(user);
    }
}
