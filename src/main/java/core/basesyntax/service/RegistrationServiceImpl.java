package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_SYMBOLS_AMOUNT = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUser(user);
        validateUserLogin(user);
        validateUserAge(user);
        validateUserPassword(user);
        return storageDao.add(user);
    }

    public void validateUser(User user) {
        if (user == null) {
            throw new RuntimeException("User can not be null");
        }
    }

    public void validateUserLogin(User user) {
        if (user.getLogin() == null) {
            throw new RuntimeException("Login can not be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Such login already exist");
        }
    }

    public void validateUserAge(User user) {
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("User younger than 18 years old");
        }
    }

    public void validateUserPassword(User user) {
        if (user.getPassword() == null) {
            throw new RuntimeException("Password can not be null");
        }
        if (user.getPassword().length() < MIN_SYMBOLS_AMOUNT) {
            throw new RuntimeException("User password is less than 6 symbols");
        }
    }
}
