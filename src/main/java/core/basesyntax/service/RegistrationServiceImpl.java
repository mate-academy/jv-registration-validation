package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.ValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        verifyUser(user);
        verifyLogin(user);
        verifyPassword(user);
        verifyAge(user);
        return storageDao.add(user);
    }

    private static void verifyAge(User user) {
        if(user.getAge() < MIN_AGE) {
            throw new ValidationException("Not valid age, age should be more than " + MIN_AGE);
        }
        if(user.getAge() == null) {
            throw new ValidationException("Age shouldn't be null");
        }
    }

    private static void verifyUser(User user) {
        if(user == null) {
            throw new ValidationException("Can't find a  user");
        }
    }
    private static void verifyLogin(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new ValidationException("User with the same login already exists");

        }
        if(user.getLogin() == null) {
            throw new ValidationException("Login can't be empty");
        }
    }

    private static void verifyPassword(User user) {
        if (user.getPassword() == null) {
            throw new ValidationException("Password can't be null");
        }
        if(user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new ValidationException("Password should be more that 6 characters");
        }
    }
}
