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
        if (user.getLogin() == null || user.getLogin().length() < 6) {
            throw new RegistrationException("Login is too short!");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("Name already taken");
        }
        if (user.getPassword().length() < 6) {
            throw new RegistrationException("Password is too short!");
        }
        if (user.getAge() < 18) {
            throw new RegistrationException("User is younger 18!");
        }
        storageDao.add(user);
        return storageDao.get(user.getLogin());
    }
}
