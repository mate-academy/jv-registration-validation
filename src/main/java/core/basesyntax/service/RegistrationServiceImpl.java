package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMAL_AGE = 18;
    private static final int MINIMAL_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User can't be null!");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null!");
        }
        if (user.getLogin().length() < MINIMAL_LENGTH) {
            throw new RegistrationException("Login should contain at least 6 characters!");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age cant be null!");
        }
        if (user.getAge() < MINIMAL_AGE) {
            throw new RegistrationException("User must be at least 18 years old!");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null!");
        }
        if (user.getPassword().length() < MINIMAL_LENGTH) {
            throw new RegistrationException("Password should contain at least 6 characters!");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with this login already exists!");
        }
        storageDao.add(user);
        return user;
    }
}
