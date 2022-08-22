package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int PASSWORD_MINIMUM_LENGTH = 6;
    private static final int USER_MINIMUM_AGE = 18;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null || user.getLogin() == null || user.getPassword() == null
                || user.getAge() == null || user.getLogin().isEmpty()) {
            throw new NullPointerException("User fields can't be null/empty");
        }
        if (user.getAge() < 0) {
            throw new RuntimeException("User age can't be negative");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User with login "
                    + user.getLogin() + " exists");
        }
        if (user.getAge() < USER_MINIMUM_AGE) {
            throw new RuntimeException("User age must be at least " + USER_MINIMUM_AGE
                    + "years old, but was " + user.getAge());
        }
        if (user.getPassword().length() < PASSWORD_MINIMUM_LENGTH) {
            throw new RuntimeException("Password length should be more than 6");
        }
        return storageDao.add(user);
    }
}
