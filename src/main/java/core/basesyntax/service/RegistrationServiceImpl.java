package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMAL_AGE = 18;
    private static final int MINIMAL_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkIfUserExist(user);
        checkLoginLength(user);
        checkMinPasswordLength(user);
        checkAgeIsNotNull(user);
        checkMinAge(user);
        checkIfAddToStorage(user);
        return user;
    }

    private boolean checkIfUserExist(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User: " + user.getLogin() + " is already exist!");
        }
        return true;
    }

    private boolean checkLoginLength(User user) {
        if (user.getLogin().length() == 0) {
            throw new RuntimeException("User`s login is to short!");
        }
        return true;
    }

    private boolean checkMinPasswordLength(User user) {
        if (user.getPassword().length() < MINIMAL_PASSWORD_LENGTH) {
            throw new RuntimeException("Password length is less than 6 characters!");
        }
        return true;
    }

    private boolean checkAgeIsNotNull(User user) {
        if (user.getAge() == null) {
            throw new NullPointerException("Age is null!");
        }
        return true;
    }

    private boolean checkMinAge(User user) {
        if (user.getAge() < MINIMAL_AGE) {
            throw new RuntimeException("Age is less than 18 years!");
        }
        return true;
    }

    private boolean checkIfAddToStorage(User user) {
        int storageOldSize = Storage.people.size();
        storageDao.add(user);
        if (Storage.people.size() != storageOldSize + 1) {
            throw new RuntimeException("User was not add to the storage!");
        }
        return true;
    }

}
