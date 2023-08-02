package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGR = 18;
    private static final int MIN_SIZE = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    private void isAgeValid(User user) {
        if (user.getAge() == null) {
            throw new NullPointerException("Age can`t be null");
        }

        if (user.getAge() < MIN_AGR) {
            throw new RegistrationException("Not valid age " + user.getAge()
                    + " Min allowed age is " + MIN_AGR);
        }
    }

    private void isLoginValid(User user) {
        if (user.getLogin() == null) {
            throw new NullPointerException("Login can`t be null");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with login " + user.getLogin()
                    + " exists in storage");
        }
        if (user.getLogin().length() < MIN_SIZE) {
            throw new RegistrationException("Not valid login " + user.getLogin()
                    + " Min large login is " + MIN_SIZE);
        }
    }

    private void isPasswordValid(User user) {
        if (user.getPassword() == null) {
            throw new NullPointerException("Password can`t be null");
        }

        if (user.getPassword().length() < MIN_SIZE) {
            throw new RegistrationException("Not valid password. "
                    + "Password length min is " + MIN_SIZE);
        }
    }

    @Override
    public User register(User user) {
        isAgeValid(user);
        isLoginValid(user);
        isPasswordValid(user);
        storageDao.add(user);
        return user;
    }
}
