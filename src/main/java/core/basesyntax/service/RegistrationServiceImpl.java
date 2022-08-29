package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_AGE_FOR_REGISTRATION = 18;
    private static final int MINIMUM_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (!isValid(user)) {
            throw new RuntimeException();
        }
        storageDao.add(user);
        return user;
    }

    public boolean isValid(User user) {
        if (user == null
                || user.getPassword() == null
                || user.getLogin() == null
                || user.getId() < 0
                || user.getAge() < MINIMUM_AGE_FOR_REGISTRATION
                || user.getPassword().length() < MINIMUM_PASSWORD_LENGTH
                || storageDao.get(user.getLogin()) != null) {
            return false;
        }
        return true;
    }
}
