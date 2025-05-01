package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User cannot be null");
        }
        if (user.getLogin() == null || user.getLogin().length() < 6) {
            throw new RegistrationException("User login must be at least"
                    + " 6 characters long and not null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User login already exists");
        }
        if (storageDao.getById(user.getId()) != null) {
            throw new RegistrationException("User id already exists");
        }
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new RegistrationException("User password must be at "
                    + "least 6 characters long and not null");
        }
        if (user.getAge() < 18) {
            throw new RegistrationException("User must be at least 18 years old");
        }

        storageDao.add(user);

        return user;
    }
}
