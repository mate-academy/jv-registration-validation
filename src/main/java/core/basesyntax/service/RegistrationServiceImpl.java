package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceprions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIX_LENGTH_LOGIN = 6;
    private static final int MIX_LENGTH_PAS = 6;
    private static final int MIN_AGE = 18;
    private StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User must not be null");
        }
        if (user.getLogin().trim().length() == 0) {
            throw new RegistrationException("Login must not be blank");
        }
        User existingUser = storageDao.get(user.getLogin());
        if (existingUser != null) {
            throw new RegistrationException("User with this login already exists");
        }
        if (user.getLogin().length() < MIX_LENGTH_LOGIN
                || user.getPassword().length() < MIX_LENGTH_PAS) {
            throw new RegistrationException("Invalid data transmitted");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Your age is not enough");
        }
        return storageDao.add(user);
    }
}
