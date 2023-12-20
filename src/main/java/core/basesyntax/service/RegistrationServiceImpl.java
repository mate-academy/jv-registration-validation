package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User  can't be null");
        }
        if (user.getId() == null || user.getLogin() == null || user.getPassword() == null
                || user.getAge() == null) {
            throw new RegistrationException("User id, login, password or age can't be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Invalid data, age must be greater than 18");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("Invalid data, login length must be greater than zero");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Invalid data, password length must be greater "
                    + "than zero");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("Invalid data, user with this already exist");
        }
        if (storageDao.get(user.getId()) != null) {
            throw new RegistrationException("Invalid data, user with this id already exist");
        }
        storageDao.add(user);
        return user;
    }
}
