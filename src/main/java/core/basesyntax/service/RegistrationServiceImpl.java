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
        validateLogin(user);
        validatePassword(user);
        return storageDao.add(user);
    }

    private void validateUser(User user) {
        if (user == null) {
            throw new RuntimeException("User can't be null");
        }
    }

    private void validateAge(User user) {
        if (user.getAge() == null) {
            throw new RuntimeException("Age can't be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("ACCESS DENIED. User are not adult!");
        }
    }

    private void validateLogin(User user) {
        if (user.getLogin() == null) {
            throw new RuntimeException("Username can't be null!");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("This username isn't allowed!");
        }
    }

    private void validatePassword(User user) {
        if (user.getPassword() == null) {
            throw new RuntimeException("Pass can't be null");
        }
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new RuntimeException("Password have to contain at least "
                    + MIN_LENGTH + " characters!");
        }
    }
}
