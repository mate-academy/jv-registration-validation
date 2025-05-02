package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_REG_AGE = 18;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new UserRegistrationException("User cannot be null");
        }
        if (user.getLogin() == null) {
            throw new UserRegistrationException("Login is empty.");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new UserRegistrationException(
                    "Login is too short, has to be at least " + MIN_LOGIN_LENGTH + " characters.");
        }
        if (user.getPassword() == null) {
            throw new UserRegistrationException("Password is empty.");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new UserRegistrationException(
                    "Password is too short, has to be at least "
                            + MIN_PASSWORD_LENGTH
                            + " characters.");
        }
        if (user.getAge() == null) {
            throw new UserRegistrationException("Age is empty.");
        }
        if (user.getAge() < MIN_REG_AGE) {
            throw new UserRegistrationException("Age has to be at least "
                    + MIN_REG_AGE
                    + " years.");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new UserRegistrationException("Login already exists.");
        }
        storageDao.add(user);
        return user;
    }
}
