package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMAL_LOGIN_LENGTH = 6;
    private static final int MINIMAL_PASSWORD_LENGTH = 6;
    private static final int MINIMAL_USER_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUser(user);
        return storageDao.add(user);
    }

    private void validateUser(User user) {
        if (Storage.people.contains(user)) {
            throw new RegistrationException("Such a user already registered");
        }
        validateLogin(user.getLogin());
        validatePassword(user.getPassword());
        validateAge(user.getAge());
    }

    private void validateLogin(String login) {
        if (login == null || login.isEmpty()) {
            throw new RegistrationException("Incorrect login or password, please try again");
        }
        if (login.length() < MINIMAL_LOGIN_LENGTH) {
            throw new RegistrationException("The length of the login cannot be less "
                    + "than the minimal: " + MINIMAL_LOGIN_LENGTH);
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new RegistrationException("Incorrect login or password, please try again");
        }
        if (password.length() < MINIMAL_PASSWORD_LENGTH) {
            throw new RegistrationException("The length of the password cannot be less "
                    + "than the minimal: " + MINIMAL_PASSWORD_LENGTH);
        }
    }

    private void validateAge(Integer age) {
        if (age == null) {
            throw new RegistrationException("Incorrect user's age, please try again");
        }
        if (age < MINIMAL_USER_AGE) {
            throw new RegistrationException("The user's age is less "
                    + "than the minimal allowed for registration: " + MINIMAL_USER_AGE);
        }
    }
}
