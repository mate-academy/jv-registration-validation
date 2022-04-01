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
        if (user == null
                || user.getLogin() == null
                || user.getPassword() == null
                || user.getAge() == null) {
            throw new NullPointerException("incorrect data");
        }

        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("User's age is under " + MIN_AGE);
        }

        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("User's password contains less than " + MIN_PASSWORD_LENGTH);
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User with this login " + user.getLogin()
                    + " already exists");
        }

        return storageDao.add(user);
    }
}
