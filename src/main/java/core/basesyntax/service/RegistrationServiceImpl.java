package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_USER_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User can't be null");
        }
        if (user.getLogin() == null || storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("There is user with such login yet");
        }
        if (user.getAge() == null || user.getAge() < MIN_USER_AGE) {
            throw new RegistrationException("User should be at least 18 years old");
        }
        if (user.getPassword() == null || user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Password should be at least 6 characters");
        }
        return storageDao.add(user);
    }
}
