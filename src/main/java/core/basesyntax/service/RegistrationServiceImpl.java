package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private StorageDaoImpl storage = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getId() < 0) {
            throw new RuntimeException("Invalid id");
        }
        if (user.getAge() == null || user.getAge() <= 18) {
            throw new RuntimeException("User under 18 years old");
        }
        if (user.getPassword() == null || user.getLogin() == null
                || user.getPassword().length() <= 6) {
            throw new RuntimeException("Password or login is incorrect");
        }
        if (storage.get(user.getLogin()) != null) {
            throw new RuntimeException("This user has already registered");
        }
        storage.add(user);
        return user;
    }
}
