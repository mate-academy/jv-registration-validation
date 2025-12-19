package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exeption.InvalidInputException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int minLoginLength = 6;
    private static final int minPasswordLength = 6;
    private static final int minAge = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null || user.getAge() == null
                || user.getPassword() == null
                || user.getLogin() == null) {
            throw new InvalidInputException("User object or fields can't be null");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidInputException("User already created");
        }

        if (user.getLogin().length() < minLoginLength) {
            throw new InvalidInputException("login must have 6 letters");
        }

        if (user.getPassword().length() < minPasswordLength) {
            throw new InvalidInputException("Password must have 6 letters");
        }

        if (user.getAge() < minAge) {
            throw new InvalidInputException("Age must be 18 or older");
        }

        return storageDao.add(user);
    }
}
