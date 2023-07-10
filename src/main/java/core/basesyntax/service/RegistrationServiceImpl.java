package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int DEFAULT_PASSWORD_SIZE = 6;
    private static final int DEFAULT_LOGIN_SIZE = 6;
    private static final int MINIMUM_AGE_FOR_REGISTER = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new ValidationException("User cannot be null");
        }
        final String login = user.getLogin();
        final String password = user.getPassword();
        final Integer age = user.getAge();

        if (storageDao.get(login) != null) {
            throw new ValidationException("This user is already registered");
        }
        if (login == null || login.length() < DEFAULT_LOGIN_SIZE) {
            throw new ValidationException("Login must be longer than "
                    + DEFAULT_LOGIN_SIZE + " characters");
        }
        if (password == null || password.length() < DEFAULT_PASSWORD_SIZE) {
            throw new ValidationException("Password must be longer than "
                    + DEFAULT_PASSWORD_SIZE + " characters");
        }
        if (age == null || age < MINIMUM_AGE_FOR_REGISTER) {
            throw new ValidationException("You must be older than " + MINIMUM_AGE_FOR_REGISTER);
        }
        storageDao.add(user);
        return user;
    }
}
