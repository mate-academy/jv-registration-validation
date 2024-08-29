package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User can't be null");
        }

        User existingUser = storageDao.get(user.getLogin());
        if (existingUser != null) {
            throw new IllegalArgumentException("User with this login already exists");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new IllegalArgumentException("Login must be at least 6 characters long");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new IllegalArgumentException("Password must be at least 6 characters long");
        }
        if (user.getAge() < MIN_AGE) {
            throw new IllegalArgumentException("User must be at least 18 years old");
        }
        if (user.getLogin().contains(" ")) {
            throw new IllegalArgumentException("Login can't contain any whitespaces");
        }
        return storageDao.add(user);
    }
}
