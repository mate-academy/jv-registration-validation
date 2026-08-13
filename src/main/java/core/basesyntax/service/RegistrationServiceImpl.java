package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User must not be null");
        }

        String login = user.getLogin();
        String password = user.getPassword();
        Integer age = user.getAge();

        if (login == null || login.length() < 6) {
            throw new RegistrationException("Login must be at least 6 characters");
        }
        if (password == null || password.length() < 6) {
            throw new RegistrationException("Password must be at least 6 characters");
        }
        if (age == null || age < 18) {
            throw new RegistrationException("User must be at least 18 years old");
        }
        if (storageDao.get(login) != null) {
            throw new RegistrationException("User with this login already exists");
        }

        return storageDao.add(user);
    }
}
