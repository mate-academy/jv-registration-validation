package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_COUNT_CHARACTER = 6;
    private StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null || user.getAge() == null || user.getPassword() == null
                || user.getLogin() == null || user.getAge() < MIN_AGE
                || user.getPassword().length() < MIN_COUNT_CHARACTER || user.getPassword().isEmpty()
                || user.getLogin().isEmpty() || storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Data is wrong!");
        }
        return storageDao.add(user);
    }
}
