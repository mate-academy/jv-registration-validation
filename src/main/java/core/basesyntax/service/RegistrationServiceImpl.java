package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH_AUTHENTICATION_DATA = 6;
    private static final int MINIMAL_USER_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUser(user);
        Storage.people.add(user);
        if (!Storage.people.contains(user)) {
            throw new RegistrationException("Such a user does not exist or is not registered");
        }
        return storageDao.add(user);
    }

    private void validateUser(User user) {
        validateLogin(user.getLogin());
        validatePassword(user.getPassword());
        validateAge(user.getAge());
    }

    private void validateLogin(String login) {
        if (login == null || login.isEmpty()) {
            throw new RegistrationException("Incorrect login or password, please try again");
        }
        if (login.length() < MIN_LENGTH_AUTHENTICATION_DATA) {
            throw new RegistrationException("The length of the login cannot be less "
                    + "than the minimal");
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new RegistrationException("Incorrect login or password, please try again");
        }
        if (password.length() < MIN_LENGTH_AUTHENTICATION_DATA) {
            throw new RegistrationException("The length of the password cannot be less "
                    + "than the minimal");
        }
    }

    private void validateAge(Integer age) {
        if (age == null) {
            throw new RegistrationException("Incorrect login or password, please try again");
        }
        if (age < MINIMAL_USER_AGE) {
            throw new RegistrationException("The user's age is less "
                    + "than the minimal age allowed for registration");
        }
    }
}
