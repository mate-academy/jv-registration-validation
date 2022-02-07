package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_CHAR = 6;
    private StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        isUserNullCheck(user);
        dataNullCheck(user);
        checkPasswordLength(user);
        checkAge(user);
        isPasswordOrLoginEmpty(user);
        isSpaceInLoginOrPassword(user);
        checkUserWithSuchLogin(user);
        return storageDao.add(user);
    }

    private void isUserNullCheck(User user) {
        if (user == null) {
            throw new RuntimeException("User is Null!");
        }
    }

    private void dataNullCheck(User user) {
        if (user.getLogin() == null || user.getPassword() == null || user.getAge() == null) {
            throw new RuntimeException("Data can't be null!");
        }
    }

    private void checkPasswordLength(User user) {
        if (user.getPassword().length() < MIN_PASSWORD_CHAR) {
            throw new RuntimeException("Password can't be less than 6 symbol");
        }
    }

    private void checkAge(User user) {
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("Age can't be less than 18");
        }
    }

    private void isPasswordOrLoginEmpty(User user) {
        if (user.getLogin().trim().equals("") || user.getPassword().trim().equals("")) {
            throw new RuntimeException("Login or password is empty!");
        }
    }

    private void isSpaceInLoginOrPassword(User user) {
        if (user.getLogin().trim().contains(" ") || user.getPassword().trim().contains(" ")) {
            throw new RuntimeException("Password or login can't have a space!");
        }
    }

    private void checkUserWithSuchLogin(User user) {
        for (User u : Storage.people) {
            if (u.getLogin().equals(user.getLogin())) {
                throw new RuntimeException("User with such login already exists!");
            }
        }
    }
}
