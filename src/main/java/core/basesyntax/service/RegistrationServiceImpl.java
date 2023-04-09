package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_USER_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();
    
    @Override
    public User register(User user) {
        checkDataForNull(user);
        checkLoginExists(user);
        checkLogin(user);
        checkPassword(user);
        checkAge(user);
        return storageDao.add(user);
    }
    
    private void checkAge(User user) {
        if (user.getAge() < MIN_USER_AGE) {
            throw new ValidationException("User must be over 18 years of age");
        }
    }
    
    private void checkPassword(User user) {
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new ValidationException(
                    "Password is too short, it must be more than 6 characters");
        }
    }
    
    private void checkLogin(User user) {
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new ValidationException("Login is too short, it must be more than 6 characters");
        }
    }
    
    private void checkLoginExists(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new ValidationException("User with this login exists try again");
        }
    }
    
    private void checkDataForNull(User user) {
        if (user.getLogin() == null
                || user.getPassword() == null
                || user.getAge() == null) {
            throw new ValidationException("Any user value cannot be null");
        }
    }
}
