package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int LOG_MIN_LEN = 0;
    public static final int PASS_MIN_LEN = 6;
    public static final int AGE_MIN = 18;
    public static final String USER_ALREADY_EXISTS =
            "the user already exists in the storage";
    public static final String USER_LOGIN_INVALID =
            "the user has invalid login";
    public static final String USER_PASS_INVALID =
            "the user has invalid password";
    public static final String USER_AGE_INVALID =
            "the user has invalid age";
    public static final String THE_USER_IS_NULL =
            "the user is null";
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException(THE_USER_IS_NULL);
        }
        if (!isLoginValid(user.getLogin())) {
            throw new RuntimeException(USER_LOGIN_INVALID);
        }
        if (!isPassValid(user.getPassword())) {
            throw new RuntimeException(USER_PASS_INVALID);
        }
        if (!isAgeValid(user.getAge())) {
            throw new RuntimeException(USER_AGE_INVALID);
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException(USER_ALREADY_EXISTS);
        }
        storageDao.add(user);
        return user;
    }

    private boolean isLoginValid(String login) {
        return login != null && login.length() != LOG_MIN_LEN;
    }

    private boolean isPassValid(String pass) {
        return pass != null && pass.length() >= PASS_MIN_LEN;
    }

    private boolean isAgeValid(Integer age) {
        return age != null && age >= AGE_MIN;
    }
}
