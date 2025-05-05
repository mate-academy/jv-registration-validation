package core.basesyntax.service;

import core.basesyntax.RegistrationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_AGE = 18;
    private static final int MINIMUM_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User can't be null!");
        }
        checkLogin(user.getLogin());
        checkPassword(user.getPassword());
        checkAge(user.getAge());
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User already exists!");
        }
        return storageDao.add(user);
    }

    private void checkPassword(String password) {
        if (password == null) {
            throw new RegistrationException("Password can't be null!");
        }
        if (password.length() < MINIMUM_LENGTH) {
            throw new RegistrationException("Password must be at least "
                    + MINIMUM_LENGTH
                    + " characters long!");
        }
    }

    private void checkLogin(String login) {
        if (login == null) {
            throw new RegistrationException("Login can't be null!");
        }
        if (login.length() < MINIMUM_LENGTH) {
            throw new RegistrationException("Login must be at least "
                    + MINIMUM_LENGTH
                    + "characters long!");
        }
    }

    private void checkAge(Integer age) {
        if (age == null) {
            throw new RegistrationException("Age can't be null!");
        }
        if (age < MINIMUM_AGE) {
            throw new RegistrationException("User must be at least "
                    + MINIMUM_AGE
                    + " years old!");
        }
    }
}
