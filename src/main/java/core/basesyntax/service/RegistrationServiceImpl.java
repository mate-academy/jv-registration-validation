package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidUserDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_LENGTH = 6;
    private static final int MINIMUM_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidUserDataException("User can not be null!");
        }

        if (user.getLogin() == null) {
            throw new InvalidUserDataException("Login can not be null!");
        }

        if (user.getLogin().length() < MINIMUM_LENGTH) {
            throw new InvalidUserDataException("Invalid login:"
                    + user.getLogin()
                    + ". Login must be at least 6 characters!");
        }

        if (user.getPassword() == null) {
            throw new InvalidUserDataException("Password can not be null!");
        }

        if (user.getPassword().length() < MINIMUM_LENGTH) {
            throw new InvalidUserDataException("Invalid password:"
                    + user.getPassword()
                    + ". Password can not be null!");
        }

        if (user.getAge() < MINIMUM_AGE) {
            throw new InvalidUserDataException("Age: "
                    + user.getAge()
                    + " not allowed. User must be at least 18 years old!");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidUserDataException("Such user has already registered!");
        }

        storageDao.add(user);

        return user;
    }
}
