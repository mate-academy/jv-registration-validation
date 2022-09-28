package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws RuntimeException {
        if (user.getLogin() == null) {
            throw new RuntimeException("User's Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("User's Password can't be null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("User's Passwords length can't be less "
                    + MIN_PASSWORD_LENGTH);
        }
        if (user.getAge() == null) {
            throw new RuntimeException("User's Age can't be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("Not valid age. Age must be least " + MIN_AGE);
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User already exists");
        }
        return storageDao.add(user);
    }
}
