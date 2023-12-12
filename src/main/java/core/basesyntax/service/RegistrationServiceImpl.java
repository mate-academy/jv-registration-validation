package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao;

    public RegistrationServiceImpl(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public User register(User user) {
        validateUser(user);
        return storageDao.add(user);
    }

    private void validateUser(User user) {
        if (user.getLogin() == null || user.getLogin().length() < 6) {
            throw new InvalidUserException("Invalid login - min 6 characters");
        }
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new InvalidUserException("Invalid password - min 6 characters");
        }
        if (user.getAge() == null || user.getAge() < 18) {
            throw new InvalidUserException("Invalid age - min 18 years old");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidUserException("A user with this login already exists");
        }
    }
}
