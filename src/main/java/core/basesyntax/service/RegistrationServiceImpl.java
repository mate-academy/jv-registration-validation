package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.UserRegisterException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {

    public static final int MIN_LENGTH_PASSWORD_PARAMETER = 6;
    public static final int MIN_LENGTH_LOGIN_PARAMETER = 6;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkUserExist(user);
        checkUserLoginLength(user);
        checkUserPassLength(user);
        checkUserAge(user);
        return storageDao.add(user);
    }

    private void checkUserExist(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new UserRegisterException(
                "User with login " + user.getLogin() + " already exists!");
        }
    }

    private void checkUserLoginLength(User user) {
        if (user.getLogin() != null && user.getLogin().length() < MIN_LENGTH_LOGIN_PARAMETER) {
            throw new UserRegisterException(
                "User login should be at least " + MIN_LENGTH_LOGIN_PARAMETER
                    + " characters long!");
        }
    }

    private void checkUserPassLength(User user) {
        if (user.getPassword() != null
                && user.getPassword().length() < MIN_LENGTH_PASSWORD_PARAMETER) {
            throw new UserRegisterException(
                "User password should be at least " + MIN_LENGTH_PASSWORD_PARAMETER
                    + " characters long!");
        }
    }

    private void checkUserAge(User user) {
        if (user.getAge() != null && user.getAge() < 18) {
            throw new UserRegisterException("User should be at least 18 years old!");
        }
    }
}
