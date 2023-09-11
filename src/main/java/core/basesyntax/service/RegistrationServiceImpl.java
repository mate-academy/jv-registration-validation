package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("This user doesn't exist: ");
        }
        validationOfLogin(user);
        validationOfPassword(user);
        validationOfAge(user);
        return storageDao.add(user);
    }

    private void validationOfLogin(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null: ");
        }
        if (user.getLogin().equals("")) {
            throw new RegistrationException("Login can't be empty line: ");
        }
        if (user.getLogin().length() < MIN_LENGTH) {
            throw new RegistrationException("Login length should be more than "
                    + MIN_LENGTH + ": ");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with this login already exist: ");
        }
    }

    private void validationOfPassword(User user) {
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null: ");
        }
        if (user.getPassword().equals("")) {
            throw new RegistrationException("Password can't be empty line: ");
        }
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new RegistrationException("Password length should be more than "
                    + MIN_LENGTH + ": ");
        }
    }

    private void validationOfAge(User user) {
        if (user.getAge() == null) {
            throw new RegistrationException("Age can not be null: ");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Your age should be more than " + MIN_AGE + ": ");
        }
    }
}
