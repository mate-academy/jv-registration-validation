package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_CHARACTERS = 6;
    private static final int MINIMUM_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUser(user);
        storageDao.add(user);
        return user;
    }

    private void validateUser(User user) {
        if (user == null) {
            throw new InvalidDataException("User is null.");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("User already exists.");
        }

        if (user.getLogin() == null || user.getLogin().length() < MINIMUM_CHARACTERS) {
            throw new InvalidDataException(String.format(
                    "Login must be at least %d characters.", MINIMUM_CHARACTERS));
        }

        if (user.getPassword() == null || user.getPassword().length() < MINIMUM_CHARACTERS) {
            throw new InvalidDataException(String.format(
                    "Password must be at least %d characters.", MINIMUM_CHARACTERS));
        }

        if (user.getAge() == null || user.getAge() < MINIMUM_AGE) {
            throw new InvalidDataException(String.format("Age must be at least %d.", MINIMUM_AGE));
        }
    }
}
