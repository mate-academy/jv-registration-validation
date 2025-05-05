package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkLogin(user.getLogin());
        checkPassword(user.getPassword());
        checkAge(user.getAge());

        return storageDao.add(user);
    }

    private void checkLogin(String userLogin) {
        if (userLogin == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (storageDao.get(userLogin) != null) {
            throw new RegistrationException("User" + userLogin + " already exist");
        }
        if (userLogin.length() < MIN_LENGTH) {
            throw new RegistrationException("Login length must be greater than " + MIN_LENGTH);
        }
    }

    private void checkPassword(String userPassword) {
        if (userPassword == null) {
            throw new RegistrationException("Password can`t be null");
        }
        if (userPassword.length() < MIN_LENGTH) {
            throw new RegistrationException("Password length must be greater than " + MIN_LENGTH);
        }
    }

    private void checkAge(Integer userAge) {
        if (userAge == null) {
            throw new RegistrationException("Age must be not null");
        }
        if (userAge < MIN_AGE) {
            throw new RegistrationException("Age must be greater than " + MIN_AGE);
        }
    }
}
