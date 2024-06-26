package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MIN_LOGIN_AND_PASWORD_LENGTH = 6;
    public static final int MIN_USER_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUser(user);
        validateLogin(user.getLogin());
        validatePassword(user.getPassword());
        validateAge(user.getAge());
        return storageDao.add(user);
    }

    private void validateUser(User user) {
        if (user == null) {
            throw new RegistrationValidationException("The user is not registered in the network");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationValidationException("User with this login already exists");
        }
    }

    private void validateLogin(String login) {
        if (login != null && login.length() < MIN_LOGIN_AND_PASWORD_LENGTH) {
            throw new RegistrationValidationException("Login must be at least 6 characters long");
        }
    }

    private void validatePassword(String password) {
        if (password.length() < MIN_LOGIN_AND_PASWORD_LENGTH) {
            throw new
                    RegistrationValidationException("Password must be at least 6 characters long");
        }
    }

    private void validateAge(int age) {
        if (age < MIN_USER_AGE) {
            throw new RegistrationValidationException("Age must be at least 18 years old");
        }
    }
}
