package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null || user.getPassword() == null) {
            throw new NullPointerException();
        }
        if (user.getLogin().length() < 6) {
            throw new RegistrationException("Login must contain at least 6 symbols");
        }
        if (user.getPassword().length() < 6) {
            throw new RegistrationException("Password must contain at least 6 symbols");
        }
        if (user.getAge() <= 17) {
            throw new RegistrationException("User must be older than 17 years");
        }
        if (Storage.people.contains(user)) {
            throw new RegistrationException("User is already registered");
        }

        Storage.people.add(user);
        return user;
    }
}
