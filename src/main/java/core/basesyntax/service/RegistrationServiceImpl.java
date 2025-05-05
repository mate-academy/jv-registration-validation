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

        if (user.getId() == null) {
            throw new RegistrationException("Id cannot be null");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with this login already exists");
        }

        if (user.getLogin() == null || user.getLogin().length() < 6) {
            throw new RegistrationException("User's login cannot be at least 6 characters");
        }

        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new RegistrationException("User's password cannot be at least 6 characters");
        }

        if (user.getAge() == null || user.getAge() < 18) {
            throw new RegistrationException("User must be at least 18 years old");
        }

        return storageDao.add(user);
    }
}
