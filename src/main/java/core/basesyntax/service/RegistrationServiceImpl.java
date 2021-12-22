package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private StorageDao storage = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("Can't create User");
        }

        if (user.getLogin() == null || user.getLogin().isEmpty()
                                    || user.getLogin().isBlank()) {
            throw new RuntimeException("Please, write correct Login");
        }

        if (storage.get(user.getLogin()) != null) {
            throw new RuntimeException("This username already exists");
        }

        if (user.getAge() == null || user.getAge() < 18) {
            throw new RuntimeException("The age doesn't correct");
        }

        if (user.getPassword() == null || user.getPassword().length() < 6
                                       || user.getPassword().isBlank()) {
            throw new RuntimeException("Please, write correct Password");
        }

        return storage.add(user);
    }
}

