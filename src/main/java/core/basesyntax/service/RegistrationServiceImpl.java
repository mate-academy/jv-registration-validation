package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkForNull(user);
        checkForValidLength(user);
        checkForValidAge(user);
        checkIfUserAlreadyExist(user);
        return storageDao.add(user);
    }

    private void checkIfUserAlreadyExist(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with login "
                    + user.getLogin() + " already exist");
        }
    }

    private static void checkForValidAge(User user) {
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Not valid age: "
                    + user.getAge() + ". Min allowed age is " + MIN_AGE);
        }
    }

    private static void checkForValidLength(User user) {
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new RegistrationException("Password length is less than " + MIN_LENGTH);
        }
        if (user.getLogin().length() < MIN_LENGTH) {
            throw new RegistrationException("Login length is less than " + MIN_LENGTH);
        }
    }

    private static void checkForNull(User user) {
        if (user == null) {
            throw new RegistrationException("User can't be null");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("User age can't be null");
        }
    }
}
