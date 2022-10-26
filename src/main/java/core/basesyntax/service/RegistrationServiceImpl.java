package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int AGE_ALLOW = 18;
    private static final int LENGTH_ALLOW = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("User can't be: null!");
        }
        loginCheck(user.getLogin());
        ageCheck(user.getAge());
        passwordLengthCheck(user.getPassword().length());
        return storageDao.add(user);
    }

    private void loginCheck(String login) {
        if (login == null) {
            throw new RuntimeException("Login can't be null!");
        }
        if (storageDao.get(login) != null) {
            throw new RuntimeException("There is already exist user with such login!");
        }
    }

    private void ageCheck(Integer age) {
        if (age == null) {
            throw new RuntimeException("Age can't be null!");
        }
        if (age < AGE_ALLOW) {
            throw new RuntimeException("User age is less than expected!");
        }
    }

    private void passwordLengthCheck(Integer passwordLength) {
        if (passwordLength == null) {
            throw new RuntimeException("Age can't be null!");
        }
        if (passwordLength < LENGTH_ALLOW) {
            throw new RuntimeException("Your password has less than 6 characters");
        }
    }
}
