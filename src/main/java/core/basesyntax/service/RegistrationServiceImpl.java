package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_CHARACTERS_LOGIN = 6;
    private static final int MINIMUM_CHARACTERS_PASSWORD = 6;
    private static final int MINIMUM_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws RegistrationException {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("This login already exist");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age can not be null");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can not be null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can not be null");
        }
        if (user.getAge() < MINIMUM_AGE) {
            throw new RegistrationException("Age must be at least 18 years old");
        }
        if (user.getLogin().length() < MINIMUM_CHARACTERS_LOGIN) {
            throw new RegistrationException("Login must be at least 6 characters");
        }
        if (user.getPassword().length() < MINIMUM_CHARACTERS_PASSWORD) {
            throw new RegistrationException("Password must be at least 6 characters");
        }
        return storageDao.add(user);
    }
}
