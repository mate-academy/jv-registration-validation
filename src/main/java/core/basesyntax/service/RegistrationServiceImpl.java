package core.basesyntax.service;

import core.basesyntax.RegistrationUserException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationUserException("User cannot be null");
        }
        if (user.getLogin() == null || user.getLogin().length() < 6) {
            throw new RegistrationUserException("Login cannot be null and must"
                    + "be at least 6 characters long");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationUserException("Cant add this user. User already exists");
        }

        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new RegistrationUserException("Password cannot be null and must"
                    + "be at least 6 characters long");
        }
        if (user.getAge() < 18) {
            throw new RegistrationUserException("Age cannot be less than 18");
        }
        return storageDao.add(user);
    }
}
