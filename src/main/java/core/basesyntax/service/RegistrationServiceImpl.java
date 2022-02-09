package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {

    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH = 6;
    private StorageDao storageDao;

    @Override
    public User register(User user) {
        storageDao = new StorageDaoImpl();
        checkAge(user);
        checkLogin(user);
        checkPassword(user);
        storageDao.add(user);
        return user;
    }

    private boolean checkAge(User user) {
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("Can`t register user, younger than 18 y.o.");
        }
        if (user.getAge() == null) {
            throw new RuntimeException("Can`t register user with no age");
        }
        return true;
    }

    private boolean checkPassword(User user) {
        if (user.getPassword() == null
                || user.getPassword().length() < MIN_LENGTH) {
            throw new RuntimeException("Wrong password. It must contain at least 6 characters");
        }
        return true;
    }

    private boolean checkLogin(User user) {
        storageDao = new StorageDaoImpl();
        if (user.getLogin() == null) {
            throw new RuntimeException("User with no login can't be registered");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Create new login. User with login "
                        + user.getLogin() + " already exists.");
        }

        return true;
    }
}
