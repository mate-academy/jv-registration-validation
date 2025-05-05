package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int REQUIRED_MIN_AGE = 18;
    private static final int REQUIRED_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {

        if (user == null) {
            throw new RuntimeException("User cannot be null");
        }
        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            throw new RuntimeException("Login cannot be empty");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("The Login is already in the database");
        }
        if (user.getAge() < REQUIRED_MIN_AGE) {
            throw new RuntimeException("The user is under 18 years of age");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("The password cannot by a null");
        }
        if (user.getPassword().length() < REQUIRED_PASSWORD_LENGTH) {
            throw new RuntimeException("The user has a password less than 6 characters");
        }
        return storageDao.add(user);
    }
}
