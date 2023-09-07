package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH = 6;
    private static final int MIN_AGE = 18;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws ValidationException {
        if (storageDao.get(user.getLogin()) != null) {
            throw new ValidationException("User is already registered: " + user.getLogin());
        }

        checkLogin(user.getLogin());
        checkPassword(user.getPassword());
        checkAge(user.getAge());
        return storageDao.add(user);
    }

    private void checkLogin(String login) {
        if (login == null) {
            throw new ValidationException("Login can't be null!");
        }
        if (login.length() < MIN_LENGTH) {
            throw new ValidationException("Login must be at least 6 characters long: " + login);
        }
    }

    private void checkAge(Integer age) {
        if (age == null) {
            throw new ValidationException("Age can't be null!");
        }
        if (age <= MIN_AGE) {
            throw new ValidationException("You must be more than 17y.o.!: " + age);
        }

    }

    private void checkPassword(String password) {
        if (password == null) {
            throw new ValidationException("Password can't be null: " + password);
        }
        if (password.length() < MIN_LENGTH) {
            throw new ValidationException("Password must be at least 6 characters long: "
                    + password);
        }
    }
}
