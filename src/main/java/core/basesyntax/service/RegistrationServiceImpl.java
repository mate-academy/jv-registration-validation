package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkLogin(user);
        checkAge(user);
        checkPassword(user);
        storageDao.add(user);
        return user;
    }

    void checkLogin(User user) {
        if (user.getLogin() == null) {
            throw new RuntimeException("Error! Null login");
        }
        if (user.getLogin().equals("")) {
            throw new RuntimeException("Error! Empty login");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Error! Login already in use");
        }
    }

    void checkAge(User user) {
        if (user.getAge() < 18) {
            throw new RuntimeException("Error! Age must be 18 or greater");
        }
    }

    void checkPassword(User user) {
        if (user.getPassword() == null) {
            throw new RuntimeException("Error! Password null");
        }
        if (user.getPassword().equals("")) {
            throw new RuntimeException("Error! Empty password");
        }
        if (user.getPassword().length() < 6) {
            throw new RuntimeException("Error! Password less that six symbols");
        }
    }
}
