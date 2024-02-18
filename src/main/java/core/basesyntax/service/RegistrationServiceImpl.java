package core.basesyntax.service;

import core.basesyntax.RegistrationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int LENGTH_OF_PASSWORD = 6;
    private static final int LENGTH_OF_LOGIN = 6;
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 100;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("Login cannot be null!");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age cannot be null!");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password cannot be null!");
        }

        User existingUser = storageDao.get(user.getLogin());

        if (existingUser != null && existingUser.getLogin().equals(user.getLogin())) {
            throw new RegistrationException("User with this login already registered!");
        }
        if (user.getPassword().length() < LENGTH_OF_PASSWORD) {
            throw new RegistrationException("User password too short!");
        }
        if (user.getLogin().length() < LENGTH_OF_LOGIN) {
            throw new RegistrationException("User login too short!");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("User too young!");
        }
        if (user.getAge() > MAX_AGE) {
            throw new RegistrationException("User too old!");
        }
        return storageDao.add(user);
    }
}

