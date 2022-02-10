package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MAX_VALUE = 100;
    private static final int MIN_VALUE = 18;
    private static final int MIN_PASSWORD = 6;
    private final StorageDaoImpl storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("user is null");
        }
        isValidUser(user);
        return storageDao.add(user);
    }

    private void isValidUser(User user) {
        userNotHadFieldsNull(user);
        userNotHadSpace(user);
        userNotHadAge(user.getAge());
        userNotHadPassword(user.getPassword());
        userNotHadLogin(user.getLogin());
    }

    private void userNotHadSpace(User user) {
        if (user.getLogin().contains(" ")) {
            throw new RuntimeException("This login has invalid characters");
        } else if (user.getPassword().contains(" ")) {
            throw new RuntimeException("This password has invalid characters");
        }
    }

    private void userNotHadFieldsNull(User user) {
        if (user.getLogin() == null || user.getPassword() == null) {
            throw new RuntimeException("user has a field with null value)");
        }
    }

    private void userNotHadLogin(String userLogin) {
        for (User getLog : Storage.people) {
            if (getLog.getLogin().equals(userLogin)) {
                throw new RuntimeException("Sorry such login already exists");
            }
        }
    }

    private void userNotHadPassword(String password) {
        if (password.length() < MIN_PASSWORD) {
            throw new RuntimeException("password shouldn't be " + password);
        }
    }

    private void userNotHadAge(Integer age) {
        if (age < MIN_VALUE || age > MAX_VALUE) {
            throw new RuntimeException("Age shouldn't be " + age);
        }
    }
}
