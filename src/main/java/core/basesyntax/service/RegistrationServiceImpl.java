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
            throw new RegistrationException("You are too young");
        }
    }

    private void checkPassword(String password) {
        if (password == null) {
            throw new RegistrationException("null password");
        }
        if (password.length() < LENGTH_AT_LEAST_REQUIRE) {
            throw new RegistrationException("Too short length password");
        }
    }

    private void checkNull(User user) {
        if (user == null) {
            throw new RegistrationException("null user.");
        }
    }

    private void checkLogin(String login) {
        if (login == null) {
            throw new RegistrationException("null login");
        }
        if (login.length() < LENGTH_AT_LEAST_REQUIRE) {
            throw new RegistrationException("too short login");
        }
        if (storageDao.get(login) != null) {
            throw new RegistrationException("this login is already existed");
        }
    }
}
