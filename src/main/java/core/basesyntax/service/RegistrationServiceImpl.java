package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {

    private static final int MIN_PASS_LENGTH = 6;
    private static final int MIN_USER_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidDataException("User can`t be null");
        }
        if (user.equals(storageDao.get(user.getLogin()))) {
            throw new InvalidDataException("User already exist");
        }
        checkAge(user.getAge());
        checkPassword(user.getPassword());
        checkLogin(user.getLogin());
        storageDao.add(user);
        return user;
    }

    private void checkAge(Integer age) {
        if (age == null) {
            throw new InvalidDataException("Age can`t be null");
        }
        if (age < 0) {
            throw new InvalidDataException("Age can`t have negative value");
        }
        if (age < MIN_USER_AGE) {
            throw new InvalidDataException("User must be older 18");
        }
    }

    private void checkPassword(String password) {
        if (password == null) {
            throw new InvalidDataException("Password can`t be null");
        }
        if (password.length() < MIN_PASS_LENGTH) {
            throw new InvalidDataException("Password length must be 6 or longer");
        }
    }

    private void checkLogin(String login) {
        if (login == null) {
            throw new InvalidDataException("Login can`t be null");
        }
        if (storageDao.get(login) != null) {
            throw new InvalidDataException("User with login " + login + " already exist");
        }
    }
}
