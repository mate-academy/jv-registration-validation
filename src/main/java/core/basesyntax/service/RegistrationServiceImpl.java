package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_NUMBER_OF_CHARACTERS = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        nullCheck(user);
        userExistCheck(user);
        ageCheck(user);
        passwordCheck(user);
        return storageDao.add(user);
    }

    private void userExistCheck(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User with login " + user.getLogin() + " exists");
        }
    }

    private void nullCheck(User user) {
        if (user == null) {
            throw new RuntimeException("User is null");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("Login is null");
        }
        if (user.getAge() == null) {
            throw new RuntimeException("Age is null");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("Password is null");
        }
    }

    private void ageCheck(User user) {
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("Age is less than 18");
        }
    }

    private void passwordCheck(User user) {
        if (user.getPassword().length() < MIN_NUMBER_OF_CHARACTERS) {
            throw new RuntimeException("Password length is less than 6 characters");
        }
    }
}
