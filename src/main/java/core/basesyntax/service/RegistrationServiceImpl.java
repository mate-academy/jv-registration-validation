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
            throw new RegistrationException("User can't be null");
        }

        String login = user.getLogin();
        if (login == null || login.length() < 6) {
            throw new RegistrationException("Login should be at least 6 characters");
        }

        String password = user.getPassword();
        if (password == null || password.length() < 6) {
            throw new RegistrationException("Password should be at least 6 characters");
        }

        Integer age = user.getAge();
        if (age == null || age < 18) {
            throw new RegistrationException("User should be at least 18 years old");
        }

        if (storageDao.get(login) != null) {
            throw new RegistrationException("User with this login already exists");
        }

        return storageDao.add(user);
    }
}
