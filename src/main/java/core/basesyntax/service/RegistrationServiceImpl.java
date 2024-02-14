package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User is null");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("User's login is null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("User's password is null");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("User's age is null");
        }
        if (user.getLogin().length() < 6) {
            throw new RegistrationException("Login is less than 6 characters");
        }
        if (user.getPassword().length() < 6) {
            throw new RegistrationException("Password is less than 6 characters");
        }
        if (user.getAge() < 18) {
            throw new RegistrationException("Age is under 18");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with this login is already in the storage");
        }
        storageDao.add(user);
        return user;
    }
}
