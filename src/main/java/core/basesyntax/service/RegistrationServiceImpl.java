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
            throw new RegistrationException("User must not be null");
        }

        if (user.getLogin() == null || user.getLogin().trim().isEmpty()) {
            throw new RegistrationException("Login must not be null or empty");
        }

        if (user.getLogin().length() < 6) {
            throw new RegistrationException("Login must be at least 6 characters long");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("Login already exists");
        }

        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new RegistrationException("Password must not be null or empty");
        }

        if (user.getPassword().length() < 6) {
            throw new RegistrationException("Password must be at least 6 characters long");
        }

        if (user.getAge() == null) {
            throw new RegistrationException("Age must not be null");
        }

        if (user.getAge() < 18) {
            throw new RegistrationException("User must be at least 18 years old");
        }

        return storageDao.add(user);
    }
}
