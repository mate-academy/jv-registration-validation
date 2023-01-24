package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkUser(user);
        checkLogin(user);
        checkPassword(user);
        checkAge(user);
        return storageDao.add(user);
    }

    private void checkUser(User user) {
        if (user == null) {
            throw new RegistrationException("User can't be null!");
        }
    }

    private void checkLogin(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null!");
        }
        if (user.getLogin().isEmpty()) {
            throw new RegistrationException("Login can't be empty!");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("Login " + user.getLogin() + " are exist!");
        }
    }

    private void checkPassword(User user) {
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null!");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Password must be at least "
                    + MIN_PASSWORD_LENGTH + " characters length!");
        }
    }

    private void checkAge(User user) {
        if (user.getAge() == null) {
            throw new RegistrationException("Age can't be null!");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Age must be at least " + MIN_AGE + "!");
        }
    }
}
