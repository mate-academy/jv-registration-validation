package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE_FOR_REGISTRATION = 18;
    private static final int MIN_LIGHT_PASSWORD = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RuntimeException("We can't have null login");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User with this login already exists: " + user.getLogin());
        }
        if (user.getAge() < MIN_AGE_FOR_REGISTRATION) {
            throw new RuntimeException("User age " + user.getAge()
                    + " less MIN_AGE_FOR_REGISTRATION: " + MIN_AGE_FOR_REGISTRATION);
        }
        if (user.getPassword().length() < MIN_LIGHT_PASSWORD) {
            throw new RuntimeException("Password must be at least MIN_LIGHT_PASSWORD: "
                    + MIN_LIGHT_PASSWORD + " characters");
        }
        return storageDao.add(user);
    }
}
