package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int PASSWORD_MIN_LENGTH = 6;
    private static final int LOGIN_MIN_LENGTH = 6;
    private static final int MIN_AGE = 18;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidUserDataException("Provided User is null");
        }
        validateLogin(user.getLogin());
        validatePassword(user.getPassword());
        validateAge(user.getAge());
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidUserDataException(
                    "User with such login has been already registered. Login: "
                    + user.getLogin());
        }
        return storageDao.add(user);
    }

    private void validateLogin(String login) {
        if (login == null) {
            throw new InvalidUserDataException("Invalid User login was provided. Login can't be null");
        }
        if (login.length() < LOGIN_MIN_LENGTH) {
            throw new InvalidUserDataException("Invalid User login was provided: "
                    + login + ". Login can't be less than "
                    + LOGIN_MIN_LENGTH + " characters");
        }
    }

    private void validatePassword(String password) {
        if (password == null) {
            throw new InvalidUserDataException("Invalid User password was provided. Password can't be null");
        }
        if (password.length() < PASSWORD_MIN_LENGTH) {
            throw new InvalidUserDataException("Invalid User password was provided: "
                    + password + ". Password can't be less than "
                    + PASSWORD_MIN_LENGTH + " characters");
        }
    }

    private void validateAge(Integer age) {
        if (age == null) {
            throw new InvalidUserDataException("Invalid User age was provided. Age can't be null");
        }
        if (age < MIN_AGE) {
            throw new InvalidUserDataException("Invalid User age was provided: "
                    + age + ". Age can't be less then " + MIN_AGE);
        }
    }

}
