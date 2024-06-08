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
        checkNull(user);
        checkAge(user.getAge());
        checkLogin(user.getLogin());
        checkPassword(user.getPassword());
        storageDao.add(user);
        return user;
    }

    private void checkAge(Integer age) {
        if (age < AGE_AT_LEAST_REQUIRE) {
            try {
                throw new RegistrationException("Not valid login");
            } catch (RegistrationException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void checkPassword(String password) {
        if (password == null
                || password.length() < LENGTH_AT_LEAST_REQUIRE) {
            try {
                throw new RegistrationException("Not valid login");
            } catch (RegistrationException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void checkNull(User user) {
        if (user == null) {
            try {
                throw new RegistrationException("Null User");
            } catch (RegistrationException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void checkLogin(String login) {
        if (login == null
                || storageDao.get(login) != null
                || login.length() < LENGTH_AT_LEAST_REQUIRE) {
            try {
                throw new RegistrationException("Not valid login");
            } catch (RegistrationException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
