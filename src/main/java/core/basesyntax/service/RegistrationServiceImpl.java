package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MIN_USER_AGE = 18;
    public static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        loginValidator(user);
        ageValidator(user);
        passwordValidator(user);
        return storageDao.add(user);
    }

    private void passwordValidator(User user) {
        if (user.getPassword() == null) {
            throw new NullPointerException("Password can't be null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Password has less than 6 symbols");
        }
    }

    private void ageValidator(User user) {
        if (user.getAge() == null) {
            throw new NullPointerException("Age can't be null");
        }
        if (user.getAge() < MIN_USER_AGE) {
            throw new RuntimeException("Age less then 18");
        }
    }

    private void loginValidator(User user) {
        if (user.getLogin() == null) {
            throw new NullPointerException("Login can't be null");
        }
        if (user.equals(storageDao.get(user.getLogin()))) {
            throw new RuntimeException("User is already exist");
        }
    }
}
