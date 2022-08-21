package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.service.db.Storage;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (Storage.people.size() >= 1) {
            for (User ifLogin : Storage.people) {
                if (ifLogin.getLogin().equals(user.getLogin())) {
                    return null;
                }
            }
        }
        if (user.getLogin() != null
                && user.getPassword() != null
                && user.getAge() != null
                && user.getAge() >= 18
                && user.getPassword().length() >= 6) {
            storageDao.add(user);
            return user;
        }
        throw new RuntimeException("Invalid data for register user");
    }
}
