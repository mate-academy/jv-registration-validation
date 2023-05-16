package core.basesyntax.service;

import core.basesyntax.InvalidDataException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_CHARACTERS = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        isValidLogin(user);
        isValidPassword(user);
        isValidAge(user);
        return storageDao.add(user);
    }

    private void isValidLogin(User user) {
        if (user == null) {
            throw new InvalidDataException("User is null");
        }
        User comparedLogin = storageDao.get(user.getLogin());
        if (comparedLogin != null) {
            throw new InvalidDataException("This login is taken");
        }
        if (user.getLogin() == null || user.getLogin().length() < MIN_CHARACTERS) {
            throw new InvalidDataException("This login least than " + MIN_CHARACTERS + " characters");
        }
    }

    private void isValidPassword(User user) {
        if (user.getPassword() == null || user.getPassword().length() < MIN_CHARACTERS) {
            throw new InvalidDataException("This password least than " + MIN_CHARACTERS + " characters");
        }
    }

    private void isValidAge(User user) {
        if (user.getAge() == null || user.getAge() < MIN_AGE) {
            throw new InvalidDataException("Your age least than " + MIN_AGE +" year old");
        }
    }
}

