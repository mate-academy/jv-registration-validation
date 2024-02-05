package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_LOGIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User can't be null");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("User's age can't be null");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("User's login can't be null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("User's password can't be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("Login you provided already exists");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException(
                    "Login must contain at least " + MIN_LOGIN_LENGTH + " characters");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException(
                    "Password must contain at least " + MIN_PASSWORD_LENGTH + " characters");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException(
                    "Age must be at least " + MIN_AGE + " years");
        }
        return storageDao.add(user);
    }
}
