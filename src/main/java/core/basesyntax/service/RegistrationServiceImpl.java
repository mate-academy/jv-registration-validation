package core.basesyntax.service;

import core.basesyntax.InvalidDataException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH = 6;
    private static final int MINIMUM_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        userNotNullValidate(user);
        validateLogin(user);
        validateAge(user);
        validatePassword(user);
        userExistValidate(user);
        storageDao.add(user);
        return user;
    }

    private void validateLogin(User user) {
        if (user.getLogin() == null) {
            throw new InvalidDataException("Your login is null");
        }
        if (user.getLogin().length() < MIN_LENGTH) {
            throw new InvalidDataException("Your login is too short, must be at least "
                    + MIN_LENGTH + " symbols");
        }
    }

    private void validatePassword(User user) {
        if (user.getPassword() == null) {
            throw new InvalidDataException("Your password is null");
        }
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new InvalidDataException("Your password is too short, must be at least "
                    + MIN_LENGTH + " symbols");
        }
    }

    private void validateAge(User user) {
        if (user.getAge() == null) {
            throw new InvalidDataException("Oops, age is null");
        }
        if (user.getAge() < MINIMUM_AGE) {
            throw new InvalidDataException("Sorry, for registration you must be at least "
                    + MINIMUM_AGE + " years");
        }
    }

    private void userExistValidate(User user) {
        if (Storage.people.contains(user)) {
            throw new InvalidDataException("Ooops, user with same login is already exist");
        }
    }

    private void userNotNullValidate(User user) {
        if (user == null) {
            throw new InvalidDataException("Ooops, user is not exist");
        }
    }
}
