package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RestrictionException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH_PASSWORD = 6;
    private static final int MIN_LENGTH_LOGIN = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUser(user);
        return storageDao.add(user);
    }

    private void validateUser(User user) {
        if (user == null) {
            throw new RestrictionException("Input cannot be null");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RestrictionException("User already exists");
        }

        if (user.getLogin() == null) {
            throw new RestrictionException("Login cannot be null");
        }

        if (user.getLogin().length() < MIN_LENGTH_LOGIN) {
            throw new RestrictionException("Login must be longer than 6 characters");
        }

        if (user.getPassword() == null) {
            throw new RestrictionException("Password cannot be null");
        }

        if (user.getPassword().length() < MIN_LENGTH_PASSWORD) {
            throw new RestrictionException("Password must be longer than 6 characters");
        }

        if (user.getAge() == 0) {
            throw new RestrictionException("Age cannot be null");
        }

        if (user.getAge() < 0) {
            throw new RestrictionException("Age cannot be negative");
        }

        if (user.getAge() <= MIN_AGE) {
            throw new RestrictionException("You should be 18 or older. Please try later");
        }
    }
}
