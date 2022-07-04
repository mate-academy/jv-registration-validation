package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_ALLOWABLE_AGE = 18;
    private static final int MINIMUM_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("A user equal to null cannot be registered");
        }
        if (Storage.people.contains(user)) {
            throw new RuntimeException("Such a user already exists");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("Login cannot be null");
        }
        if (user.getAge() < MINIMUM_ALLOWABLE_AGE) {
            throw new RuntimeException("Only users over the age of 18");
        }
        if (user.getPassword().length() < MINIMUM_PASSWORD_LENGTH) {
            throw new RuntimeException("user password is at least 6 characters");
        }
        return storageDao.add(user);
    }
}
