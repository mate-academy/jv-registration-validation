package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int LOGIN_MIN_LENGTH = 6;
    private static final int PASSWORD_MIN_LENGTH = 6;
    private static final int AGE_MINIMAL_LIMIT = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User can't be null!");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null!");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null!");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age can't be null!");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with same login already exist!");
        }
        if (user.getLogin().length() < LOGIN_MIN_LENGTH) {
            throw new RegistrationException("Login should be more than six characters!");
        }
        if (user.getPassword().length() < PASSWORD_MIN_LENGTH) {
            throw new RegistrationException("Password should be more than six characters!");
        }
        if (user.getAge() < AGE_MINIMAL_LIMIT) {
            throw new RegistrationException(
                    "User should be 18 years old or more!"
            );
        }
        return storageDao.add(user);
    }
}
