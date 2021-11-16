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
        if (user == null || user.getAge() == null || user.getAge() < MINIMUM_AGE
                || user.getPassword() == null || user.getPassword().length() < MINIMUM_PASSWORD
                || user.getPassword().contains(" ")
                || user.getLogin().isEmpty()
                || user.getLogin().contains(" ")
                || storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Invalid data");
        }
        return storageDao.add(user);
    }
}
