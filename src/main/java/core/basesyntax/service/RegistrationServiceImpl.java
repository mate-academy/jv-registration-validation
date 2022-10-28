package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    static final int MIN_AGE = 18;
    static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RuntimeException("Login is null");
        }

        if (user.getLogin().equals("")) {
            throw new RuntimeException("Login is empty");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Login are not unique");
        }
        if (user.getAge() == null) {
            throw new RuntimeException("Age is null");
        }

        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("Age is lower than 18");
        }

        if (user.getPassword() == null) {
            throw new RuntimeException("Password is null");
        }

        if (user.getPassword().equals("")) {
            throw new RuntimeException("Password is empty");
        }

        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Password is poor. Minimal size is 6 character");
        }

        storageDao.add(user);
        return user;
    }
}
