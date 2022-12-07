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
        if (user == null) {
            throw new RuntimeException("User is null");
        }
        if (user.getId() == null) {
            throw new RuntimeException("User is not found");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("user with this login does not exist");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("Age is lower than MIN_AGE for registration");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("User password must be at least 6 characters");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User with this login is already in the register");
        }
        return storageDao.add(user);
    }
}
