package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH_FOR_LOGIN_PASSWORD = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        userCheck(user);
        if (storageDao.get(user.getLogin()) == null) {
            return storageDao.add(user);
        }
        throw new RegistrationException("We have user with such login");
    }

    private void userCheck(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age can't be null");
        }
        if (user.getLogin().length() < MIN_LENGTH_FOR_LOGIN_PASSWORD) {
            throw new RegistrationException("Login must to have at least 6 characters");
        }
        if (user.getPassword().length() < MIN_LENGTH_FOR_LOGIN_PASSWORD) {
            throw new RegistrationException("Password must to have at least 6 characters");
        }
        if (user.getAge() < 18) {
            throw new RegistrationException("User must to be adult");
        }
    }
}
