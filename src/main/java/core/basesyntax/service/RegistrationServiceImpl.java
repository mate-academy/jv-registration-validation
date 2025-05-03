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
            throw new RegistrationException("User cannot be null.");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login cannot be null.");
        }
        if (user.getLogin().length() < 6) {
            throw new RegistrationException("Login must be at least 6 characters long, but was "
                    + user.getLogin().length() + ".");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password cannot be null.");
        }
        if (user.getPassword().length() < 6) {
            throw new RegistrationException("Password must be at least 6 characters long, but was "
                    + user.getPassword().length() + ".");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age cannot be null.");
        }
        if (user.getAge() < 0) {
            throw new RegistrationException("Age cannot be negative, but was "
                    + user.getAge() + ".");
        }
        if (user.getAge() < 18) {
            throw new RegistrationException("User must be at least 18 years old, but was "
                    + user.getAge() + ".");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with login '" + user.getLogin()
                    + "' already exists.");
        }

        return storageDao.add(user);
    }
}
