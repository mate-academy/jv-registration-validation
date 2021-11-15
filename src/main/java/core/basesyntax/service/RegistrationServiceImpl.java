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
            throw new RuntimeException("Invalid User");
        }
        if (user.getLogin() == null || user.getLogin().isEmpty()
                || storageDao.get(user.getLogin()) != null
                || user.getPassword() == null
                || user.getPassword().length() < MIN_PASSWORD_LENGTH
                || user.getAge() < MIN_AGE || user.getAge() == null) {
            throw new RuntimeException("Invalid Data");
        }
        return storageDao.add(user);
    }
}
