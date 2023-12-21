package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationServiceImplException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_AND_PASSWORD_LEN = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws RegistrationServiceImplException {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationServiceImplException("User with this login already exists");
        }
        if (user.getLogin().length() < MIN_LOGIN_AND_PASSWORD_LEN) {
            throw new RegistrationServiceImplException("Login should consist of at least 6 characters");
        }
        if (user.getPassword().length() < MIN_LOGIN_AND_PASSWORD_LEN) {
            throw new RegistrationServiceImplException("Password should consist of at least 6 characters");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationServiceImplException("Password should consist of at least 6 characters");
        }
        storageDao.add(user);
        return user;
    }
}
