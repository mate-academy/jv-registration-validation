package core.basesyntax.service;

import core.basesyntax.Exception.RegistrationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storage = new StorageDaoImpl();
    private final static int MIN_USERS_AGE = 18;
    private final static int MIN_LOGIN_LENGTH = 6;
    private final static int MIN_PASSWORD_LENGTH = 6;

    @Override
    public User register(User user) throws RegistrationException {
        CheckLoginUser(user.getLogin());
        CheckPasswordUser(user.getPassword());
        CheckAgeUser(user.getAge());
        if (storage.get(user.getLogin()) != null) {
            throw new RegistrationException("The user with login " + storage.get(user.getLogin()).getLogin() + " already exists in storage!");
        }
        storage.add(user);
        return storage.get(user.getLogin());
    }

    private void CheckLoginUser(String login) throws RegistrationException {
        if (login == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (login.length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("Login can't be less than" + MIN_LOGIN_LENGTH + " symbols");
        }
    }

    private void CheckPasswordUser(String password) throws RegistrationException {
        if (password == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Password can't be less than" + MIN_PASSWORD_LENGTH + " symbols");
        }
    }

    private void CheckAgeUser(int age) throws RegistrationException {
        if (age == 0) {
            throw new RegistrationException("Age can't be 0");
        }
        if (age < 0) {
            throw new RegistrationException("Age can't be negative");
        }
        if (age < MIN_USERS_AGE) {
            throw new RegistrationException("Not valid age: " + age + ". Min allowed age is " + MIN_USERS_AGE);
        }
    }
}
