package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.UserRegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        String validationMessage = validateUser(user);
        if (validationMessage == null) {
            if (storageDao.get(user.getLogin()) == null) {
                return storageDao.add(user);
            } else {
                throw new UserRegistrationException("User with this login already exists.");
            }
        } else {
            throw new UserRegistrationException(validationMessage);
        }
    }

    private String validateUser(User user) {
        if (user == null) {
            return "User cannot be null.";
        }
        if (user.getLogin() == null || user.getLogin().length() < 6) {
            return "Login must be at least 6 characters long.";
        }
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            return "Password must be at least 6 characters long.";
        }
        if (user.getAge() == null || user.getAge() < 18) {
            return "User must be at least 18 years old.";
        }
        return null;
    }
}
