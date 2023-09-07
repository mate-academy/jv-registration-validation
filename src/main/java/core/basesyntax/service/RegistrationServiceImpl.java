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
        if (user == null) {
            throw new RegistrationException("The user doesn't exist");
        }
        validateLogin(user.getLogin());
        validatePassword(user.getPassword());
        validateAge(user.getAge());
        return storageDao.add(user);
    }

    private void validateLogin(String login) {
        if (login == null) {
            throw new RegistrationException("Login can't be null.");
        }
        if (login.length() < MIN_LENGTH) {
            throw new RegistrationException(String
                    .format("Your login should contain %s MIN_LENGTH or more symbols.",
                            MIN_LENGTH));
        }
        if (storageDao.get(login) != null) {
            throw new RegistrationException(String.format("User with login %s already exists.",
                    login));
        }
    }

    private void validatePassword(String password) {
        if (password == null) {
            throw new RegistrationException("Your password can't be null.");
        }
        if (password.length() < MIN_LENGTH) {
            throw new RegistrationException(String
                    .format("Your password should contain %s MIN_LENGTH or more symbols.",
                            MIN_LENGTH));
        }
    }

    private void validateAge(Integer age) {
        if (age == null) {
            throw new RegistrationException("Your age can't be null");
        }
        if (age < MIN_AGE) {
            throw new RegistrationException(String.format("You age should be at least %d y.o.",
                    MIN_AGE));
        }
    }
}
