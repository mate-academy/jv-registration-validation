package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User can`t be null!");
        }

        if (user.getLogin().length() < 6) {
            throw new RegistrationException("Login must contain at least 6 symbols!");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with this login already exists!");
        }

        if (user.getPassword() == null) {
            throw new RegistrationException("Password cannot be null!");
        }

        if(user.getPassword().length() < 6) {
            throw new RegistrationException("Password must be contain at least 6 symbols!");
        }

        if (user.getAge() == null || user.getAge() < 18) {
            throw new RegistrationException("User`s age must be at least 18 years old!");
        }
        return storageDao.add(user);
    }
}
