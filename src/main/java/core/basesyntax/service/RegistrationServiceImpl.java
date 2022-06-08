package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throwException(" User is null.");
        }
        if (user.getLogin() == null) {
            throwException(" User login is null.");
        }
        if (user.getAge() == null) {
            throwException(" User age is null.");
        }
        if (user.getPassword() == null) {
            throwException(" User password is null.");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throwException(" User already exists.");
        }
        if (user.getAge() < 18) {
            throwException(" Age is less than 18.");
        }
        if (user.getPassword().length() < 6) {
            throwException(" Password should be at least 6 symbols.");
        }
        return storageDao.add(user);
    }

    private void throwException(String message) {
        throw new RuntimeException("Cannot register user." + message);
    }
}
