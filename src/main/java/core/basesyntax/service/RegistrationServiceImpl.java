package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private StorageDao storage = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("Can't create User, because user is null");
        }

        if (user.getLogin() == null || user.getLogin().isEmpty()
                || user.getLogin().isBlank()) {
            throw new RuntimeException("Cant register user.Login shouldn't be null and empty."
                    + " User: " + user);
        }

        if (storage.get(user.getLogin()) != null) {
            throw new RuntimeException("This username already exists");
        }

        if (user.getAge() == null || user.getAge() < 18) {
            throw new RuntimeException("Cant register user.Age shouldn't be null and less 18.User:"
                    + user);
        }

        if (user.getPassword() == null || user.getPassword().length() < 6
                || user.getPassword().isBlank()) {
            throw new RuntimeException("Please, write correct Password");
        }

        storage.add(user);
        return user;
    }
}

