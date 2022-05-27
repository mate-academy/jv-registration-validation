package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_AGE = 18;
    private static final int MINIMUM_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getAge() < 0) {
            throw new RuntimeException("Age cannot be negative");
        }
        if (user.getAge() < MINIMUM_AGE) {
            throw new RuntimeException("Your age should be 18 and higher");
        }
        if (user.getAge() == null) {
            throw new RuntimeException("The age cannot be empty field");
        }
        if (user.getLogin() == null
                || user.getLogin().isEmpty()
                || user.getLogin().isBlank()) {
            throw new RuntimeException("The login cannot be empty field");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User with such login already exists");
        }
        if (user.getPassword() == null
                || user.getPassword().isBlank()
                || user.getPassword().isEmpty()) {
            throw new RuntimeException("Password cannot be empty field");
        }
        if (user.getPassword().length() < MINIMUM_PASSWORD_LENGTH) {
            throw new RuntimeException("Your password should be at least 6 characters long");
        }
        storageDao.add(user);
        return user;
    }
}
