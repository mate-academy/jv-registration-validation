package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_ALLOWED_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null || user.getPassword() == null || user.getAge() == null) {
            throw new RuntimeException("Data can't be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("This login already exists");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Password can't be less than " + MIN_PASSWORD_LENGTH
                    + " characters");
        }
        if (user.getAge() < MIN_ALLOWED_AGE) {
            throw new RuntimeException("Age under " + MIN_ALLOWED_AGE + " is not allowed");
        }
        storageDao.add(user);
        return user;
    }
}
