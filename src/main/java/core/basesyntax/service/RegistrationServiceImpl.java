package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASS_LENGTH = 6;
    private StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("User can't be null");
        }
        if (user.getLogin().equals(null)) {
            throw new RuntimeException("Login can't be null");
        }
        if (user.getPassword().equals(null)) {
            throw new RuntimeException("Password can't be null");
        }
        if (user.getPassword().length() < MIN_PASS_LENGTH) {
            throw new RuntimeException("Minimum " + MIN_PASS_LENGTH + " characters");
        }
        if (user.getAge() == null) {
            throw new RuntimeException("Age can't be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("Age less than " + MIN_AGE);
        }
        return storageDao.add(user);
    }
}
