package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int VALID_AGE = 18;
    private static final int VALID_PASSWORD = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null
                || user.getAge() == null || user.getPassword() == null
                || user.getLogin() == null || storageDao.get(user.getLogin()) != null
                || user.getAge() < VALID_AGE || user.getPassword().length() < VALID_PASSWORD) {
            throw new RuntimeException("Incorrect data");
        }
        return storageDao.add(user);
    }
}
