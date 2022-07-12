package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException();
        }
        if (user.getId() != null) {
            throw new RuntimeException("You cannot set ID");
        }
        if (user.getAge() == null || user.getAge() < MIN_AGE) {
            throw new RuntimeException("Age should be at least 18 years old");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("User should have a password value");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Password should have at least 6 symbols");
        }
        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            throw new RuntimeException("Login cannot be null or empty");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User already exists with such login: " + user.getLogin());
        }
        storageDao.add(user);
        return user;
    }
}
