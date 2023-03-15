package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidInputDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        loginCheck(user.getLogin());
        passwordCheck(user.getPassword());
        ageCheck(user.getAge());
        storageDao.add(user);
        return user;
    }

    private void loginCheck(String login) {
        if (login == null) {
            throw new InvalidInputDataException("You need to write your login");
        }
        if (storageDao.get(login) != null) {
            throw new InvalidInputDataException("User with login: " + login + " already exists");
        }
    }

    private void passwordCheck(String password) {
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidInputDataException("Oops, your password is too short, try to add "
                    + (MIN_PASSWORD_LENGTH - password.length()) + " symbol/s");
        }
        if (password == null) {
            throw new InvalidInputDataException("You need to write password, it must be "
                    + MIN_PASSWORD_LENGTH + " or more characters");
        }
    }

    private void ageCheck(Integer age) {
        if (age == null) {
            throw new InvalidInputDataException("Write your real age");
        }
        if (age < MIN_AGE) {
            throw new InvalidInputDataException("Come back when you are " + MIN_AGE);
        }
    }

}

