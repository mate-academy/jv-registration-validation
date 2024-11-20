package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws InvalidDataException {
        if (user.getAge() == null || user.getLogin() == null || user.getPassword() == null) {
            throw new InvalidDataException("All required fields should be completed ");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("this login already exists");
        }
        if (user.getLogin().length() < 6) {
            throw new InvalidDataException("Login is too short");
        }
        if (user.getPassword().length() < 6) {
            throw new InvalidDataException("Password is too short");
        }
        if (user.getAge() < 18) {
            throw new InvalidDataException("You have to be at least 18 years old to register");
        }
        storageDao.add(user);
        return user;
    }
}
