package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkAge(user);
        checkPassword(user);
        checkLogin(user);
        storageDao.add(user);
        return user;
    }

    private void checkAge (User user) {
        if (user.getAge() < 18) {
            throw new RuntimeException("Age user least 18 years");
        }
    }
    private void checkPassword (User user) {
        if (user.getPassword().length() < 6) {
            throw new RuntimeException("User password is least 6 characters");
        }
    }
    private void checkLogin (User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("the user already exists");
        }
    }

}
