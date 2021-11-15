package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 120;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("User must not be equal null");
        }
        if (user.getLogin() == null
                || user.getAge() <= 0
                || user.getAge() == null
                || user.getPassword() == null
                || user.getAge() >= MAX_AGE) {
            throw new RuntimeException("Incorrect data");
        }

        if (user.getAge() >= MIN_AGE
                && user.getAge() <= MAX_AGE
                && user.getPassword().length() >= MIN_PASSWORD_LENGTH
                && userWithSameLoginExists(user) == false) {
            return storageDao.add(user);
        }
        return null;
    }

    public boolean userWithSameLoginExists(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            return true;
        }
        return false;
    }
}
