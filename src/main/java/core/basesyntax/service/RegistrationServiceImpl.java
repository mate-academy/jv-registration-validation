package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        nullChecks(user);
        fieldsLengthChecks(user);
        minAgeCheck(user);
        existenceCheck(user);
        return storageDao.add(user);
    }

    private void nullChecks(User user) {
        if (user == null) {
            throw new RegistrationException("User can't be null");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null.");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age can't be null.");
        }
    }

    private void fieldsLengthChecks(User user) {
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("Invalid login. Login should "
                    + "be at least 6 characters long.");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Invalid password. Password should be"
                    + " at least 6 characters long.");
        }
    }

    private void minAgeCheck(User user) {
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Invalid age. Age should be at least 18.");
        }
    }

    private void existenceCheck(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with the same login already exists.");
        }
    }
}

