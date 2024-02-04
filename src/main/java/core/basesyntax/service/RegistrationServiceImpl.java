package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.AuthenticationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (isInvalidUser(user)) {
            throw new AuthenticationException("Invalid user data for registration");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new AuthenticationException("User with this login already exists");
        }
        return storageDao.add(user);
    }

    private boolean isInvalidUser(User user) {
        return user.getLogin().length() < 6
               || user.getPassword().length() < 6
                || user.getAge() < 18;
    }
}
