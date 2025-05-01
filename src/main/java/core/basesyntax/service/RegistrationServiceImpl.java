package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final Integer MIN_LOGIN_LENGTH = 6;
    private static final Integer MIN_PASSWORD_LENGTH = 6;
    private static final Integer MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("Login cannot be null.");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password or age cannot be null.");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age cannot be null.");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with such login already exists.");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("Login is not valid: "
                    + user.getLogin().length()
                    + " characters. Should be at least " + MIN_LOGIN_LENGTH);
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Password is not valid: "
                    + user.getPassword().length()
                    + " characters. Should be at least " + MIN_PASSWORD_LENGTH);
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Not valid age: "
                    + user.getAge() + ". Minimum allowed age is: " + MIN_AGE);
        }
        return storageDao.add(user);
    }
}
