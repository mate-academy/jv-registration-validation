package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_LOGIN_LENGTH = 6;
    private static final int MINIMUM_PASSWORD_LENGTH = 6;
    private static final int MINIMUM_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidDataException("Can't register null user!");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("Such a user is already registered!");
        }
        loginValidation(user.getLogin());
        passwordValidation(user.getPassword());
        ageValidation(user.getAge());
        return storageDao.add(user);
    }

    private void loginValidation(String login) {
        if (login == null) {
            throw new InvalidDataException("Login can't be null!");
        }
        if (login.length() < MINIMUM_LOGIN_LENGTH) {
            throw new InvalidDataException("Not valid user's login " + login
                    + ". Min allowed login length is " + MINIMUM_LOGIN_LENGTH);
        }
    }

    private void passwordValidation(String password) {
        if (password == null) {
            throw new InvalidDataException("Password can't be null!");
        }

        if (password.length() < MINIMUM_PASSWORD_LENGTH) {
            throw new InvalidDataException("Not valid user's password " + password
                    + ". Min allowed password length is " + MINIMUM_PASSWORD_LENGTH);
        }
    }

    private void ageValidation(int age) {
        if (age < MINIMUM_AGE) {
            throw new InvalidDataException("Not valid user's age " + age
                    + ". Min allowed age is " + MINIMUM_AGE);
        }
    }
}
