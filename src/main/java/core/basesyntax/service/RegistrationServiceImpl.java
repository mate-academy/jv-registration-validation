package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getAge() < AGE) {
            throw new RuntimeException();
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException();
        }
        if (user.getLogin() == null) {
            throw new RuntimeException();
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException();
        }
        storageDao.add(user);
        return user;
    }
}
