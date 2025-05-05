package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_USER_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        ageCheck(user);
        loginCheck(user);
        passwordCheck(user);
        return storageDao.add(user);
    }

    private void ageCheck(User user) {
        if (user.getAge() == null) {
            throw new RuntimeException("User age can't be null");
        }
        if (user.getAge() < MIN_USER_AGE) {
            throw new RuntimeException("Age is not valid");
        }
    }

    private void loginCheck(User user) {
        if (user.getLogin() == null) {
            throw new RuntimeException("Login can't be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("This login is already exist");
        }
    }

    private void passwordCheck(User user) {
        if (user.getPassword() == null) {
            throw new RuntimeException("Password can't be null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Password length should be "
                    + MIN_PASSWORD_LENGTH + " or more symbols");
        }
    }
}
