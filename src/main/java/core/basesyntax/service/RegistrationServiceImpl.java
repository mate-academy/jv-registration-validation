package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidUserCredentialsException;
import core.basesyntax.exception.InvalidUserObjectException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();
    private static final int REQUIRED_AGE = 18;
    private static final int MINIMAL_PASSWORD_LENGTH = 6;
    private static final int MINIMAL_EMAIL_LENGTH = 6;

    @Override
    public User register(User user) {
        if (validateUserCredentials(user)) {
            return storageDao.add(user);
        }
        return null;
    }

    public boolean validateUserCredentials(User user) {
        if (user == null) {
            throw new InvalidUserObjectException("User object cannot be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidUserObjectException("User already exists");
        }
        if (user.getAge() <= 0) {
            throw new InvalidUserCredentialsException("User age cannot be negative or zero");
        }
        if (user.getAge() < 18) {
            throw new InvalidUserCredentialsException("User age cannot be less than 18");
        }
        validateUserEmail(user);
        validateUserPassword(user);
        return true;
    }

    public void validateUserEmail(User user) {
        if (user.getLogin() == null) {
            throw new InvalidUserCredentialsException("User login cannot be null");
        }
        if (user.getLogin().length() < 6) {
            throw new InvalidUserCredentialsException("User login length cannot be less than 6");
        }
    }

    public void validateUserPassword(User user) {
        if (user.getPassword() == null) {
            throw new InvalidUserCredentialsException("User password cannot be null");
        }
        if (user.getPassword().length() < 6) {
            throw new InvalidUserCredentialsException("User password length cannot be less than 6");
        }
    }

}
