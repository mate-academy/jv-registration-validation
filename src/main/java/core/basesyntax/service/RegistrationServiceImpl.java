package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkLogin(user);
        checkPassword(user);
        checkAge(user);
        return storageDao.add(user);
    }

    public void checkLogin(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can`t be null");
        }
        if (user.getLogin().isBlank() || user.getLogin().length() < MIN_LENGTH) {
            throw new RegistrationException("Login must have " + MIN_LENGTH + "or more symbols");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("Login is almost exit");
        }
    }

    public void checkPassword(User user) {
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can`t be null");
        }
        if (user.getPassword().isBlank() || user.getPassword().length() < MIN_LENGTH) {
            throw new RegistrationException("Password must have " + MIN_LENGTH + "or more symbols");
        }
    }

    public void checkAge(User user) {
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Age can`t be less than " + MIN_AGE);
        }
    }
}
