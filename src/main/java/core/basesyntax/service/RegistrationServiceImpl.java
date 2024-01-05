package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_USER_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User can't be null!");
        }
        loginCheck(user);
        passwordCheck(user);
        ageCheck(user);
        return storageDao.add(user);
    }

    private User loginCheck(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null!");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("Login should be at least "
                    + MIN_LOGIN_LENGTH + " characters!");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with such login already exist!");
        }
        return user;
    }

    private User passwordCheck(User user) {
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null!");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Password should be at least "
                    + MIN_PASSWORD_LENGTH + " characters!");
        }
        return user;
    }

    private User ageCheck(User user) {
        if (user.getAge() == null) {
            throw new RegistrationException("Age can't be null!");
        }
        if (user.getAge() < MIN_USER_AGE) {
            throw new RegistrationException("Not valid age: " + user.getAge()
                    + "User should be at least " + MIN_USER_AGE + " y.o.");
        }
        return user;
    }
}
