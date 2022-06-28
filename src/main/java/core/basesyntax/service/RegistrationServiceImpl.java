package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE_ALLOWED = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("User can't be null");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("Login can't be null");
        }

        if (user.getPassword().length() >= MIN_PASSWORD_LENGTH
                && !user.equals(storageDao.get(user.getLogin()))
                && user.getAge() != null
                && user.getAge() >= MIN_AGE_ALLOWED
                && user.getAge() < Integer.MAX_VALUE) {
            storageDao.add(user);
        }
        return user;
    }
}
