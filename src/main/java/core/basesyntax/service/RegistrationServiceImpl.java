package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMAL_AGE = 18;
    private static final int PASSWORD_LENGTH = 6;
    private final StorageDao database = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getAge() == null || user.getAge() < MINIMAL_AGE) {
            throw new RuntimeException("Your age is inappropriate");
        }
        if (user.getPassword() == null || user.getPassword().length() < PASSWORD_LENGTH) {
            throw new RuntimeException("Password must contain more than 6 characters");
        }
        if (user.getLogin() == null || database.get(user.getLogin()) != null) {
            throw new RuntimeException("User with this login already exists");
        }
        return database.add(user);
    }
}
