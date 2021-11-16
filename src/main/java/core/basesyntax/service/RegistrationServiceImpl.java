package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storage = new StorageDaoImpl();
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("User is null");
        }
        if (user.getLogin() == null || user.getPassword() == null || user.getAge() == null || user.getId() == null) {
            throw new RuntimeException("User cannot have null states");
        }
        if (storage.get(user.getLogin()) != null) {
            throw new RuntimeException("User with login " + user.getLogin() + " is already registered");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("User is too young, " + user.getAge() + " y.o.");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Password should be biggest or equals to 6 symbols");
        }
        return storage.add(user);
    }
}
