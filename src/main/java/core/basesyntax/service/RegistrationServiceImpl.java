package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user != null && user.getAge() >= 18 && user.getAge() < 120
                && user.getLogin() != null && !user.getLogin().isEmpty()
                && user.getPassword() != null && user.getPassword().length() > 6
                && storageDao.get(user.getLogin()) == null) {
            storageDao.add(user);
        } else {
            throw new RuntimeException();
        }
        return user;
    }
}
