package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {

    private final StorageDaoImpl storageDao;

    public RegistrationServiceImpl(StorageDao dao) {
        this.storageDao = (StorageDaoImpl) dao;
    }

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidDataException("User cannot be null");
        }

        if (user.getLogin() == null || user.getLogin().trim().isEmpty() || user.getLogin().length() < 6) {
            throw new InvalidDataException("Invalid login");
        }

        if (user.getPassword() == null || user.getPassword().trim().isEmpty() || user.getPassword().length() < 6) {
            throw new InvalidDataException("Invalid password");
        }

        if (user.getAge() < 18) {
            throw new InvalidDataException("You must be at least 18 years old");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("Login already exists");
        }

        storageDao.add(user);
        return user;
    }
}
