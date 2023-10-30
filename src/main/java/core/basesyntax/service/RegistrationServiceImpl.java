package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws RegistrationException {
        if (user == null) {
            throw new RegistrationException("User is null");
        }
        if (storageDao.get(user.getLogin()) != null
                && !user.getLogin().equals(storageDao.get(user.getLogin()))) {
            throw new RegistrationException("User with this login already exists ");
        }
        if (user.getLogin().length() < 6 || user.getPassword().length() < 6) {
            throw new RegistrationException("Login and Password can't be less than 6");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Not valid age: " + user.getAge()
                    + ". Min allowed age is " + MIN_AGE);
        }
        return storageDao.add(user);
    }
}
