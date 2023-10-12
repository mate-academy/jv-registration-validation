package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
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
        if (user.getLogin().length() < 6) {
            throw new RegistrationServiceException("The login is too short");
        }
        if (user.getPassword().length() < 6) {
            throw new RegistrationServiceException("The password is too short");
        }
        if (user.getAge() < 18) {
            throw new RegistrationServiceException("User must be adult");
        }
        storageDao.add(user);
        return user;
    }
}
