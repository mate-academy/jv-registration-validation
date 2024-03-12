package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationServiceException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH = 6;
    private static final int MIN_AGE = 18;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkLoginNotNull(user);
        checkLoginMinLength(user);
        checkPasswordNotNull(user);
        checkPassword(user);
        checkAgeNotNull(user);
        checkAge(user);
        return storageDao.add(user);
    }

    private void checkLoginMinLength(User user) {
        if (user.getLogin().length() < MIN_LENGTH) {
            throw new RegistrationServiceException(
                    "User login can not be least then "
                            + MIN_LENGTH
                            + " chars!"
            );
        }
        checkLoginDuplicate(user);
    }

    private void checkLoginNotNull(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationServiceException("Login can not be null!");
        }
    }

    private void checkLoginDuplicate(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationServiceException(
                    "User with login: "
                            + user.getLogin()
                            + " has already registered!"
            );
        }
    }

    private void checkPasswordNotNull(User user) {
        if (user.getPassword() == null) {
            throw new RegistrationServiceException("Password can not be null!");
        }
    }

    private void checkPassword(User user) {
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new RegistrationServiceException(
                    "User password can not be least then "
                            + MIN_LENGTH
                            + " chars!"
            );
        }
    }

    private void checkAgeNotNull(User user) {
        if (user.getAge() == null) {
            throw new RegistrationServiceException("Age can not be null!");
        }
    }

    private void checkAge(User user) {
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationServiceException("User must be over " + MIN_AGE + " years old");
        }
    }
}
