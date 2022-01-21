package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {

        if (user == null) {
            throw new RuntimeException("User object can't be null");
        }

        if (user.getAge() == null || user.getPassword() == null || user.getLogin() == null) {
            throw new RuntimeException("Age, password or login must not be null");
        }

        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("User age is less than 18!");
        }

        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("User password less than 6 characters!");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Login " + user.getLogin() + " already exists!");
        }

        return storageDao.add(user);
    }
}
