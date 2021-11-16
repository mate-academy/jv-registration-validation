package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_USER_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null || user.getAge() == null || user.getAge() < MIN_USER_AGE
                || user.getLogin() == null || user.getLogin().isEmpty()
                || user.getPassword() == null || user.getPassword().length() < MIN_PASSWORD_LENGTH
                || storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("invalid data ");
        }
        return storageDao.add(user);
    }
}
