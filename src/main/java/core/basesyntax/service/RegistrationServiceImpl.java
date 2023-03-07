package core.basesyntax.service;

import core.basesyntax.InvalidDataException;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int VALID_AGE = 18;
    private static final int VALID_LENGTH_PASS = 6;
    private StorageDaoImpl storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidDataException("User object can't be null");
        }
        if (user.getLogin() == null) {
            throw new InvalidDataException("User login can't be null");
        }
        if (user.getAge() == null) {
            throw new InvalidDataException("User age can't be null");
        }
        if (user.getPassword() == null) {
            throw new InvalidDataException("User password can't be null");
        }
        if (userIsInStorage(user) && isValidAge(user) && isValidPassword(user)) {
            storageDao.add(user);
            return user;
        }
        return null;
    }

    private boolean userIsInStorage(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("User with this login is already registered");
        }
        return true;
    }

    private boolean isValidAge(User user) {
        if (user.getAge() < VALID_AGE) {
            throw new InvalidDataException("Age must be at least " + VALID_AGE);
        }
        return true;
    }

    private boolean isValidPassword(User user) {
        if (user.getPassword().length() < VALID_LENGTH_PASS) {
            throw new InvalidDataException("Your password must contain at least "
                    + VALID_LENGTH_PASS + " characters");
        }
        return true;
    }
}
