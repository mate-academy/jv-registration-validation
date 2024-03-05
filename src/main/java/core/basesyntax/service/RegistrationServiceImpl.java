package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws RegistrationException {
        try {
            loginNullCheck(user.getLogin());
            passwordNullCheck(user.getPassword());
            dublicateCheck(user);
            loginLengthCheck(user.getLogin());
            passwordLegthCheck(user.getPassword());
            ageCheck(user.getAge());
        } catch (RegistrationException e) {
            throw e;
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        storageDao.add(user);
        return user;
    }

    public void dublicateCheck(User user) throws RegistrationException {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException(
                    "User with login: " + user.getLogin() + " already exist.");
        }
    }

    public void loginNullCheck(String login) throws RegistrationException {
        if (login == null) {
            throw new RuntimeException("Login couldn't be null");
        }
    }

    public void loginLengthCheck(String login) throws RegistrationException {
        if (login.length() < 6) {
            throw new RegistrationException("Length of " + login + "less than 6 characters.");
        }
    }

    public void passwordNullCheck(String password) throws RegistrationException {
        if (password == null) {
            throw new RuntimeException("Login couldn't be null");
        }
    }

    public void passwordLegthCheck(String password) throws RegistrationException {
        if (password.length() < 6) {
            throw new RegistrationException("Length of " + password + "less than 6 characters.");
        }
    }

    private void ageCheck(int age) throws RegistrationException {
        if (age < 18) {
            throw new RegistrationException("Age should be at least 18, but was: " + age);
        }
    }
}
