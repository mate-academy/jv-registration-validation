package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.UserInvalidException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int USER_MIN_AGE = 18;
    private static final int USER_MAX_AGE = 150;
    private static final int PASSWORD_MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkUserIsNotNull(user);
        checkPassword(user);
        checkAge(user);
        checkLogin(user);
        storageDao.add(user);
        return user;
    }

    private void checkPassword(User user) {
        if (user.getPassword() == null || user.getPassword().length() < PASSWORD_MIN_LENGTH) {
            throw new UserInvalidException("Password is: " + user.getPassword()
                    + "Should be not null and more than 6 letters");
        }
    }

    private void checkAge(User user) {
        if (user.getAge() < USER_MIN_AGE || user.getAge() > USER_MAX_AGE) {
            throw new UserInvalidException("Age is: " + user.getAge()
                    + "Should be more than 17 and less than 150");
        }
    }

    private void checkUserIsNotNull(User user) {
        if (user == null) {
            throw new UserInvalidException("User is null you should to create the user");
        }
    }

    private void checkLogin(User user) {
        if (user.getLogin() == null) {
            throw new UserInvalidException("Login is: " + user.getLogin()
                    + " login shouldn`t be null");
        } else if (storageDao.get(user.getLogin()) != null) {
            throw new UserInvalidException("Login is: " + user.getLogin()
                    + " user with this login is already exist try to use another login");
        }
    }
}
