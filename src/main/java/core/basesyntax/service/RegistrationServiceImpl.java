package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.UserRegisterException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {

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
            throw new UserRegisterException("User exists!");
        }
    }

    private void checkUserLoginLength(User user) {
        if (user.getLogin().length() < MIN_LENGTH_LOGIN_PARAMETER) {
            throw new UserRegisterException("Login is short!");
        }
    }

    private void checkUserPassLength(User user) {
        if (user.getPassword().length() < MIN_LENGTH_LOGIN_PARAMETER) {
            throw new UserRegisterException("Pass is short!");
        }
    }

    private void checkUserAge(User user) {
        if (user.getAge() < 18) {
            throw new UserRegisterException("Age is bellow 18!");
        }
    }
}
