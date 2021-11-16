package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE_USER = 18;
    private static final int MINIMUM_LENGTH_PASSWORD = 6;
    private StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationUserException("User is null");
        }
        checkLogin(user.getLogin());
        checkPassword(user.getPassword());
        if (user.getAge() < MIN_AGE_USER) {
            throw new RegistrationUserException("User age less than " + MIN_AGE_USER);
        }
        return storageDao.add(user);
    }

    private void checkLogin(String login) {
        if (login == null) {
            throw new RegistrationUserException("Login is null");
        }
        if (login.length() < 1) {
            throw new RegistrationUserException("Login is not valid, length is less than 1");
        }
        StorageDaoImpl storage = new StorageDaoImpl();
        if (storage.get(login) != null) {
            throw new RegistrationUserException("Such a login is contained in the storage");
        }
    }

    private void checkPassword(String password) {
        if (password == null) {
            throw new RegistrationUserException("Password is null");
        }
        if (password.length() <= MINIMUM_LENGTH_PASSWORD) {
            throw new RegistrationUserException("Login is not valid, length is less than "
                    + MINIMUM_LENGTH_PASSWORD);
        }
    }
}
