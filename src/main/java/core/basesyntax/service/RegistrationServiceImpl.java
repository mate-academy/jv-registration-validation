package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_FIELD_LENGTH = 6;
    private static final int MIN_AGE = 18;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User can't be null");
        }

        String login = user.getLogin();
        if (login == null || login.length() < MIN_FIELD_LENGTH) {
            throw new RegistrationException("Login should be at least "
                    + MIN_FIELD_LENGTH + " characters");
        }

        String password = user.getPassword();
        if (password == null || password.length() < MIN_FIELD_LENGTH) {
            throw new RegistrationException("Password should be at least "
                    + MIN_FIELD_LENGTH + " characters");
        }

        Integer age = user.getAge();
        if (age == null || age < MIN_AGE) {
            throw new RegistrationException("User should be at least "
                    + MIN_AGE + " years old");
        }

        if (storageDao.get(login) != null) {
            throw new RegistrationException("User with this login already exists");
        }

        return storageDao.add(user);
    }
}
