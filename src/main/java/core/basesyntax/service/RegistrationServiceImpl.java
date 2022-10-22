package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int LOG_MIN_LEN = 1;
    public static final int PASS_MIN_LEN = 6;
    public static final int AGE_MIN = 18;
    public static final String THE_USER_IS_NULL =
            "the user is null";
    public static final String USER_HAS_NO_LOGIN =
            "the user has no login";
    public static final String USER_LOGIN_IS_SHORTER_THEN_1 =
            "the user has short login";
    public static final String USER_PASS_IS_NULL =
            "the user password is null";
    public static final String USER_PASS_LENGTH_IS_SHORT =
            "the user password length is short";
    public static final String USER_AGE_IS_NULL =
            "the user age is null";
    public static final String USER_AGE_IS_LESS_THEN_18 =
            "the user age is less then 18";
    public static final String USER_ALREADY_EXISTS =
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
            showException(THE_USER_IS_NULL);
        }
    }

    private void checkLogin(String login) {
        if (login == null) {
            showException(USER_HAS_NO_LOGIN);
        }
        if (login.length() < LOG_MIN_LEN) {
            showException(USER_LOGIN_IS_SHORTER_THEN_1);
        }
    }

    private void checkPass(String pass) {
        if (pass == null) {
            showException(USER_PASS_IS_NULL);
        }
        if (pass.length() < PASS_MIN_LEN) {
            showException(USER_PASS_LENGTH_IS_SHORT);
        }
    }

    private void checkAge(Integer age) {
        if (age == null) {
            showException(USER_AGE_IS_NULL);
        }
        if (age < AGE_MIN) {
            showException(USER_AGE_IS_LESS_THEN_18);
        }
    }

    private void checkUserAlreadyExists(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            showException(USER_ALREADY_EXISTS);
        }
    }

    private void showException(String errorMessage) {
        throw new RuntimeException(errorMessage);
    }
}
