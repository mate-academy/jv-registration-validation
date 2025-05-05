package core.basesyntax.service;
import core.basesyntax.exception.InvalidUserException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao;

    public RegistrationServiceImpl(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidUserException("User cannot be null.");
        }
        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            throw new InvalidUserException("Login cannot be null or empty.");
        }
        if (user.getLogin().length() < 6) {
            throw new InvalidUserException("Login must be at least 6 characters long.");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidUserException("User with this login already exists.");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new InvalidUserException("Password cannot be null or empty.");
        }
        if (user.getPassword().length() < 6) {
            throw new InvalidUserException("Password must be at least 6 characters long.");
        }
        if (user.getAge() == null || user.getAge() < 18) {
            throw new InvalidUserException("User must be at least 18 years old.");
        }
        return storageDao.add(user);
    }
}

