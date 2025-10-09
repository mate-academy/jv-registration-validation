package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User data is null");
        }
        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            throw new RegistrationException(
                    "Login must be at least 6 character: was null or empty"
            );
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User already exists");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("Login should be at least 6 characters");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password should not be null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Password should be at least 6 characters");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age should not be null");
        }
        if (user.getAge() <= 0) {
            throw new RegistrationException("Age should be greater than 0");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Age should be at least 18");
        }
        return storageDao.add(user);
    }
}
