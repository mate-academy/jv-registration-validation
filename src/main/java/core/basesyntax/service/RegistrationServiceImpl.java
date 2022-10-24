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
        checkForNull(user);
        checkForAge(user);
        passwordCheck(user);
        reintroductionCheck(user);
        return storageDao.add(user);
    }

    private User checkForNull(User user) {
        if (user == null) {
            throw new RuntimeException("User can't be null.");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("Password can't be null");
        }
        return user;
    }

    private User checkForAge(User user) {
        if (user.getAge() < 0) {
            throw new RuntimeException("Not valid age.");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("User is too young.");
        }
        return user;
    }

    private User reintroductionCheck(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("This user is already registered.");
        }
        return user;
    }

    private User passwordCheck(User user) {
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Password is not valid.");
        }
        return user;
    }
}
