package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validatingUser(user);
        validatingUserLogin(user);
        validatingUserAge(user);
        validatingUserPassword(user);
        return storageDao.add(user);
    }

    public void validatingUser(User user) {
        if (user == null) {
            throw new RuntimeException("User can not be null");
        }
    }

    public void validatingUserLogin(User user) {
        if (user.getLogin() == null) {
            throw new RuntimeException("Login can not be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Such login already exist");
        }
    }

    public void validatingUserAge(User user) {
        if (user.getAge() < 18) {
            throw new RuntimeException("User younger than 18 years old");
        }
    }

    public void validatingUserPassword(User user) {
        if (user.getPassword() == null) {
            throw new RuntimeException("Password can not be null");
        }
        if (user.getPassword().length() < 6) {
            throw new RuntimeException("User password is less than 6 symbols");
        }
    }
}
