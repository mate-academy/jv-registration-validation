package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final String USER_ALREADY_EXISTS =
            "the user already exists in the storage";
    public static final String USER_AGE_IS_LESS_THAN_18 =
            "the user age is less than 18";
    public static final String USER_PASSWORD_IS_SHORT =
            "the user password is shorter than 6 characters";
    public static final String USER_HAS_NO_LOGIN =
            "the user has no login";
    public static final String USER_HAS_NO_PASSWORD =
            "the user has no password";
    public static final String USER_HAS_NO_AGE =
            "the user has no age";
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RuntimeException(USER_HAS_NO_LOGIN);
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException(USER_ALREADY_EXISTS);
        }
        if (user.getPassword() == null) {
            throw new RuntimeException(USER_HAS_NO_PASSWORD);
        }
        if (user.getPassword().length() < 6) {
            throw new RuntimeException(USER_PASSWORD_IS_SHORT);
        }
        if (user.getAge() == null) {
            throw new RuntimeException(USER_HAS_NO_AGE);
        }
        if (user.getAge() < 18) {
            throw new RuntimeException(USER_AGE_IS_LESS_THAN_18);
        }
        storageDao.add(user);
        return user;
    }
}
