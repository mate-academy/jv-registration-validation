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
            throw new RegistrationException("User cannot be null");
        }

        String login = user.getLogin();
        if (login == null || login.length() < 6) {
            throw new RegistrationException("Login must have at least 6 characters");
        }

        if (storageDao.get(login) != null) {
            throw new RegistrationException("Login already exists");
        }

        String password = user.getPassword();
        if (password == null || password.length() < 6) {
            throw new RegistrationException("Password must have at least 6 characters");
        }

        Integer age = user.getAge();
        if (age == null || age < 18) {
            throw new RegistrationException("Your age must be at least 18");
        }

        User addedUser = storageDao.add(user);

        return addedUser;
    }
}
