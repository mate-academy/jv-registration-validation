package core.basesyntax.service;

import core.basesyntax.InvalidDataException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_CHARACTERS = 6;
    private static final int MINIMUM_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws InvalidDataException {
        isUserNotNull(user);
        validateLogin(user);
        validateAge(user);
        validatePassword(user);
        isUserExist(user);
        storageDao.add(user);
        return user;
    }

    private void validateLogin(User user) {
        if (user.getLogin() != null && user.getLogin().length() >= MINIMUM_CHARACTERS ) {
            return;
        }
        if (user.getLogin() == null) {
            throw new InvalidDataException("Your login is not exist");
        }
        if (user.getLogin().length() < MINIMUM_CHARACTERS) {
            throw new InvalidDataException("Your login is too short");
        }
    }

    private void validatePassword(User user) {
        if (user.getPassword() != null && user.getPassword().length() >= MINIMUM_CHARACTERS ) {
            return;
        }
        if (user.getPassword() == null) {
            throw new InvalidDataException("Your password is not exist");
        }
        if (user.getPassword().length() < MINIMUM_CHARACTERS) {
            throw new InvalidDataException("Your password is too short");
        }
    }

    private void validateAge(User user) {
        if (user.getAge() != null && user.getAge() >= MINIMUM_AGE) {
            return;
        }
        if (user.getAge() == null) {
            throw new InvalidDataException("Oops, age is not exist");
        }
        if (user.getAge() < MINIMUM_AGE) {
            throw new InvalidDataException("Sorry, for registration you must be at least " + MINIMUM_AGE + " years");
        }
    }

    private void isUserExist(User user) {
        if (Storage.people.contains(user)) {
            throw new InvalidDataException("Ooops, user with same login is already exist");
        }
    }

    private void isUserNotNull(User user) {
        if (user == null) {
            throw new InvalidDataException("Ooops, user is not exist");
        }
    }
}
