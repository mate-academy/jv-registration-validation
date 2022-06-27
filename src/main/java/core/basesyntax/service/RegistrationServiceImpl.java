package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private StorageDaoImpl storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getPassword().length() < 6) {
            throw new RuntimeException("Password length should be 6 or more characters");
        }
        if (user.getAge() < 18) {
            throw new RuntimeException("User should be at least 18 years old");
        }
        for (User user1 : Storage.people) {
            if (user1.equals(user)) {
                throw new RuntimeException("User already exists");
            }
        }
        storageDao.add(user);
        return user;
    }
}
