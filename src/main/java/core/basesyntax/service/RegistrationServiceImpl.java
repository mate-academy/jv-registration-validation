package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int PASSWORD_MIN_LENGTH = 6;
    private static final int ILLEGAL_AGE_MAX = 18;
    private static final String VALID_LOGIN_REGEX = "^\\w{6}\\w*$";
    private static final String VALID_PASSWORD_REGEX =
            "(^\\w*((\\d+\\w*[A-Z]+)|([A-Z]+\\w*\\d+))\\w*$)";
    private static StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (storageDao.get(user.getLogin()) == null && user.getLogin() != null
                && user.getPassword() != null
                && user.getPassword().matches(VALID_PASSWORD_REGEX)
                && user.getPassword().length() >= PASSWORD_MIN_LENGTH
                && user.getLogin().matches(VALID_LOGIN_REGEX)
                && user.getAge() != null
                && user.getAge() > ILLEGAL_AGE_MAX) {
            return storageDao.add(user);
        }
        throw new RuntimeException("Registration failed");
    }
}
