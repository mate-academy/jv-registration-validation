package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.InvalidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH = 6;
    private static final int MIN_LOGIN_LENGTH = 3;
    private static final int MIN_AGE = 18;
    private static final int ZERO_AGE = 0;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws InvalidDataException {
        userValidation(user);
        passwordValidation(user);
        ageValidation(user);
        loginValidation(user);
        return storageDao.add(user);
    }

    private void userValidation(User user) throws InvalidDataException {
        if (user == null) {
            throw new InvalidDataException("The user should not be equal null");
        }
        User newUser = storageDao.get(user.getLogin());
        if (newUser != null) {
            throw new InvalidDataException("The user already is in storage: " + user.getLogin());
        }
    }

    private void passwordValidation(User user) throws InvalidDataException {
        if (user.getPassword() == null) {
            throw new InvalidDataException("Your password is equal null");
        }
        if (user.getPassword().isEmpty()) {
            throw new InvalidDataException("You did not write any symbol: "
                    + user.getPassword());
        }
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new InvalidDataException("Your password is short: "
                    + user.getPassword());
        }
    }

    private void ageValidation(User user) throws InvalidDataException {
        if (user.getAge() == null) {
            throw new InvalidDataException("Your age is equal null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new InvalidDataException("You are younger then need: "
                    + user.getAge());
        }
        if (user.getAge() < ZERO_AGE) {
            throw new InvalidDataException("You wrote a negative number: "
                    + user.getAge());
        }
    }

    private void loginValidation(User user) throws InvalidDataException {
        if (user.getLogin() == null) {
            throw new InvalidDataException("Your login is equal null");
        }
        if (user.getLogin().isEmpty()) {
            throw new InvalidDataException("You did not write any symbol: "
                    + user.getLogin());
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new InvalidDataException("Your login is very short: "
                    + user.getLogin());
        }
    }
}
