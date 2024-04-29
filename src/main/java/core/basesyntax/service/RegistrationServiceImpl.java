package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int DEFAULT_LOGIN_LENGTH = 6;
    private static final int DEFAULT_PASSWORD_LENGTH = 6;
    private static final int MINIMAL_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User can't be null");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (user.getLogin().length() < DEFAULT_LOGIN_LENGTH) {
            throw new RegistrationException("Login must be longer then "
                    + DEFAULT_LOGIN_LENGTH + " characters");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with this login exists");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (user.getPassword().length() < DEFAULT_PASSWORD_LENGTH) {
            throw new RegistrationException("You password must be longer then "
                    + DEFAULT_PASSWORD_LENGTH + " characters");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age can't be null");
        }
        if (user.getAge() < MINIMAL_AGE) {
            throw new RegistrationException("Your age must be over "
                    + MINIMAL_AGE + " years old");
        }
        return storageDao.add(user);
    }
}
