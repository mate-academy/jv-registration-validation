package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MIN_AGE = 18;
    public static final int MIN_CHARACTERS = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUser(user);
        validateLogin(user.getLogin());
        validatePassword(user.getPassword());
        validateAge(user.getAge());

        return storageDao.add(user);
    }

    private void validateUser(User user) {
        if (user == null) {
            throw new RegistrationException("Error, user is null");
        }
    }

    private void validateLogin(String login) {
        if (login == null) {
            throw new RegistrationException("Error, user`s login is null`");
        }
        if (storageDao.get(login) != null) {
            throw new RegistrationException("Error, user exists with such login "
                    + login);
        }
        if (login.length() < MIN_CHARACTERS) {
            throw new RegistrationException("Error, 6 is min length, login length is "
                    + login.length());
        }
    }

    private void validatePassword(String password) {
        if (password == null) {
            throw new RegistrationException("Error, user`s password is null`");
        }
        if (password.length() < MIN_CHARACTERS) {
            throw new RegistrationException("Error, 6 is min length, password length is "
                    + password.length());
        }
    }

    private void validateAge(Integer age) {
        if (age == null) {
            throw new RegistrationException("Error, user`s age is null`");
        }
        if (age < MIN_AGE) {
            throw new RegistrationException("Error, 18 is min age, your age is " + age);
        }
    }
}
