package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new InvalidUserException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new InvalidUserException("Password can't be null");
        }

        if (user.getAge() < MIN_AGE) {
            throw new InvalidUserException("Not valid age: "
                    + user.getAge()
                    + ". Min allowed age is " + MIN_AGE);
        }

        if (user.getLogin().length() < 6 || user.getPassword().length() < 6) {
            throw new InvalidUserException("Login and password must be at least 6 characters long");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidUserException("User with this login already exists");
        }

        return storageDao.add(user);
    }
}
