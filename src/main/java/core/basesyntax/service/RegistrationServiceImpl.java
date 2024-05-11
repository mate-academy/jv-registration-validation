package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASS_AND_LOGIN_RANGE = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao;

    public RegistrationServiceImpl(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public User register(User user) {
        checkUserNull(user);
        checkUniqueLogin(user);
        checkUserData(user);
        checkLoginAndPasswordLength(user);
        checkAge(user);
        return storageDao.add(user);
    }

    private void checkUniqueLogin(User user) {
        User storedUser = storageDao.get(user.getLogin());
        if (storedUser != null) {
            throw new InvalidDataException("This login is occupied. "
                    + "Please change your login to another one");
        }
    }

    private void checkUserNull(User user) {
        if (user == null) {
            throw new InvalidDataException("Login and password should be 6 "
                    + "or more characters and age is more than 18");
        }
    }

    private void checkUserData(User user) {
        if (user.getLogin() == null) {
            throw new InvalidDataException("Login must be not null");
        }
        if (user.getPassword() == null) {
            throw new InvalidDataException("Password must be not null");
        }
        if (user.getAge() == null) {
            throw new InvalidDataException("User`s age must be not null");
        }
    }

    private void checkLoginAndPasswordLength(User user) {
        if (user.getLogin().length() < MIN_PASS_AND_LOGIN_RANGE) {
            throw new InvalidDataException("User`s login should be more than 6 characters");
        }
        if (user.getPassword().length() < MIN_PASS_AND_LOGIN_RANGE) {
            throw new InvalidDataException("User`s password should be more than 6 characters");
        }
    }

    private void checkAge(User user) {
        if (user.getAge() < MIN_AGE) {
            throw new InvalidDataException("User`s age should be more than 18");
        }
    }
}
