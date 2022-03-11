package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDaoImpl  storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getAge() > 17 && user.getPassword() != null
                && user.getPassword().length() > 5 && !findMatchLogin(user)) {
            storageDao.add(user);
            return user;
        }
        throw new RuntimeException("Input Errors");
    }

    private boolean findMatchLogin(User user) {
        for (User current: Storage.people) {
            if (current.getLogin().equals(user.getLogin())) {
                return true;
            }

        }
        return false;
    }
}
