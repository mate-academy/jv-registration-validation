package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {

        if (user.getLogin() == null || user.getAge() == null || user.getPassword() == null) {
            throw new RuntimeException("Value cannot be null!");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User with such login already exists.");
        }

        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("Age cannot be less than 18.");
        }

        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Password length cannot be snaller than 6 symbols.");
        }
        storageDao.add(user);
        return user;
    }
}
