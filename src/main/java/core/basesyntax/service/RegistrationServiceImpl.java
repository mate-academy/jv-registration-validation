package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MIN_LENGTH = 6;
    public static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User must not be null");
        }
        String login = user.getLogin();
        String password = user.getPassword();
        Integer age = user.getAge();
        if (login == null) {
            throw new RegistrationException("Username must not be empty");
        }
        if (password == null) {
            throw new RegistrationException("Password must not be empty");
        }
        if (age == null) {
            throw new RegistrationException("Age must not be empty");
        }
        if (storageDao.get(login) != null) {
            throw new RegistrationException("Username '"
                    + login
                    + "' is already taken");
        }
        if (login.trim().length() < MIN_LENGTH) {
            throw new RegistrationException("Username must be at least 6 characters long");
        }
        if (password.trim().length() < MIN_LENGTH) {
            throw new RegistrationException("Password must be at least 6 characters long");
        }
        if (age < MIN_AGE) {
            throw new RegistrationException("You must be at least 18 years old");
        }
        return storageDao.add(user);
    }
}
