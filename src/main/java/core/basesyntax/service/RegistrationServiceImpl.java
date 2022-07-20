package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        User storagedUser = storageDao.get(user.getLogin());
        if (user == null
                || user.getPassword() == null
                || user.getLogin() == null
                || (storagedUser != null && user.getLogin().equals(storagedUser.getLogin()))
                || user.getAge() == null
                || user.getAge() < 18) {
            throw new RuntimeException();
        }

        return storageDao.add(user);
    }
}
