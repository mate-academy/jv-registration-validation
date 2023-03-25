package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        userValidation(user);
        loginValidation(user);
        passwordValidation(user);
        ageValidation(user);
        return storageDao.add(user);
    }

    private void userValidation(User user) {
        if (user == null) {
            throw new RegistrationException("User can't be null");
        }
    }

    private void passwordValidation(User user) {
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (user.getPassword().length() < 6) {
            throw new RegistrationException("Password should be 6 and more symbols");
        }
    }

    private void loginValidation(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("Such login is already existed");
        }
    }

    private void ageValidation(User user) {
        if (user.getAge() == null) {
            throw new RegistrationException("Age can't be null");
        }
        if (user.getAge() < 18) {
            throw new RegistrationException("User should be 18 and older");
        }
    }
}
