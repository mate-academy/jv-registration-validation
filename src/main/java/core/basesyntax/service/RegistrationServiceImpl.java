package core.basesyntax.service;

import core.basesyntax.customexception.InvalidDataException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int USER_VALID_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkAge(user.getAge());
        checkLogin(user.getLogin());
        checkPassword(user.getPassword());
        storageDao.add(user);
        return user;
    }

    private void checkAge(int age) {
        if (age < USER_VALID_AGE) {
            throw new InvalidDataException("Invalid age for register " + age);
        }
    }

    private void checkLogin(String login) {
        String existsLogin = null;
        if (login == null) {
            throw new InvalidDataException("Login can`t be null");
        }
        User user = storageDao.get(login);
        if (user != null) {
            existsLogin = user.getLogin();
        }
        if (login.equals(existsLogin)) {
            throw new InvalidDataException("This login already exists");
        }
    }

    private void checkPassword(String password) {
        if (password == null) {
            throw new InvalidDataException("Password can`t be null");
        }
        if (password.length() < 6) {
            throw new InvalidDataException("Password length can`t be less than 6");
        }
    }
}
