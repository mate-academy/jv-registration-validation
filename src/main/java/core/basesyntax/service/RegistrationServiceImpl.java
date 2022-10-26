package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("The user must be older than " + MIN_AGE);
        }

        if (user.getLogin() == null) {
            throw new RuntimeException("The login can not be null!");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("The password length must be longer than "
                    + MIN_PASSWORD_LENGTH);
        }

        if (user.getPassword() == null) {
            throw new RuntimeException("The password can not be null!");

        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("The user already axist! Please, enter another email.");
        }
        return storageDao.add(user);
    }
}

