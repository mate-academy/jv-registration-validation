package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final Integer MIN_REGISTRATION_AGE = 18;
    private static final Integer MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storage;

    public RegistrationServiceImpl() {
        storage = new StorageDaoImpl();
    }

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("User is null");
        }
        if (user.getLogin() == null || user.getLogin().isBlank()) {
            throw new RuntimeException("Login is empty");
        }
        if (storage.get(user.getLogin()) != null) {
            throw new RuntimeException("User with this login is already exist");
        }
        if (user.getPassword() == null || user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Password length is less than 6");
        }
        if (user.getAge() == null || user.getAge() < MIN_REGISTRATION_AGE) {
            throw new RuntimeException("Invalid user age");
        }
        storage.add(user);
        return storage.get(user.getLogin());
    }
}
