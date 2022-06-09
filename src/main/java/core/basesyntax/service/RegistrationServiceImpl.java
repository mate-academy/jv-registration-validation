package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkUser(user);
        return storageDao.add(user);
    }

    private void checkUser(User user) {
        if (user == null) {
            throw new RuntimeException("Null input parameter at user argument");
        }
        checkUserPassword(user);
        checkUserAge(user);
        checkUserLogin(user);
    }

    private void checkUserLogin(User user) {
        if (user.getLogin() == null || user.getLogin().length() == 0) {
            throw new RuntimeException("Can`t register, invalid login");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Can`t register, login is exist");
        }
    }

    private void checkUserPassword(User user) {
        if (user.getPassword() == null || user.getPassword().length()
                < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Can`t register, password must be longer");
        }
    }

    private void checkUserAge(User user) {
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("Can`t register, not enough years");
        }
    }
}
