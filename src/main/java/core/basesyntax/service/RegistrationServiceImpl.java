package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASS_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkUserNullFields(user);
        checkInvalidAge(user);
        checkUserExist(user);
        checkValidPass(user);
        storageDao.add(user);
        return user;
    }

    private boolean checkValidPass(User user) {
        if (user.getPassword().length() < MIN_PASS_LENGTH) {
            throw new RuntimeException("User password must be at least 6 characters.");
        }
        return true;
    }

    private boolean checkUserExist(User user) {
        if (isUserExist(user)) {
            throw new RuntimeException("User with login:" + user.getLogin() + " is already exist.");
        }
        return true;
    }

    private boolean checkInvalidAge(User user) {
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("You can't register a user under the age of 18!");
        }
        return true;
    }

    private boolean checkUserNullFields(User user) {
        if (user.getLogin() == null || user.getPassword() == null || user.getAge() == null) {
            throw new RuntimeException("You can't register a user with any null parameters!");
        }
        return true;
    }

    private boolean isUserExist(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            return true;
        }
        return false;
    }
}
