package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can`t be null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can`t be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Age can`t be less than eighteen");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Password length can`t be less than six symbol");
        }
        if (storageDao.get(user.getLogin()).getLogin().equals(user.getLogin())) {
            throw new RegistrationException("User with this login already exists");
        }
        return storageDao.add(user);
    }
}
