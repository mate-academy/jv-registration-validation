package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_USER_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new NullPointerException("User can not be Null!");
        }
        validateUserLogin(user.getLogin());
        validateUserAge(user.getAge());
        validateUserPassword(user.getPassword());
        storageDao.add(user);
        return user;
    }

    private void validateUserPassword(String password) {
        if (password == null) {
            throw new NullPointerException("Password field can't be null");
        } else if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Password length must be at least " + MIN_PASSWORD_LENGTH);
        }
    }

    private void validateUserAge(Integer age) {
        if (age == null) {
            throw new NullPointerException("User age can not be Null!");
        } else if (age < MIN_USER_AGE) {
            throw new RuntimeException("User is under " + MIN_USER_AGE + " years old");
        }
    }

    private void validateUserLogin(String login) {
        if (login == null) {
            throw new NullPointerException("User login can not be Null!");
        } else if (login.isEmpty()) {
            throw new RuntimeException("User login can not be empty!");
        } else if (login.contains(" ")) {
            throw new RuntimeException("User login can not contain white spaces!");
        } else if (storageDao.get(login) != null) {
            throw new RuntimeException("Such user has already exist!!!");
        }
    }
}
