package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int PASSWORD_MIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null
                || user.getLogin() == null
                || user.getPassword() == null
                || user.getAge() == null
                || user.getPassword().length() < PASSWORD_MIN_LENGTH
                || user.getAge() < MIN_AGE
                || storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Something wrong, check requirements");
        }
        return storageDao.add(user);
    }
}
