package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASS_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUser(user);
        return storageDao.add(user);
    }

    private void validateUser(User user) {
        validateNotNull(user, "User cannot be null");
        validateNotEmptyUser(user, "Fill in data fields for empty user");
        validateNotNullOrEmpty(user.getLogin(), "Login cannot be null");
        validateLoginLength(user.getLogin(),
                "Login must be at least " + MIN_LOGIN_LENGTH + " characters");
        validateNotNullOrEmpty(user.getPassword(), "Password cannot be null");
        validatePasswordLength(user.getPassword(),
                "Password must be at least " + MIN_PASS_LENGTH + " characters");
        validateAge(user.getAge(),
                "You must be at least " + MIN_AGE + " years old to register");
        validateExistingUser(user, "Current user already exists");
    }

    private void validateNotNull(User user, String exceptMessage) {
        if (user == null) {
            throw new RegistrationException(exceptMessage);
        }
    }

    private void validateNotEmptyUser(User user, String exceptMessage) {
        if (user.equals(new User())) {
            throw new RegistrationException(exceptMessage);
        }
    }

    private void validateNotNullOrEmpty(String value, String exceptMessage) {
        if (value == null) {
            throw new RegistrationException(exceptMessage);
        }

        if (value.isEmpty()) {
            throw new RegistrationException(exceptMessage);
        }
    }

    private void validateLoginLength(String login, String exceptMessage) {
        if (login.length() < RegistrationServiceImpl.MIN_LOGIN_LENGTH) {
            throw new RegistrationException(exceptMessage);
        }
    }

    private void validatePasswordLength(String password, String exceptMessage) {
        if (password.length() < RegistrationServiceImpl.MIN_PASS_LENGTH) {
            throw new RegistrationException(exceptMessage);
        }
    }

    private void validateAge(int ageActual, String exceptMessage) {
        if (ageActual < RegistrationServiceImpl.MIN_AGE) {
            throw new RegistrationException(exceptMessage);
        }
    }

    private void validateExistingUser(User user, String exceptMessage) {
        for (User storageUser : Storage.people) {
            if (storageUser.getLogin().equals(user.getLogin())) {
                throw new RegistrationException("User with current login already exists");
            }
        }
    }
}
