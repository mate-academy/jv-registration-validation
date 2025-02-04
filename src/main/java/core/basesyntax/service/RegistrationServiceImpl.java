package core.basesyntax.service;

import core.basesyntax.RegistrationUserException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationUserException("User cannot be null");
        }
        if (user.getLogin() == null || user.getLogin().length() < 6) {
            throw new RegistrationUserException("Login can't be null"
                    + "and must be at least 6 characters long.");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationUserException("User with this login already exists.");
        }

        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new RegistrationUserException("Password can't be null"
                    + "and must be at least 6 characters long.");
        }
        if (user.getAge() < 18) {
            throw new RegistrationUserException("Age must be at least 18 years old.");
        }
        return storageDao.add(user);
    }
}
