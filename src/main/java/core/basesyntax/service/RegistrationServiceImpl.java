package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.InvalidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_LOGIN_LENGTH = 6;
    private static final int MINIMUM_PASSWORD_LENGTH = 6;
    private static final int MINIMUM_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkIfUserIsNotNull(user);
        checkIfIdIsNotNull(user);
        checkIfLoginIsNotNull(user);
        checkIfPasswordIsNotNull(user);
        checkIfAgeIsNotNull(user);
        checkIfUserExists(user);
        checkLogin(user);
        checkPassword(user);
        checkAge(user);
        Storage.people.add(user);
        return user;
    }

    private void checkIfUserIsNotNull(User user) {
        if (user == null) {
            throw new InvalidDataException("The user is null");
        }
    }

    private void checkIfIdIsNotNull(User user) {
        if (user.getId() == null) {
            throw new InvalidDataException("The user's ID is null");
        }
    }

    private void checkIfLoginIsNotNull(User user) {
        if (user.getLogin() == null) {
            throw new InvalidDataException("The user's login is null");
        }
    }

    private void checkIfPasswordIsNotNull(User user) {
        if (user.getPassword() == null) {
            throw new InvalidDataException("The user's password is null");
        }
    }

    private void checkIfAgeIsNotNull(User user) {
        if (user.getAge() == null) {
            throw new InvalidDataException("The user's age is null");
        }
    }

    private void checkIfUserExists(User user) {
        if (Storage.people.contains(user)) {
            throw new InvalidDataException("This user is already registered");
        }
    }

    private void checkLogin(User user) {
        if (user.getLogin().length() < MINIMUM_LOGIN_LENGTH) {
            throw new InvalidDataException("The login is shorter than 6 characters");
        }
    }

    private void checkPassword(User user) {
        if (user.getPassword().length() < MINIMUM_PASSWORD_LENGTH) {
            throw new InvalidDataException("The password is shorter than 6 characters");
        }
    }

    private void checkAge(User user) {
        if (user.getAge() < MINIMUM_AGE) {
            throw new InvalidDataException("The age is less than 18");
        }
    }
}
