package core.basesyntax.service;

import core.basesyntax.InvalidDataException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidDataException("User is null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("User with this login already exist");
        }
        if (user.getLogin() == null || user.getLogin().length() < 6) {
            throw new InvalidDataException("Login should contain at least 6 characters");
        }
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new InvalidDataException("Password should contain at least 6 characters");
        }
        if (user.getAge() == 0 || user.getAge() < 18) {
            throw new InvalidDataException("User age should be at least 18");
        }
        return storageDao.add(user);
    }
}
