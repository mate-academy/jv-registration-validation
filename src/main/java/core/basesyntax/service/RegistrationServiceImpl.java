package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User cannot be null");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with such login already exists");
        }

        if (user.getLogin() == null || user.getLogin().trim().isEmpty()
                || user.getLogin().length() < 6) {
            throw new RegistrationException("Login"
                    + " must be at least 6 characters long and not blank");
        }

        if (user.getPassword() == null || user.getPassword().trim().isEmpty()
                || user.getPassword().length() < 6) {
            throw new RegistrationException("Password"
                    + " must be at least 6 characters long and not blank");
        }

        if (user.getAge() == null || user.getAge() < 18) {
            throw new RegistrationException("User must be at least 18 years old");
        }

        return storageDao.add(user);
    }
}
