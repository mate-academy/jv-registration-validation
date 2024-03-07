package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkAge(user);
        checkLogin(user);
        checkPassword(user);
        checkDuplicate(user);
        return storageDao.add(user);
    }

    private void checkDuplicate(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with email= "
                    + user.getLogin()
                    + " already exists"
            );
        }
    }

    private void checkLogin(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("Not valid login length: "
                    + user.getLogin()
                    + ". Min allowed age is "
                    + MIN_LOGIN_LENGTH
            );
        }
    }

    private void checkAge(User user) {
        if (user.getAge() == null) {
            throw new RegistrationException("Age can't be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException(
                    "Not valid age: " + user.getAge()
                    + ". Min allowed age is " + MIN_AGE
            );
        }
    }

    private void checkPassword(User user) {
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException(
                    "Not valid password length,"
                    + " allowed min password length is "
                    + MIN_PASSWORD_LENGTH
            );
        }
    }
}
