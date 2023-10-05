package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkInputForNulls(user);

        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidInputException("This login is already taken");
        }

        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new InvalidInputException("This login is too short");
        }

        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidInputException("This password is too short");
        }

        if (user.getAge() < MIN_AGE) {
            throw new InvalidInputException("You are too young");
        }

        return storageDao.add(user);
    }

    private void checkInputForNulls(User user) {
        if (user == null) {
            throw new NullPointerException("User can't be null");
        }

        if (user.getLogin() == null) {
            throw new InvalidInputException("Login can't be null");
        }

        if (user.getPassword() == null) {
            throw new InvalidInputException("Password can't be null");
        }
    }
}
