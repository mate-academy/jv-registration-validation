package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User can not be null");
        }

        if (user.getPassword() == null) {
            throw new RegistrationException("Password can not be null");
        }

        if (user.getAge() == null) {
            throw new RegistrationException("Age can not be null");
        }

        if (user.getLogin() == null) {
            throw new RegistrationException("Login can not be null");
        }

        User existingUser = storageDao.get(user.getLogin());

        if (existingUser != null) {
            throw new RegistrationException("Such User login already exists");
        }

        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("User must be older than 17");

        }

        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("User's login must be longer than 5 characters");

        }

        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("User's password must be longer than 5 characters");
        }

        return storageDao.add(user);
    }
}
