package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MAX_AGE = 140;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkUser(user);
        checkPassword(user.getPassword());
        checkLogin(user.getLogin());
        checkAge(user);
        return storageDao.add(user);
    }

    private void checkUser(User user) {
        if (user == null) {
            throw new RegistrationException("User can't be NULL.");
        }
    }

    private void checkLogin(String login) {
        if (login == null || login.isEmpty()) {
            throw new RegistrationException("Field login can't be empty.");
        }
        if (storageDao.get(login) != null) {
            throw new RegistrationException("This login is already exists, please, choose another");
        }
    }

    private void checkAge(User user) {
        if (user.getAge() == null) {
            throw new RegistrationException("Field age can't be empty.");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("User with age less then 18 can't be registered.");
        }
        if (user.getAge() > MAX_AGE) {
            throw new RegistrationException("Invalid age input, check this field.");
        }
    }

    private void checkPassword(String password) {
        if (password == null) {
            throw new RegistrationException("You need to fill all fields for registration");
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Your password's length should be 6 or more.");
        }
    }
}
