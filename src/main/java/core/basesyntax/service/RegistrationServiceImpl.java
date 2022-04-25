package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH_PASSWORD = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException();
        }
        if (user.getAge() == null || user.getLogin() == null
                || user.getPassword() == null) {
            throw new RuntimeException("You must not leave any fields empty");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("Your age must be greater than eighteen");
        }
        if (user.getPassword().length() < MIN_LENGTH_PASSWORD) {
            throw new RuntimeException("Your password must have more than 6 characters");
        }
        return storageDao.add(user);
    }
}
