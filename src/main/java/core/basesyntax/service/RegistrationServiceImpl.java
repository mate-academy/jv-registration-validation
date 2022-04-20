package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {

        nullCheck(user);
        checkingAge(user);
        checkingPasswordLength(user);
        checkingInStorage(user);
        storageDao.add(user);
        return user;
    }

    private User nullCheck(User user) {
        if (user == null || user.getLogin() == null || user.getPassword() == null) {
            throw new NullPointerException("Enter correct registration info");
        }
        return user;
    }

    private void checkingInStorage(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User was created");
        }
    }

    private void checkingAge(User user) {
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("User must be greater than 18 years old");
        }
    }

    private void checkingPasswordLength(User user) {
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Password is incorrect");
        }
    }
}
