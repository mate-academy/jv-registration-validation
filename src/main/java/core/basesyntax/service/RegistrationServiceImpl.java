package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationServiceException("User cannot be null");
        }
        if (user.getLogin() == null) {
            throw new RegistrationServiceException("Login cannot be null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationServiceException("Password cannot be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationServiceException("The user is registered");
        }
        if (user.getLogin().length() < MIN_LENGTH) {
            throw new RegistrationServiceException("The login is too short");
        }
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new RegistrationServiceException("The password is too short");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationServiceException("User must be adult");
        }
        storageDao.add(user);
        return user;
    }
}
