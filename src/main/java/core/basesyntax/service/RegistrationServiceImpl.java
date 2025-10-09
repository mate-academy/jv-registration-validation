package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User data is null");
        }
        if (userAlreadyExists(user)) {
            throw new RegistrationException("User already exists");
        }
        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            throw new RegistrationException("Login is empty");
        }
        if (user.getLogin().length() < 6) {
            throw new RegistrationException("Login should be at least 6 characters");
        }
        if (user.getPassword().length() < 6) {
            throw new RegistrationException("Password should be at least 6 characters");
        }
        if (user.getAge() < 18) {
            throw new RegistrationException("Age should be at least 18");
        }
        storageDao.add(user);
        return user;
    }

    private boolean userAlreadyExists(User user) {
        for (User u : Storage.people) {
            if (u.getLogin().equals(user.getLogin())) {
                return true;
            }
        }
        return false;
    }
}
