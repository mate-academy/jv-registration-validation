package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getAge() < 18) {
            throw new RuntimeException("Age must be more than 17");
        }
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new RuntimeException("Password must be more than 5 symbols");
        }
        for (User userFromList : Storage.people) {
            if (userFromList.getLogin().equals(user.getLogin())) {
                throw new RuntimeException("User login already exist");
            }
        }
        storageDao.add(user);
        return user;
    }
}
