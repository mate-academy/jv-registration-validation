package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.AgeException;
import core.basesyntax.exception.LoginException;
import core.basesyntax.exception.PasswordException;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        String login = user.getLogin();
        String password = user.getPassword();
        Integer age = user.getAge();
        if (login == null) {
            throw new LoginException("Username must not be empty");
        }
        if (password == null) {
            throw new PasswordException("Password must not be empty");
        }
        if (age == null) {
            throw new AgeException("Age must not be empty");
        }
        if (storageDao.get(login) != null) {
            throw new LoginException("Username '"
                    + login
                    + "' is already taken");
        }
        if (login.trim().length() < 6) {
            throw new LoginException("Username must be at least 6 characters long");
        }
        if (password.trim().length() < 6) {
            throw new PasswordException("Password must be at least 6 characters long");
        }
        if (age < 18) {
            throw new AgeException("You must be at least 18 years old");
        }
        return storageDao.add(user);
    }
}
