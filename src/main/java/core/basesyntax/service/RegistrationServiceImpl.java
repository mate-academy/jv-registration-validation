package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (storageDao.get(user.getLogin()).equals(null)) {
            throw new RuntimeException("Login is exist");
        }
        if (user.getLogin().equals(null) || user.getPassword().equals(null)) {
            throw new RuntimeException("Your password or login is incorrect");
        }
        if (user.getLogin().length() < 6 && user.getPassword().length() < 6) {
            throw new RuntimeException("Your password and login should be bigger than 6");
        }
        if (user.getAge().equals(null)) {
            throw new RuntimeException("Your age is incorrect");
        }
        if (user.getAge() < 18) {
            throw new RuntimeException("Your age should be 18 or older");
        }
        return storageDao.add(user);
    }
}
