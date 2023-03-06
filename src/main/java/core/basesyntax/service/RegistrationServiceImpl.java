package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int NULL_AGE = 0;
    private static final int MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkForNulls(user);
        checkLogin(user);
        checkPassword(user);
        checkAge(user);
        return storageDao.add(user);
    }

    private void checkForNulls(User user) {
        if (user == null || user.getLogin() == null || user.getPassword() == null
                || user.getAge() == null) {
            throw new RegistrationException("User params can't be null!!!");
        }
    }

    private void checkLogin(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with same login already exists!!!");
        }
    }

    private void checkPassword(User user) {
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new RegistrationException("Password should has more than 6 characters");
        }
    }

    private void checkAge(User user) {
        if (user.getAge() < NULL_AGE) {
            throw new RegistrationException("It is impossible to have an age less than 0");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("You must be over 18 to register");
        }
    }
}

