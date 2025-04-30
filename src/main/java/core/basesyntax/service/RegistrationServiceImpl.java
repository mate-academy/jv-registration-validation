package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_LOGIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUser(user);
        validateAge(user);
        validateLogin(user);
        validatePassword(user);
        return storageDao.add(user);
    }

    private void validateLogin(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("Please enter your login");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("This login is already use: " + user.getLogin());
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("User's login must be at least " + MIN_LOGIN_LENGTH
                    + " character long, but was " + user.getLogin().length());
        }
    }

    private void validatePassword(User user) {
        if (user.getPassword() == null) {
            throw new RegistrationException("Please enter your password");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("User's password must be at least "
                    + MIN_PASSWORD_LENGTH + " character long, but was " + user.getLogin().length());
        }
    }

    private void validateAge(User user) {
        if (user.getAge() == null) {
            throw new RegistrationException("Please enter your age");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("User's age must be minimum " + MIN_AGE
                    + " but was " + user.getAge());
        }
    }

    private void validateUser(User user) {
        if (user == null) {
            throw new RegistrationException("User can not be null");
        }
    }
}
