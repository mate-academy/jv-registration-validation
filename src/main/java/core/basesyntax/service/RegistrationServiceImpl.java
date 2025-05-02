package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkUserDataIsValid(user);
        checkUserExist(user);

        return storageDao.add(user);
    }

    private void checkUserDataIsValid(User user) {
        if (user == null) {
            throw new UserRegistrationError("Can't register null user");
        }

        if (user.getLogin() == null) {
            throw new UserRegistrationError("Login can't be null");
        }

        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new UserRegistrationError("Login length is less than " + MIN_LOGIN_LENGTH);
        }

        if (user.getPassword() == null) {
            throw new UserRegistrationError("Password can't be null");
        }

        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new UserRegistrationError("Password length is less than " + MIN_PASSWORD_LENGTH);
        }

        if (user.getAge() == null) {
            throw new UserRegistrationError("Age can't be null");
        }

        if (user.getAge() < MIN_AGE) {
            throw new UserRegistrationError("Age is less than " + MIN_AGE);
        }
    }

    private void checkUserExist(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new UserRegistrationError("User already exist.");
        }
    }
}
