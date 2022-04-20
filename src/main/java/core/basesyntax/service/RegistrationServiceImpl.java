package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD = 6;

    @Override
    public User register(User user) {
        StorageDaoImpl storage = new StorageDaoImpl();
        if (user.getPassword() == null || user.getPassword().length() < MIN_PASSWORD) {
            throw new RuntimeException("Password isn't correct!");
        }
        if (storage.get(user.getLogin()) != null) {
            throw new RuntimeException("Login already exist!");
        }
        if (user == null) {
            throw new RuntimeException("User is null!");
        }
        if (user.getAge() == null || user.getAge() < MIN_AGE) {
            throw new RuntimeException("Age isn't correct!");
        }
        storage.add(user);
        return user;
    }
}
