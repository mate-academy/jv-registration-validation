package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASSWORD_SIZE = 6;
    private static final int MIN_AGE = 18;
    private StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkPassword(user.getPassword());
        checkAge(user);
        checkLogin(user.getLogin());
        storageDao.add(user);
        return user;
    }

    private void checkPassword(String password) {
        if (password == null) {
            throw new RegistrationException("Password cant be null");
        }
        if (password.length() < MIN_PASSWORD_SIZE) {
            throw new RegistrationException("Password has to be at least "
                    + MIN_PASSWORD_SIZE + " characters");
        }
    }

    private void checkAge(User user) {
        if (user.getAge() == null) {
            throw new RegistrationException("Age cant be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Age cant be less then "
                    + MIN_AGE);
        }
    }

    private void checkLogin(String login) {
        if (login == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (storageDao.get(login) != null) {
            throw new RegistrationException("Login already exists");
        }
    }
}
