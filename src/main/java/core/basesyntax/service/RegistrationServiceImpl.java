package core.basesyntax.service;

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
            throw new NullPointerException("User cannot be null.");
        }
        validateLogin(user);
        validatePassword(user.getPassword());
        validateAge(user.getAge());

        storageDao.add(user);
        return user;
    }

    private void validateLogin(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("Login cannot be null.");
        }
        if (user.getLogin().length() < MINIMUM_LENGTH) {
            throw new RegistrationException("Login must be at least "
                    + MINIMUM_LENGTH + " characters long.");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with this login "
                    + user.getLogin() + " already exists.");
        }
    }

    private void validatePassword(String password) {
        if (password == null) {
            throw new RegistrationException("Password cannot be null.");
        }
        if (password.length() < MINIMUM_LENGTH) {
            throw new RegistrationException("Password must be at least "
                    + MINIMUM_LENGTH + " characters long.");
        }
    }

    private void validateAge(Integer age) {
        if (age == null) {
            throw new RegistrationException("Age cannot be null.");
        }
        if (age < MINIMUM_AGE) {
            throw new RegistrationException("User must be at least " + MINIMUM_AGE + " years old.");
        }
    }
}
