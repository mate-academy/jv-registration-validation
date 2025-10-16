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
            throw new RegistrationException("User cannot be null");
        }

        String login = user.getLogin();
        if (login == null) {
            throw new RegistrationException("Login cannot be null");
        }

        if (login.length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("Login must have at least "
                    + MIN_LOGIN_LENGTH + " characters");
        }

        if (storageDao.get(login) != null) {
            throw new RegistrationException("Login already exists");
        }

        String password = user.getPassword();
        if (password == null) {
            throw new RegistrationException("Password cannot be null");
        }

        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Password must have at least "
                    + MIN_PASSWORD_LENGTH + "characters");
        }

        Integer age = user.getAge();
        if (age == null) {
            throw new RegistrationException("Your age cannot be null");
        }

        if (age < MIN_AGE) {
            throw new RegistrationException("Your age must be at least " + MIN_AGE + " years");
        }

        return storageDao.add(user);
    }
}
