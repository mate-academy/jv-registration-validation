package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User is null");
        }
        String login = user.getLogin();
        if (login == null || login.length() < 6) {
            throw new RegistrationException("Login must be at least 6 characters long");
        }

        String password = user.getPassword();
        if (password == null || password.length() < 6) {
            throw new RegistrationException("Password is null or short");
        }

        int age = user.getAge();
        if (age < 18) {
            throw new RegistrationException("To small guy");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("Login is already taken");
        }

        storageDao.add(user);
        return user;
    }
}
