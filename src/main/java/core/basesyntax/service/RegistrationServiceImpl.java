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
        if (user == null || user.getAge() == null
                || user.getLogin() == null || user.getPassword() == null) {
            throw new RuntimeException("User or user fields could not be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Such a login already exists" + user.getLogin());
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("Minimal age must be " + MIN_AGE);
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Minimal length must be " + MIN_PASSWORD_LENGTH);
        }
        return storageDao.add(user);
    }
}
