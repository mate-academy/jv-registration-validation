package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_AGE = 18;
    private static final int MINIMUM_PASSWORD = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getAge() < MINIMUM_AGE || user.getAge() == null
                || user.getPassword().length() < MINIMUM_PASSWORD || user.getPassword() == null
                || user.getPassword().contains(" ")
                || user.getLogin() == null || user.getLogin().length() == 0
                || user.getLogin().contains(" ")
                || storageDao.get(user.getLogin()) != null || user == null) {
            throw new RuntimeException("Invalid data");
        }
        return storageDao.add(user);
    }
}
