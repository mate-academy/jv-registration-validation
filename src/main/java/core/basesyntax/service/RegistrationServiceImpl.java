package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationServiceException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkExistingLogins(user);
        checkLoginLength(user);
        checkPasswordLength(user);
        checkAge(user);
        return storageDao.add(user);
    }

    private void checkExistingLogins(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationServiceException("This login already exists");
        }
    }

    private void checkLoginLength(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationServiceException("Your login can not be null");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationServiceException("Your login length "
                    + "must be at least 6 characters");
        }
    }

    private void checkPasswordLength(User user) {
        if (user.getPassword() == null) {
            throw new RegistrationServiceException("Your password can not be null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationServiceException("Your password length "
                    + "must be at least 6 characters");
        }
    }

    private void checkAge(User user) {
        if (user.getAge() == null) {
            throw new RegistrationServiceException("Your can not be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationServiceException("Not valid age: " + user.getAge()
                    + ". Min allowed age is " + MIN_AGE);
        }
    }
}
