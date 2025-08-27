package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new IllegalDataException("Login cannot be null");
        }
        if (user.getPassword() == null) {
            throw new IllegalDataException("Password cannot be null");
        }
        if (user.getAge() == null) {
            throw new IllegalDataException("Age cannot be null");
        }
        if (user.getLogin().length() < 6) {
            throw new IllegalDataException("Login should be 6 digits and longer");
        }
        if (user.getPassword().length() < 6) {
            throw new IllegalDataException("Password should be 6 digits and longer");
        }
        if (user.getAge() < 18) {
            throw new IllegalDataException("User's age is at least 18 y.o.");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new IllegalDataException("User with this login already exists");
        }
        return storageDao.add(user);
    }
}
