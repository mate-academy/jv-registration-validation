package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MIN_AGE = 18;
    public static final int MIN_PASSWORD_LENGTH = 6;
    public static final int MIN_LOGIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("Login does not by null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password does not by null");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age does not be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Age does not be null" + MIN_AGE);
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("Are no enough characters" + MIN_LOGIN_LENGTH);
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Are no enough characters" + MIN_PASSWORD_LENGTH);
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException(user.getLogin()
                    + "already exists, choose another login");
        }
        return storageDao.add(user);
    }
}
