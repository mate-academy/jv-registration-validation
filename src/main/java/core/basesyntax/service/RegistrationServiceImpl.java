package core.basesyntax.service;

import core.basesyntax.RegistrationValidationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_NUMBER_OF_CHARACTERS = 6;
    private static final int MINIMUM_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationValidationException("User cannot be null");
        }
        if (user.getLogin() == null || user.getLogin().length() < MINIMUM_NUMBER_OF_CHARACTERS) {
            throw new RegistrationValidationException("Login must contain at least 6 characters");
        }
        if (user.getPassword() == null
                || user.getPassword().length() < MINIMUM_NUMBER_OF_CHARACTERS) {
            throw new
                    RegistrationValidationException("Password must contain at least 6 characters");
        }
        if (user.getAge() == null || user.getAge() < 0) {
            throw new RegistrationValidationException("Age cannot be negative");
        }
        if (user.getAge() < MINIMUM_AGE) {
            throw new RegistrationValidationException("User must be at least 18 years old");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationValidationException("Login already exists");
        }
        return storageDao.add(user);
    }

    @Override
    public User get(String login) {
        return storageDao.get(login);
    }
}
