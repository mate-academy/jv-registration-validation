package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        loginNullCheck(user.getLogin());
        passwordNullCheck(user.getPassword());
        dublicateCheck(user);
        loginLengthCheck(user.getLogin());
        passwordLegthCheck(user.getPassword());
        ageCheck(user.getAge());
        storageDao.add(user);
        return user;
    }

    public void dublicateCheck(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException(
                    "User with login: " + user.getLogin() + " already exist.");
        }
    }

    public void loginNullCheck(String login) {
        if (login == null) {
            throw new RegistrationException("Login couldn't be null");
        }
    }

    public void loginLengthCheck(String login) {
        if (login.length() < 6) {
            throw new RegistrationException("Length of " + login + "less than 6 characters.");
        }
    }

    public void passwordNullCheck(String password) {
        if (password == null) {
            throw new RegistrationException("Login couldn't be null");
        }
    }

    public void passwordLegthCheck(String password) {
        if (password.length() < 6) {
            throw new RegistrationException("Length of " + password + "less than 6 characters.");
        }
    }

    private void ageCheck(int age) {
        if (age < 18) {
            throw new RegistrationException("Age should be at least 18, but was: " + age);
        }
    }
}
