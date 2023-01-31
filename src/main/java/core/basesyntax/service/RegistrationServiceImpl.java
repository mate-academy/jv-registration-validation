package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUser(user);
        validateAge(user);
        validatePassword(user);
        validateUniqueUser(user);
        return user;
    }

    private void validateUser(User user) {
        if (user == null) {
            throw new RuntimeException("User can not be null");
        } else if (user.getLogin() == null) {
            throw new RuntimeException("Login can not be null");
        } else if (user.getPassword() == null) {
            throw new RuntimeException("Password can not be null");
        } else if (user.getAge() == null) {
            throw new RuntimeException("User's age can not be null");
        }
    }

    private void validateAge(User user) {
        if (user.getAge() < 0) {
            throw new RuntimeException("User's age can not be negative");
        } else if (user.getAge() == 0) {
            throw new RuntimeException("User's age can not be zero");
        } else if (user.getAge() > 0 && user.getAge() < MIN_AGE) {
            throw new RuntimeException("User should be adult");
        }
    }

    private void validatePassword(User user) {
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new RuntimeException("Password should be six and more characters");
        }
    }

    private void validateUniqueUser(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("This user login is already exist");
        }
    }
}
