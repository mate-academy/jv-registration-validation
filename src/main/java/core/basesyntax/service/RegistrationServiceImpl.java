package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMAL_AGE = 18;
    private static final int MINIMAL_LOGIN_LENGTH = 6;
    private static final int MINIMAL_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User wasnt created");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (user.getLogin().length() <= MINIMAL_LOGIN_LENGTH) {
            throw new RegistrationException("Login can't be less than "
                    + MINIMAL_LOGIN_LENGTH + " characters");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (user.getPassword().length() <= MINIMAL_PASSWORD_LENGTH) {
            throw new RegistrationException("Password can't be less than "
                    + MINIMAL_PASSWORD_LENGTH + " characters");
        }
        if (user.getAge() < MINIMAL_AGE) {
            throw new RegistrationException("Not valid age: " + user.getAge()
                    + ". Min allowed age is " + MINIMAL_AGE);
        }
        if (storageDao.get(user.getLogin()) == null) {
            return storageDao.add(user);
        } else {
            throw new RegistrationException("User with such login already exists");
        }
    }
}
