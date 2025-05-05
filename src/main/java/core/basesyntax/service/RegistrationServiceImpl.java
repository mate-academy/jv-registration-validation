package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH = 6;
    private static final int MIN_AGE = 18;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User is already registered: " + user.getLogin());
        }

        checkLogin(user.getLogin());
        checkPassword(user.getPassword());
        checkAge(user.getAge());
        return storageDao.add(user);
    }

    private void checkLogin(String login) {
        if (login == null) {
            throw new RegistrationException("Login can't be null!");
        }
        if (login.length() < MIN_LENGTH) {
            throw new RegistrationException("Login must be at least "
                    + MIN_LENGTH + " characters long: " + login);
        }
    }

    private void checkAge(Integer age) {
        if (age == null) {
            throw new RegistrationException("Age can't be null!");
        }
        if (age <= MIN_AGE) {
            throw new RegistrationException("You must be more than 17y.o.!: " + age);
        }

    }

    private void checkPassword(String password) {
        if (password == null) {
            throw new RegistrationException("Password can't be null!");
        }
        if (password.length() < MIN_LENGTH) {
            throw new RegistrationException("Password must be at least "
                    + MIN_LENGTH + " characters long: "
                    + password);
        }
    }
}
