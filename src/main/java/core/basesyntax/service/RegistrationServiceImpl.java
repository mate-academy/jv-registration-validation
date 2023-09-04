package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws RegistrationException {
        if (user == null) {
            throw new RegistrationException("User can not be null");
        }
        checkAge(user.getAge());
        checkLogin(user.getLogin());
        checkPassword(user.getPassword());
        return storageDao.add(user);
    }

    private void checkLogin(String login) {
        if (login == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (login.length() < MIN_LENGTH) {
            throw new RegistrationException(
                    "Your login needs to be at least " + MIN_LENGTH + " characters long"
            );
        }
        if (storageDao.get(login) != null) {
            throw new RegistrationException("There is user with such login already");
        }
    }

    private void checkAge(Integer age) {
        if (age == null) {
            throw new RegistrationException("Age can't be null");
        }
        if (age < 0) {
            throw new RegistrationException("Age can't be negative");
        }
        if (age < MIN_AGE) {
            throw new RegistrationException("User must be at least" + MIN_AGE + "years old");
        }
    }

    private void checkPassword(String password) {
        if (password == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (password.length() < MIN_LENGTH) {
            throw new RegistrationException(
                    "Your password needs to be at least " + MIN_LENGTH + " characters long"
            );
        }
    }
}
