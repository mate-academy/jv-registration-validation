package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.ValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new ValidationException("User can't be null");
        }
        checkLogin(user.getLogin());
        checkAge(user.getAge());
        checkPassword(user.getPassword());
        return storageDao.add(user);
    }

    private void checkLogin(String login) {
        if (login == null || login.isEmpty()) {
            throw new ValidationException("Login can't be null");
        }
        if (storageDao.get(login) != null) {
            throw new ValidationException("There is user with login "
                    + login + " in the Storage yet");
        }
    }

    private void checkAge(Integer age) {
        if (age == null || age < MIN_AGE) {
            throw new ValidationException("User's age must be more than" + MIN_AGE);
        }
    }

    private void checkPassword(String password) {
        if (password == null) {
            throw new ValidationException("Login can't be null");
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new ValidationException("User's password must be more than"
                    + MIN_PASSWORD_LENGTH);
        }
    }
}
