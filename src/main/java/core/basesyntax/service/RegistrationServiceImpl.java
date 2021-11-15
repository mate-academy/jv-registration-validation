package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 120;
    private static final int MIN_PASSWORD_LENGTH = 6;

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

        StorageDao storageDao = new StorageDaoImpl();

        if (user.getAge() >= MIN_AGE
            && user.getAge() <= MAX_AGE
            && user.getPassword().length() >= MIN_PASSWORD_LENGTH
            && userWithSameLoginExists(user) == false) {
            storageDao.add(user);
            return user;
        }
        return null;
    }

    public boolean userWithSameLoginExists(User user) {
        StorageDao storageDao = new StorageDaoImpl();
        if (storageDao.get(user.getLogin()) != null) {
            return true;
        }
        return false;
    }
}
