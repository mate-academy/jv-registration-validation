package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int LOG_AND_PASS_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkValidUser(user);
        return storageDao.add(user);
    }

    private void checkValidUser(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (user.getLogin().length() < LOG_AND_PASS_LENGTH) {
            throw new RegistrationException("Login is shorter than valid length");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (user.getPassword().length() < LOG_AND_PASS_LENGTH) {
            throw new RegistrationException("Password is shorter than valid length");
        }
        if (user.getAge() < 18) {
            throw new RegistrationException("User is not adult");
        }
    }
}
