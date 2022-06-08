package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkUserNotNull(user);
        checkLoginNotNull(user);
        checkAgeNotNull(user);
        checkPasswordNotNull(user);
        checkUserDoesNotAlreadyExist(user);
        checkAgeValid(user);
        checkPasswordValid(user);
        return storageDao.add(user);
    }

    private void checkUserNotNull(User user) {
        if (user == null) {
            throw new RuntimeException("Cannot register user. User is null.");
        }
    }

    private void checkLoginNotNull(User user) {
        if (user.getLogin() == null) {
            throw new RuntimeException("Cannot register user. User login is null.");
        }
    }

    private void checkAgeNotNull(User user) {
        if (user.getAge() == null) {
            throw new RuntimeException("Cannot register user. User age is null.");
        }
    }

    private void checkPasswordNotNull(User user) {
        if (user.getPassword() == null) {
            throw new RuntimeException("Cannot register user. User password is null.");
        }
    }

    private void checkUserDoesNotAlreadyExist(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Cannot register user. User"
                    + user.getLogin() + " already exists.");
        }
    }

    private void checkPasswordValid(User user) {
        if (user.getAge() < 18) {
            throw new RuntimeException("Cannot register user. Age is less than 18.");
        }
    }

    private void checkAgeValid(User user) {
        if (user.getPassword().length() < 6) {
            throw new RuntimeException("Cannot register user. "
                    + "Password should be at least 6 symbols.");
        }
    }
}
