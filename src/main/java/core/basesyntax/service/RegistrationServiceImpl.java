package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationError("User is null");
        }
        if (user.getLogin() == null) {
            throw new RegistrationError("Login is null");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationError("Login is too short");
        }
        if (user.getPassword() == null) {
            throw new RegistrationError("Password is null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationError("Password is too short");
        }
        if (user.getAge() == null) {
            throw new RegistrationError("Age is null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationError("User is too young");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationError("User already exists");
        }
        return storageDao.add(user);
    }
}
