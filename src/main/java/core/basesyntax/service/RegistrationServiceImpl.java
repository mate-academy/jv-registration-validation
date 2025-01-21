package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null || user.getLogin().length() < 6) {
            throw new RegistrationServiceException("Login is not valid");
        }
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new RegistrationServiceException("Password is not valid");
        }
        if (user.getAge() == null || user.getAge() < 18) {
            throw new RegistrationServiceException("Age can't be less than 18");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationServiceException("User with this login already exists.");
        }
        return storageDao.add(user);
    }
}
