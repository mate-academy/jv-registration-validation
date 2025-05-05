package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_USER_AGE = 18;
    private static final int MIN_USER_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("User is null");
        }
        if (user.getAge() == null || user.getAge() < MIN_USER_AGE) {
            throw new RuntimeException("Invalid User age value: " + user.getAge());
        }
        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            throw new RuntimeException("Invalid User login value: " + user.getLogin());
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("There is already user with that login");
        }
        if (user.getPassword() == null || user.getPassword().length() < MIN_USER_PASSWORD_LENGTH) {
            throw new RuntimeException("Invalid User password value");
        }
        return storageDao.add(user);
    }
}
