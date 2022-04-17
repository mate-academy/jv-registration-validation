package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {

    @Override
    public User register(User user) {
        StorageDao database = new StorageDaoImpl();
        if (user.getAge() < 18 || user.getAge() == null) {
            throw new RuntimeException("Your age is inappropriate");
        }
        if (user.getPassword().length() < 6 || user.getPassword() == null) {
            throw new RuntimeException("Password must contain more than 6 characters");
        }
        if (database.get(user.getLogin()) != null || user.getLogin() == null) {
            throw new RuntimeException("User with this login already exists");
        }
        database.add(user);
        return user;
    }
}
