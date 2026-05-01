package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_LOG_LENGTH = 6;
    private static final int MIN_AGE = 18;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("Incorrect user registration");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login is null!");
        }
        if (user.getLogin().length() < MIN_LOG_LENGTH) {
            throw new RegistrationException("Login is too short!");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("Name already taken");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password is null!");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Password is short!");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age is null!");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("User is younger 18!");
        }
        storageDao.add(user);
        return storageDao.get(user.getLogin());
    }
}
