package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static int AGE_AT_LEAST_REQUIRE = 18;
    private static int LENGTH_AT_LEAST_REQUIRE = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkUser(user);
        checkAge(user.getAge());
        checkLogin(user.getLogin());
        checkPassword(user.getPassword());
        storageDao.add(user);
        return user;
    }

    private void checkAge(Integer age) {
        if (age == null || age < AGE_AT_LEAST_REQUIRE) {
            throw new RegistrationException("Not allow register too young user");
        }
    }

    private void checkPassword(String password) {
        if (password == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (password.length() < LENGTH_AT_LEAST_REQUIRE) {
            throw new RegistrationException("Password can't be to short");
        }
    }

    private void checkUser(User user) {
        if (user == null) {
            throw new RegistrationException("User can't be null");
        }
    }

    private void checkLogin(String login) {
        if (login == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (login.length() < LENGTH_AT_LEAST_REQUIRE) {
            throw new RegistrationException("Login can't be too short");
        }
        if (storageDao.get(login) != null) {
            throw new RegistrationException("This login is already existed");
        }
    }
}
