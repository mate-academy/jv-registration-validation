package core.basesyntax.service;

import core.basesyntax.RegistrationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MINIMUM_LOGIN_LENGTH = 6;
    public static final int MINIMUM_PASSWORD_LENGTH = 6;
    public static final int MINIMUM_ALLOWED_AGE = 18;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User can't be null");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (user.getLogin().length() < MINIMUM_LOGIN_LENGTH) {
            throw new RegistrationException("Login can't be shorter than 6 characters");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with this login already exists");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (user.getPassword().length() < MINIMUM_PASSWORD_LENGTH) {
            throw new RegistrationException("Password can't be shorter than 6 characters");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age can't be null");
        }
        if (user.getAge() < MINIMUM_ALLOWED_AGE) {
            throw new RegistrationException("Age can't be less than 18 years old");
        }
        return storageDao.add(user);
    }
}
