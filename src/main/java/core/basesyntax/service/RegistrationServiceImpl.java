package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.ValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new ValidationException("The user must not being a null");
        }
        if (user.getLogin() == null) {
            throw new ValidationException("The login must not be a null");
        }
        if (user.getPassword() == null) {
            throw new ValidationException("The password must not be not a null");
        }
        if (user.getAge() == null) {
            throw new ValidationException("The age most not be a null");
        }
        if (!checkForMinCharsInLine(user)) {
            throw new ValidationException("Invalid length line!"
                    + "Length line must be at least 6 characters");
        }
        if (user.getAge() < MIN_AGE) {
            throw new ValidationException("Invalid age! The age must be at least 18 years old");
        }
        if (checkOnExistedLogin(user)) {
            throw new ValidationException("This login has already used");
        }
        return storageDao.add(user);
    }

    private boolean checkForMinCharsInLine(User user) {
        if (user.getLogin().length() < MIN_LENGTH || user.getPassword().length() < MIN_LENGTH) {
            return false;
        }
        return true;
    }

    private boolean checkOnExistedLogin(User user) {
        User userFromStorage = storageDao.get(user.getLogin());
        if (user.getLogin() != null && userFromStorage != null
                && user.getLogin().equals(userFromStorage.getLogin())) {
            return true;
        }
        return false;
    }
}

