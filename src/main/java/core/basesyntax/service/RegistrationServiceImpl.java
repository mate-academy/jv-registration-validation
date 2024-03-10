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
        checkValidLogin(user);
        checkPassword(user);
        checkAge(user);
        return storageDao.add(user);
    }

    private void checkValidLogin(User user) {
        if (user.getLogin() == null
                || user.getLogin().length() < MIN_LENGTH) {
            throw new RegistrationServiceException(
                    "User login can not be least then "
                            + MIN_LENGTH
                            + " chars!"
            );
        } else {
            checkEmailDuplicate(user);
        }
    }

    private void checkEmailDuplicate(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationServiceException(
                    "User with login: "
                            + user.getLogin()
                            + " has already registered!"
            );
        }
    }

    private void checkPassword(User user) {
        if (user.getPassword() == null
                || user.getPassword().length() < MIN_LENGTH) {
            throw new RegistrationServiceException(
                    "User password can not be least then "
                            + MIN_LENGTH
                            + " chars!"
            );
        }
    }

    private void checkAge(User user) {
        if (user.getAge() == null || user.getAge() < MIN_AGE) {
            throw new RegistrationServiceException("User must be of legal age!");
        }
    }
}
