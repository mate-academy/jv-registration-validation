package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        passwordValidate(user);
        ageValidate(user);
        loginValidate(user);
        return storageDao.add(user);
    }

    private void loginValidate(User user) {
        if (user.getLogin() == null) {
            throw new NullPointerException("Login can`t be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Login like this already exist");
        }
    }

    private void passwordValidate(User user) {
        if (user.getPassword() == null) {
            throw new NullPointerException("Password can`t be null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Invalid password (password length can`t be less than 6)");
        }
    }

    private void ageValidate(User user) {
        if (user.getAge() == null) {
            throw new NullPointerException("Age can`t be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("Age can`t be less than 18");
        }
    }
}
