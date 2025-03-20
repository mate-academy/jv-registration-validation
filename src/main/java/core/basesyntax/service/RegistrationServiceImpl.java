package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidDataException("User can't be null");
        }
        if (user.getAge() == null || user.getAge() < 18) {
            throw new InvalidDataException("Invalid User's age");
        }
        if (user.getLogin() == null || user.getPassword() == null) {
            throw new InvalidDataException("User's credentials are invalid");
        }
        if (user.getLogin().length() < 6) {
            throw new InvalidDataException("Login is too short");
        }
        if (user.getPassword().length() < 6) {
            throw new InvalidDataException("Password is too short");
        }
        if (!(storageDao.get(user.getLogin()) == null)) {
            throw new InvalidDataException("Provided user is already registered");
        }
        storageDao.add(user);
        return user;
    }
}
