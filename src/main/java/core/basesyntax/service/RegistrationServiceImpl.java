package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_SIZE = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validDaraAge(user);
        validDataLogin(user);
        isPasswordValid(user);
        return storageDao.add(user);
    }

    private void validDaraAge(User user) {
        if (user.getAge() == null) {
            throw new RegistrationException("Age can`t be null");
        }

        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Not valid age " + user.getAge()
                    + " Min allowed age is " + MIN_AGE);
        }
    }

    private void validDataLogin(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can`t be null");
        }

        if (user.getLogin().length() < MIN_SIZE) {
            throw new RegistrationException("Not valid login " + user.getLogin()
                    + " Min large login is " + MIN_SIZE);
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with login " + user.getLogin()
                    + " exists in storage");
        }
    }

    private void isPasswordValid(User user) {
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can`t be null");
        }

        if (user.getPassword().length() < MIN_SIZE) {
            throw new RegistrationException("Not valid password. "
                    + "Password length min is " + MIN_SIZE);
        }
    }
}
