package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        User tmp = storageDao.get(user.getLogin());
        if (user.getAge() == null || user.getLogin() == null || user.getPassword() == null) {
            throw new NullPointerException();
        }

        if (tmp == null) {
            if (user.getAge() >= 18 && user.getLogin().length() >= 6 && user.getPassword().length() >= 6) {
                storageDao.add(user);
                return user;
            }
        } else {
            return new User();
        }
        return null;
    }

}
