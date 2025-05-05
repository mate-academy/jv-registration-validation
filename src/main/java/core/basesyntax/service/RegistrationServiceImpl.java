package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int LOG_MIN_LENGTH = 1;
    private static final int PASS_MIN_LENGTH = 6;
    private static final int AGE_MIN = 18;
    private static final String USER_IS_NULL_MESSAGE =
            "the instance of User class is null";
    private static final String USER_HAS_NO_LOGIN_MESSAGE =
            "the user has no login";
    private static final String LOGIN_IS_SHORTER_THEN_1_MESSAGE =
            "the user has short login";
    private static final String PASS_IS_NULL_MESSAGE =
            "the user password is null";
    private static final String PASS_LENGTH_IS_SHORT_MESSAGE =
            "the user password length is short";
    private static final String AGE_IS_NULL_MESSAGE =
            "the user age is null";
    private static final String AGE_IS_LESS_THEN_18_MESSAGE =
            "the user age is less then 18";
    private static final String USER_ALREADY_EXISTS_MESSAGE =
            "the user already exists in the storage";
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkUserNotNull(user);
        checkLogin(user.getLogin());
        checkPass(user.getPassword());
        checkAge(user.getAge());
        checkUserAlreadyExists(user);
        storageDao.add(user);
        return user;
    }

    private void checkUserNotNull(User user) {
        if (user == null) {
            showException(USER_IS_NULL_MESSAGE);
        }
    }

    private void checkLogin(String login) {
        if (login == null) {
            showException(USER_HAS_NO_LOGIN_MESSAGE);
        }
        if (login.length() < LOG_MIN_LENGTH) {
            showException(LOGIN_IS_SHORTER_THEN_1_MESSAGE);
        }
    }

    private void checkPass(String pass) {
        if (pass == null) {
            showException(PASS_IS_NULL_MESSAGE);
        }
        if (pass.length() < PASS_MIN_LENGTH) {
            showException(PASS_LENGTH_IS_SHORT_MESSAGE);
        }
    }

    private void checkAge(Integer age) {
        if (age == null) {
            showException(AGE_IS_NULL_MESSAGE);
        }
        if (age < AGE_MIN) {
            showException(AGE_IS_LESS_THEN_18_MESSAGE);
        }
    }

    private void checkUserAlreadyExists(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            showException(USER_ALREADY_EXISTS_MESSAGE);
        }
    }

    private void showException(String errorMessage) {
        throw new RuntimeException(errorMessage);
    }
}
