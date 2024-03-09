package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("Error, user can not be null");
        }
        validateLogin(user.getLogin());
        validatePassword(user.getPassword());
        validateAge(user.getAge());
        return storageDao.add(user);
    }

    private boolean validateLogin(String login) {
        if (login == null || login.length() < MIN_LENGTH) {
            throw new RegistrationException("Login should be at least " + MIN_LENGTH);
        }
        if (storageDao.get(login) != null) {
            throw new RegistrationException("This login has already taken by another user");
        }
        return true;
    }

    private boolean validateAge(Integer age) {
        if (age < MIN_AGE) {
            throw new RegistrationException("You should be at least " + MIN_AGE);
        }
        return true;
    }

    private boolean validatePassword(String password) {
        if (password == null || password.length() < MIN_LENGTH) {
            throw new RegistrationException("Password should be at least " + MIN_LENGTH);
        }
        return true;
    }
}
