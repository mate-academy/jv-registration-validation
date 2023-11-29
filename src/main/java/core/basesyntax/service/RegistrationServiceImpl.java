package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.AgeRestrictionException;
import core.basesyntax.exception.PasswordException;
import core.basesyntax.exception.PasswordLengthException;
import core.basesyntax.exception.UserLoginException;
import core.basesyntax.exception.UserParamException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int AGE_REQUIRE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        evaluateUser(user);
        loginExist(user.getLogin());
        evaluateLogin(user.getLogin());
        evaluatePassword(user.getPassword());
        checkAge(user.getAge());
        return storageDao.add(user);
    }

    private void evaluateUser(User user) {
        if (user == null) {
            throw new UserParamException("user can't be null");
        }
    }

    private void loginExist(String login) {
        if (storageDao.get(login) != null) {
            throw new UserLoginException("user with this login already exist");
        }
    }

    private void evaluateLogin(String login) {
        if (login == null) {
            throw new UserLoginException("login can't be null");
        } else if (login.length() < MIN_PASSWORD_LENGTH) {
            throw new UserLoginException("user's login must is at least 6 characters");
        }
    }

    private void evaluatePassword(String password) {
        if (password == null) {
            throw new PasswordException("password can't be null");
        } else if (password.length() < MIN_LOGIN_LENGTH) {
            throw new PasswordLengthException("user's password must is at least 6 characters");
        }
    }

    private void checkAge(Integer age) {
        if (age == null) {
            throw new AgeRestrictionException("age can't be null");
        } else if (age < AGE_REQUIRE) {
            throw new AgeRestrictionException("user's age must is at least 18 years old");
        }
    }
}
