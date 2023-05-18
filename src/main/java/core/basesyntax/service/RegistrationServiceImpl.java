package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGHT = 6;
    private static final int MIN_PASSWORD_LENGHT = 6;
    private static final int USER_MIN_AGE = 18;
    private StorageDao storageDao;

    public RegistrationServiceImpl() {
        storageDao = new StorageDaoImpl();
    }

    @Override
    public User register(User user) {
        checkNullUser(user);
        validateLogin(user);
        validatePassword(user);
        validateAge(user);
        return storageDao.add(user);
    }

    private void checkNullUser(User user) {
        if (user == null) {
            throw new RegistrationException("User cannot be null");
        }
    }

    private void validateLogin(User user) {
        if (user.getLogin() == null || user.getLogin().length() < MIN_LOGIN_LENGHT) {
            throw new RegistrationException("Invalid login. "
                    + "User login must have at least 6 characters");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with the specified login is already exist ");
        }
    }

    private void validatePassword(User user) {
        if (user.getPassword() == null || user.getPassword().length() < MIN_PASSWORD_LENGHT) {
            throw new RegistrationException("Invalid password. "
                    + "User password must have at least 6 characters");
        }
    }

    private void validateAge(User user) {
        if (user.getAge() == null || user.getAge() < USER_MIN_AGE) {
            throw new RegistrationException("Registration is available only from the age of 18");
        }
    }
}
