package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("Incorrect user registration");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login is null!");
        }
        int minLogLength = 6;
        if (user.getLogin().length() < minLogLength) {
            throw new RegistrationException("Login is too short!");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("Name already taken");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password is null!");
        }
        int minPassLength = 6;
        if (user.getPassword().length() < minPassLength) {
            throw new RegistrationException("Password is short!");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age is younger null!");
        }
        int minAge = 18;
        if (user.getAge() < minAge) {
            throw new RegistrationException("User is younger 18!");
        }
        storageDao.add(user);
        return storageDao.get(user.getLogin());
    }
}
