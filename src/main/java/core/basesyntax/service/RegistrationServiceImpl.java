package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.UserAgeException;
import core.basesyntax.exception.UserLoginExistsException;
import core.basesyntax.exception.UserLoginLengthException;
import core.basesyntax.exception.UserPasswordLengthException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int USER_LOGIN_LENGTH_MIN = 6;
    private static final int USER_PASSWORD_LENGTH_MIN = 6;
    private static final int USER_AGE_MIN = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        String userLogin = user.getLogin();
        String userPassword = user.getPassword();
        Integer userAge = user.getAge();
        checkLoginNull(userLogin);
        checkPasswordNull(userPassword);
        checkAgeNull(userAge);
        if (!isUserLoginLengthOk(userLogin)) {
            throw new UserLoginLengthException("User login length less than min length: "
                                                       + USER_LOGIN_LENGTH_MIN
                                                       + " try input another one");
        } else if (isUserLoginExist(userLogin)) {
            throw new UserLoginExistsException("User with this login: "
                                                       + userLogin
                                                       + " already exists");
        } else if (!isUserPasswordLengthOk(userPassword)) {
            throw new UserPasswordLengthException("User password length less than min length: "
                                                          + USER_PASSWORD_LENGTH_MIN
                                                          + " try input another one");
        } else if (!isUserAgeOk(userAge)) {
            throw new UserAgeException("User age less than min age: "
                                               + USER_AGE_MIN);
        }
        return storageDao.add(user);
    }

    private boolean isUserLoginExist(String login) {
        if (storageDao.get(login) != null) {
            return true;
        }
        return false;
    }

    private boolean isUserLoginLengthOk(String login) {
        if (login.length() < USER_LOGIN_LENGTH_MIN) {
            return false;
        }
        return true;
    }

    private boolean isUserPasswordLengthOk(String password) {
        if (password.length() < USER_PASSWORD_LENGTH_MIN) {
            return false;
        }
        return true;
    }

    private boolean isUserAgeOk(int age) {
        if (age < USER_AGE_MIN) {
            return false;
        }
        return true;
    }

    private void checkLoginNull(String login) {
        if (login == null) {
            throw new NullPointerException("Login is null, try input another one");
        }
    }

    private void checkPasswordNull(String password) {
        if (password == null) {
            throw new NullPointerException("Password is null, try input another one");
        }
    }

    private void checkAgeNull(Integer age) {
        if (age == null) {
            throw new NullPointerException("Age is null, try input another one");
        }
    }
}
