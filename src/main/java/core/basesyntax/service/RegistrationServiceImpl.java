package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MINIMAL_AGE = 18;
    public static final int PASSWORD_LENGTH = 6;

    @Override
    public User register(User user) {
        StorageDao database = new StorageDaoImpl();
        if (user.getAge() < MINIMAL_AGE || user.getAge() == null) {
            throw new RuntimeException("Your age is inappropriate");
        }
        if (user.getPassword().length() < PASSWORD_LENGTH || user.getPassword() == null) {
            throw new RuntimeException("Password must contain more than 6 characters");
        }
        if (database.get(user.getLogin()) != null || user.getLogin() == null) {
            throw new RuntimeException("User with this login already exists");
        }
        return database.add(user);
    }
}
