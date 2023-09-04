package core.basesyntax.service;

import core.basesyntax.RegistrationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User can't be null!");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null!");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null!");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age can't be null!");
        }
        if (user.getLogin().length() < 6) {
            throw new RegistrationException("Login must be at least 6 characters long!");
        }
        if (user.getPassword().length() < 6) {
            throw new RegistrationException("Password must be at least 6 characters long!");
        }
        if (user.getAge() < 18) {
            throw new RegistrationException("User must be at least 18 years old!");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User already exists!");
        }
        return storageDao.add(user);
    }
}
