package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.UserValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new UserValidationException("User is null");
        }

        String login = user.getLogin();

        // there is no user with such login in the Storage yet
        User userFromDb = storageDao.get(login);
        if (userFromDb != null) {
            throw new UserValidationException("User with such login already exists");
        }

        // user's login is at least 6 characters
        String normalizedLogin = login.trim();
        if (normalizedLogin.length() < MIN_LOGIN_LENGTH) {
            throw new UserValidationException("User's login must be at least 6 characters long");
        }

        // user's password is at least 6 characters
        String normalizedPassword = user.getPassword().trim();
        if (normalizedPassword.length() < MIN_PASSWORD_LENGTH) {
            throw new UserValidationException("User's password must be at least 6 characters long");
        }

        // user's age is at least 18
        if (user.getAge() < MIN_AGE) {
            throw new UserValidationException("User's age must be at least 18");
        }

        return this.storageDao.add(user);
    }
}
