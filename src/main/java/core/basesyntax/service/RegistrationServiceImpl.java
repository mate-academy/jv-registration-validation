package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkUser(user);
        return storageDao.add(user);
    }

    private void checkUser(User user) {
        if (user == null) {
            throw new RuntimeException("Null input parameter at user argument");
        }
        if (user.getLogin() == null || user.getLogin().length() == 0) {
            throw new RuntimeException("Can`t register, invalid login");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Can`t register, login is exist");
        }
        if (user.getAge() < 18) {
            throw new RuntimeException("Can`t register, not enough years");
        }
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new RuntimeException("Can`t register, password must be longer");
        }
    }
}
