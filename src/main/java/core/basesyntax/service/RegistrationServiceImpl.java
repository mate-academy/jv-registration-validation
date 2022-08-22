package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_USER_LOGIN_LENGTH = 6;
    private static final int MAXIMUM_USER_LOGIN_LENGTH = 10;
    private static final int MINIMUM_USER_PASSWORD_LENGTH = 6;
    private static final int MAXIMUM_USER_PASSWORD_LENGTH = 15;
    private static final int MINIMUM_USER_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("Can`t add user with parameter null");
        }
        checkUserLogin(user);
        checkUserPassword(user);
        checkUserAge(user);
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Can`t add user. User with the same login already exist!");
        }
        storageDao.add(user);
        return user;
    }

    private void checkUserLogin(User user) {
        if (user.getLogin() == null) {
            throw new RuntimeException("Can`t add user with null login");
        }
        if (user.getLogin().length() < MINIMUM_USER_LOGIN_LENGTH
                || user.getLogin().length() > MAXIMUM_USER_LOGIN_LENGTH) {
            throw new RuntimeException("Can`t add user with login length less than "
                    + MINIMUM_USER_LOGIN_LENGTH + " and more than "
                    + MAXIMUM_USER_LOGIN_LENGTH + ", your login length is "
                    + user.getLogin().length());
        }
    }

    private void checkUserPassword(User user) {
        if (user.getPassword() == null) {
            throw new RuntimeException("Can`t add user with null password");
        }
        if (user.getPassword().length() < MINIMUM_USER_PASSWORD_LENGTH
                || user.getPassword().length() > MAXIMUM_USER_PASSWORD_LENGTH) {
            throw new RuntimeException("Can`t add user with password length less than "
                    + MINIMUM_USER_PASSWORD_LENGTH + " and more than "
                    + MAXIMUM_USER_PASSWORD_LENGTH + ", your password length is "
                    + user.getPassword().length());
        }
    }

    private void checkUserAge(User user) {
        if (user.getAge() == null) {
            throw new RuntimeException("Can`t add user with null age");
        }
        if (user.getAge() < MINIMUM_USER_AGE) {
            throw new RuntimeException("Can`t add user with age less than "
                    + MINIMUM_USER_AGE + ", your age is " + user.getAge());
        }
    }
}
