package core.basesyntax.service;

import core.basesyntax.RegistrationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("user is null");
        }

        for (User users : Storage.people) {
            if (users.getLogin().equals(user.getLogin())) {
                throw new RegistrationException("this Login already register");
            }
        }

        if (user.getLogin() == null) {
            throw new RegistrationException("login is null");
        }

        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("this Login too short");
        }

        if (user.getPassword() == null) {
            throw new RegistrationException("password is null");
        }

        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("this Password too short");
        }

        if (user.getAge() == null || user.getAge() <= 0) {
            throw new RegistrationException("age cant be zero or negative");
        }

        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("you need be at least 18 years old to register");
        }

        storageDao.add(user);
        return user;
    }
}
