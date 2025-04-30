package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User is empty");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age can't be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with the same login already exists");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("Login should be at least 6 characters");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Password should be at least 6 characters");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("User must be adult");
        }
        return storageDao.add(user);
    }
}
