package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("User is null");
        }

        if (user.getLogin() == null) {
            throw new RuntimeException("Login is null");
        }

        if (user.getPassword() == null) {
            throw new RuntimeException("Password is null");
        }

        if (user.getAge() == null) {
            throw new RuntimeException("Age is null");
        }

        if (user.getLogin().equals("")) {
            throw new RuntimeException("Login is empty");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User already exists");
        }

        if (user.getAge() <= 18) {
            throw new RuntimeException("Age is under 18");
        }

        if (user.getPassword().length() < 6) {
            throw new RuntimeException("Password is less than 6 characters");
        }

        storageDao.add(user);
        return user;
    }
}
