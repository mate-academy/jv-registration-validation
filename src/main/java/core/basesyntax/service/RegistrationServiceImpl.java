package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can`t be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User" + user.getLogin() + " already exist");
        }
        if (user.getLogin().length() < MIN_LENGTH) {
            throw new RegistrationException("Login length must be greater than 6");
        }
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new RegistrationException("Password length must be greater than 6");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Age must be greater than 18");
        }
        return storageDao.add(user);
    }
}
